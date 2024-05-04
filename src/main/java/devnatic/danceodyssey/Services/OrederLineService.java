package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.CartRepository;
import devnatic.danceodyssey.DAO.Repositories.OrderLineRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.Interfaces.IOrderLineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrederLineService implements IOrderLineService {
    private final OrderLineRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;



    @Override
    public OrderLine addProductToOrderAndCreateCart(Integer productId, Integer nbrProduct, Integer cartId) {
        // Vérifier si le panier existe
        CART cart = cartRepository.findById(cartId).orElse(new CART()); // Créer un nouveau panier s'il n'existe pas

        // Calculer le prix total du produit
        Products product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        float totalPriceForProduct;
        if (product.getIsPromotion()) {
            totalPriceForProduct = product.getPricePromotion() * nbrProduct;
        } else {
            totalPriceForProduct = product.getPrice() * nbrProduct;
        }

        // Créer une nouvelle commande
        OrderLine order = new OrderLine();
        order.setTotalPrice(totalPriceForProduct);
        order.setOrderDate(LocalDate.now());
        order.setDescription(product.getProductName());
        // Récupérer la première image du produit s'il existe et si c'est une image (pas une vidéo)
        if (!product.getImages().isEmpty()) {
            for (MediaFiles media : product.getImages()) {
                if (media.getImageUrl().toLowerCase().endsWith(".png") || media.getImageUrl().toLowerCase().endsWith(".jpg") || media.getImageUrl().toLowerCase().endsWith(".jpeg")) {
                    order.setImgUrl(media.getImageUrl());
                    break;
                }
            }
        }       order.setProducts(product);
        order.setNbProdO(nbrProduct);
        if (product.getIsPromotion()) {
            order.setDetailPrice(product.getPricePromotion());
        } else {
            order.setDetailPrice(product.getPrice());
        }
        order.setCart(cart);

        // Sauvegarder la commande
        orderRepository.save(order);



        // Mettre à jour le prix total du panier
        float totalCartPrice = cart.getTotPrice();
        totalCartPrice += totalPriceForProduct;
        cart.setTotPrice(totalCartPrice);
        // Mettre à jour le nombre total de produits dans le panier
        Integer totalProductsInCart = cart.getTotalProducts();
        if (totalProductsInCart == null) {
            totalProductsInCart = 0;
        }
        totalProductsInCart += nbrProduct;
        cart.setTotalProducts(totalProductsInCart);

        // Sauvegarder le panier
        cartRepository.save(cart);
        return order;
    }

    @Override
    public List<OrderLine> getOrderLinesWithNullOrderLineId() {
        return orderRepository.findByOrdersIsNull();
    }

    @Override
    public OrderLine  removeOrderLine(Integer orderLineId, Integer cartId) {
        // Vérifier si l'OrderLine existe
        OrderLine orderLine = orderRepository.findById(orderLineId)
                .orElseThrow(() -> new RuntimeException("OrderLine not found"));

        // Vérifier si le CART associé existe
        CART cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Mettre à jour le prix total du panier
        float totalCartPrice = cart.getTotPrice();
        // Réduire le prix total du panier en fonction du prix de l'OrderLine supprimée
        totalCartPrice -= orderLine.getTotalPrice();
        cart.setTotPrice(totalCartPrice);

        // Supprimer l'OrderLine du CART
        cart.getOrderList().remove(orderLine);

        // Mettre à jour le nombre total de produits dans le panier
        Integer totalProductsInCart = cart.getTotalProducts() - orderLine.getNbProdO();
        cart.setTotalProducts(totalProductsInCart);

        // Sauvegarder les modifications apportées au CART, y compris la mise à jour du nombre total de produits
        cartRepository.save(cart);

        // Supprimer l'OrderLine de la base de données
        orderRepository.delete(orderLine);

        // Renvoyer l'OrderLine supprimée
        return orderLine;
    }
    @Override
    public OrderLine updateOrderLineQuantity(Integer orderLineId, Integer newQuantity) {
        // Trouver l'OrderLine dans la base de données
        OrderLine orderLine = orderRepository.findById(orderLineId)
                .orElseThrow(() -> new RuntimeException("OrderLine not found"));

        // Calculer le changement de quantité
        int quantityChange = newQuantity - orderLine.getNbProdO();

        // Mettre à jour la quantité de produit
        orderLine.setNbProdO(newQuantity);

        // Mettre à jour le prix total de la commande
        float totalPriceForProduct;
        if (orderLine.getProducts().getIsPromotion()) {
            totalPriceForProduct = orderLine.getProducts().getPricePromotion() * newQuantity;
        } else {
            totalPriceForProduct = orderLine.getProducts().getPrice() * newQuantity;
        }orderLine.setTotalPrice(totalPriceForProduct)
        ;

        // Sauvegarder les modifications dans la base de données
        OrderLine updatedOrderLine = orderRepository.save(orderLine);

        // Mettre à jour le prix total du panier en fonction des modifications apportées à la ligne de commande
        // Mettre à jour le prix total du panier en fonction des modifications apportées à la ligne de commande
        CART cart = orderLine.getCart();
        float totalCartPrice = 0;

        // Parcourir toutes les lignes de commande associées à ce panier et recalculer le prix total du panier
        List<OrderLine> cartOrderLines = orderRepository.findByOrdersIsNull();
        for (OrderLine ol : cartOrderLines) {
            totalCartPrice += ol.getTotalPrice();
        }
        cart.setTotPrice(totalCartPrice);

        // Mettre à jour le nombre total de produits dans le panier
        Integer totalProductsInCart = cart.getTotalProducts() + quantityChange;
        cart.setTotalProducts(totalProductsInCart);

        // Sauvegarder les modifications apportées au CART
        cartRepository.save(cart);

        return updatedOrderLine;
    }
    }
