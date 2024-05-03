package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.ENUM.Etat;
import devnatic.danceodyssey.DAO.ENUM.Payment_Mode;
import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.*;
import devnatic.danceodyssey.Interfaces.IOrdersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrdersService implements IOrdersService {
    private  final OrdersRepositppry ordersRepositppry;
    private final OrderLineRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private  final  DeliveryRepository deliveryRepository;


    @Override
    public List<Orders> Get_AllOrders(Integer idcart) {
        // Récupérer le panier à partir de son identifiant
        CART cart = cartRepository.findById(idcart)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        // Récupérer toutes les commandes associées à ce panier
        List<Orders> ordersList = cart.getOrdersList();

        return ordersList;}
    @Override
    public List<Orders> Get_AllOrders() {
        // Récupérer toutes les commandes à partir du repository
        List<Orders> allOrders = ordersRepositppry.findAll();

        return allOrders;}

    @Override
    public void Accept_Orders(Integer idorders) {
    Orders orders =ordersRepositppry.findById(idorders).orElse(null);
        orders.setEtat(Etat.VALIDATED);
        ordersRepositppry.save(orders);
    }

    @Override
    public void Refuse_orders(Integer idorders) {
Orders orders= ordersRepositppry.findById(idorders).orElse(null);
orders.setEtat(Etat.REFUSED);
ordersRepositppry.save(orders);
    }

    @Override
    public void Cancel_orders(Integer idorders) {
        Orders orders = ordersRepositppry.findById(idorders).orElse(null);
        LocalDateTime currentTimeNow = LocalDateTime.now();
        LocalDateTime Limite = orders.getOrdersDate().atStartOfDay().plusMinutes(300);
        if (currentTimeNow.isBefore(Limite) && orders.getEtat()!=Etat.VALIDATED) {
            ordersRepositppry.delete(orders);
        } else {
            throw new RuntimeException("La commande ne peut plus être annulée.");
        }
    }

    @Override
    public Delivery affectcamandtolivaison(String Region, Delivery l) {
        List<Orders> order=ordersRepositppry.getnotaffectedCommand(Region);
        LocalDate d;

        for (Orders i:order)
        {

            i.setEtat(Etat.VALIDATED);
        }
        l.setAdress(Region);
        d=LocalDate.now();
        l.setRelease_date(d);
        deliveryRepository.save(l);
        return l;
    }

    @Override
    public Orders Confirm_Order(Orders orders, Integer idCart) {
        // Vérifier si le panier existe
        CART cart = cartRepository.findById(idCart)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        // Associer le panier à la commande
        orders.setCartO(cart);
        double x = 0.2;

        // Mettre à jour la date de la commande
        orders.setOrdersDate(LocalDate.now());
        orders.setPayment_mode(Payment_Mode.TRANSFER);
        orders.setTax((long) (cart.getTotPrice() * x));
        orders.setTotalPriceOders(cart.getTotPrice()+orders.getTax());
        orders.setBuyer_email(cart.getUser().getEmail());
        orders.setEtat(Etat.EDITABLE);

        orders.setCurrency("DT");
        // Sauvegarder la commande
        Orders savedOrder = ordersRepositppry.save(orders);

        // Mettre à jour l'ID de la commande pour les lignes de commande dont l'ID de commande est null
        List<OrderLine> orderLinesToUpdate = orderRepository.findByOrdersIsNull();
        for (OrderLine orderLine : orderLinesToUpdate) {
            orderLine.setOrders(savedOrder);
        }
        orderRepository.saveAll(orderLinesToUpdate);

        // Supprimer toutes les lignes de commande associées au panier confirmé
        List<OrderLine> orderLinesToDelete = orderRepository.findByCart(cart);

        // Mise à jour de la quantité des produits vendus
        for (OrderLine orderLine : orderLinesToDelete) {
            Products product = orderLine.getProducts();
            int quantitySold = orderLine.getNbProdO();
            product.setQuantiteVendue(quantitySold);
            product.setQuantity(product.getQuantity() - quantitySold);
            productRepository.save(product);
        }

        // Réinitialiser le prix total du panier à 0
        cart.setTotPrice(0.0f);
        cart.setTotalProducts(0);
        // Réinitialiser la liste des lignes de commande dans le panier
        cart.setOrderList(new ArrayList<>());

        // Sauvegarder les modifications apportées au panier
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public List<OrderLine> getOrderLinesByOrderId(Integer orderId) {
        return orderRepository.findByOrdersOrdersId(orderId);
    }

    @Override
    public List<String> getProductNamesByCartId(Integer cartId) {
        List<OrderLine> orderLines = orderRepository.findByCartId(cartId);
        return orderLines.stream()
                .map(orderLine -> orderLine.getProducts().getProductName())
                .collect(Collectors.toList());
    }

    }



