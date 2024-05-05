package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.CART;
import devnatic.danceodyssey.DAO.Entities.OrderLine;
import devnatic.danceodyssey.DAO.Entities.Orders;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.OrderLineRepository;
import devnatic.danceodyssey.DAO.Repositories.OrdersRepositppry;
import devnatic.danceodyssey.Interfaces.ICartService;
import devnatic.danceodyssey.Interfaces.IOrderLineService;
import devnatic.danceodyssey.Interfaces.IOrdersService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.webjars.NotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private  final IOrderLineService iOrderService;
    private  final IOrdersService iOrdersService;
    private  final OrdersRepositppry ordersRepositppry;
    private  final OrderLineRepository orderLineRepository;
    private  final ICartService cartService;

    @PostMapping("/addorder")
    public OrderLine addProductsToOrderAndCreateCart(@RequestParam int nbrProduct, @RequestParam int productId, @RequestParam int idCart) {
        return iOrderService.addProductToOrderAndCreateCart(productId, nbrProduct, idCart);
    }
    @PutMapping("/{orderLineId}/quantity")
    public OrderLine updateOrderLineQuantity(@PathVariable Integer orderLineId,
                                             @RequestParam Integer newQuantity) {
        return iOrderService.updateOrderLineQuantity(orderLineId, newQuantity);
    }
    @PostMapping("/confirm/{cartId}")
    public Orders confirmOrder(@RequestBody Orders orders, @PathVariable Integer cartId) {
        return iOrdersService.Confirm_Order(orders, cartId);
    }
    @GetMapping("/retrieve-all-Commandes/{Cart-id}")
    public List<Orders> getOrders(@PathVariable("Cart-id") Integer idcart) {
        return iOrdersService.Get_AllOrders(idcart);
    }
    @GetMapping("/retrieve-all-Commandes/")
    public List<Orders> getOrders() {
        return iOrdersService.Get_AllOrders();
    }
    @PutMapping("/{idOrers}/accept")
    public ResponseEntity<String> accepteOrder(@PathVariable("idOrers") Integer idOrers) {
        iOrdersService.Accept_Orders(idOrers);
        return ResponseEntity.ok("La commande a été acceptée avec succès.");
    }
    @PutMapping("/{idOrers}/refuser")
    public ResponseEntity<String> refuseOrder(@PathVariable("idOrers") Integer idOrers) {
        iOrdersService.Refuse_orders(idOrers);
        return ResponseEntity.ok("La commande a été refusée avec succès.");
    }

    @GetMapping("/generateInvoice/{orderId}")
    public void generateInvoice(@PathVariable Integer orderId, HttpServletResponse response) throws IOException {
        try {
            // Récupérer les informations de la commande à partir de la base de données
            Orders order = ordersRepositppry.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("Order not found"));

            // Création du document PDF
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            // Ajouter l'en-tête du document
            addDocumentHeader(document);

            // Ajouter les informations de la facture
            addInvoiceInfo(document, order);

            // Ajouter les articles commandés
            addOrderedItems(document, order);

            // Ajouter le résumé des totaux
            addSummary(document, order);

            // Fermer le document PDF
            document.close();

            // Envoi du document PDF en réponse
            response.setContentType("application/pdf");
            response.setContentLength(out.size());
            response.setHeader("Content-Disposition", "attachment; filename=\"invoice.pdf\"");
            OutputStream outStream = response.getOutputStream();
            outStream.write(out.toByteArray());
            outStream.flush();
            outStream.close();
        } catch (NotFoundException e) {
            // Gérer l'exception si la commande n'est pas trouvée
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            // Gérer les autres exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while generating the invoice.");
        }
    }

    private void addDocumentHeader(Document document) throws DocumentException, IOException {
        // Ajouter le logo de l'entreprise
        Image logo = Image.getInstance("src/main/java/devnatic/danceodyssey/Images/logoDance.png");
        logo.scaleToFit(90, 90);
        logo.setAbsolutePosition(document.getPageSize().getWidth() - 100, document.getPageSize().getHeight() - 80);
        document.add(logo);

        // Ajouter la signature de l'entreprise
        Image signature = Image.getInstance("src/main/java/devnatic/danceodyssey/Images/signHassen.png");
        signature.scaleToFit(100, 100);
        signature.setAbsolutePosition(document.getPageSize().getWidth() - signature.getScaledWidth() - 30, 30);
        document.add(signature);

        // Ajouter le titre de la facture
        Paragraph title = new Paragraph("Dance Odyssey", new Font(Font.TIMES_ROMAN, 22, Font.BOLD));
        title.setAlignment(Element.ALIGN_LEFT);
        title.add(Chunk.NEWLINE);
        title.add(Chunk.NEWLINE);
        title.add(new Chunk("Invoice", new Font(Font.TIMES_ROMAN, 16)));
        title.setAlignment(Element.ALIGN_CENTER);
        title.add(Chunk.NEWLINE);
        title.add(Chunk.NEWLINE);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addInvoiceInfo(Document document, Orders order) throws DocumentException {
        // Ajouter les informations de la facture
        document.add(new Paragraph("Invoice for the order N°: " + order.getOrdersId(), new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
        document.add(new Paragraph("Customer's email: " + order.getBuyer_email(), new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
        document.add(new Paragraph("Customer's address: " + order.getBuyer_address(), new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
        document.add(Chunk.NEWLINE);
    }

    private void addOrderedItems(Document document, Orders order) throws DocumentException {
        // Ajouter l'en-tête du tableau des articles commandés
        Paragraph header = new Paragraph("\n" +
                "Table of ordered items:", new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);
// Récupérer les lignes de commande associées à la commande spécifique
        List<OrderLine> orderLines = orderLineRepository.findByOrdersOrdersId(order.getOrdersId());

// Créer la table des articles commandés avec 4 colonnes
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

// Ajouter les en-têtes de colonne
        table.addCell("Product Name");
        table.addCell("Quantity");
        table.addCell("Unit price");
        table.addCell("Total amount");

// Ajouter chaque article commandé à la table
        for (OrderLine orderLine : orderLines) {
            table.addCell( orderLine.getDescription());
            table.addCell(String.valueOf(orderLine.getNbProdO()));
            table.addCell(String.valueOf(orderLine.getProducts().getPrice()));
            table.addCell(String.valueOf(orderLine.getTotalPrice()));
        }

// Ajouter la table au document
        document.add(table);
        document.add(Chunk.NEWLINE);

    }

    private void addSummary(Document document, Orders order) throws DocumentException {
        // Ajouter le résumé des totaux

        document.add(new Paragraph("Total Tax : " + order.getTax() + "DT", new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));

        document.add(new Paragraph("Total price includes Tax: " + order.getTotalPriceOders() + "DT", new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
    }

    @GetMapping("/{cartId}")
    public CART getCartById(@PathVariable Integer cartId) {
        return cartService.getCartById(cartId);
    }

    @GetMapping("/orderlines/{orderId}")
    public List<OrderLine> getOrderLinesByOrderId(@PathVariable Integer orderId) {
        return iOrdersService.getOrderLinesByOrderId(orderId);
    }
    @GetMapping("/orderlines/cart/{cartId}/productNames")
    public List<String> getProductNamesByCartId(@PathVariable Integer cartId) {
        return iOrdersService.getProductNamesByCartId(cartId);
    }
    @GetMapping("/nullOrderId")
    public List<OrderLine> getOrderLinesWithNullOrderIdByCartId() {
        return iOrderService.getOrderLinesWithNullOrderLineId();
    }
    @DeleteMapping("/orderline/{orderLineId}/cart/{cartId}")
    public OrderLine removeOrderLine(@PathVariable Integer orderLineId, @PathVariable Integer cartId) {
        return iOrderService.removeOrderLine(orderLineId, cartId);
    }

}
