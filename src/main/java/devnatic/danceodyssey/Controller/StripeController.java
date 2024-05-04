package devnatic.danceodyssey.Controller;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.stripe.exception.StripeException;
import devnatic.danceodyssey.DAO.Entities.Orders;
import devnatic.danceodyssey.DAO.Repositories.CartRepository;
import devnatic.danceodyssey.DAO.Repositories.OrdersRepositppry;
import devnatic.danceodyssey.Interfaces.IStripeSerivce;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
@RestController
public class StripeController {
    private final IStripeSerivce iStripeSerivce;
    private  final OrdersRepositppry ordersRepositppry;
    private  final CartRepository cartRepository;


    @PostMapping("/paiement")
    public ResponseEntity<String> effectuerPaiement(@RequestParam("commandeId") Integer commandeId,
                                                    @RequestParam("devise") String devise,
                                                   HttpServletResponse response) throws StripeException, IOException {
        Orders order = ordersRepositppry.findById(commandeId).get();

     // iStripeSerivce.effectuerPaiement(order.getTotalPriceOders(), devise);
        // System.out.println("paiement effectué avec succés, vous pouvez télécharger votre facture");
        // création du document PDF
        com.lowagie.text.Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // ajouter les informations de la facture
        document.add(new Paragraph("Facture pour la commande #" + order.getOrdersId()));
        document.add(new Paragraph("Email client: " + order.getBuyer_email()));
        document.add(new Paragraph("Adresse client: " + order.getBuyer_address()));

        // ajouter les articles commandés
        PdfPTable table = new PdfPTable(3);
        table.addCell("Nom de l'article");
        table.addCell("Quantité");
        table.addCell("Prix");

        document.add(table);

        // ajouter le total de la facture
        document.add(new Paragraph("Total: " + order.getTotalPriceOders()));
        document.add(new Paragraph("Tax: " + order.getTax()));
        document.close();

        // envoi du document PDF en réponse
        response.setContentType("facture/pdf");
        response.setContentLength(out.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"facture.pdf\"");
        OutputStream outStream = response.getOutputStream();
        outStream.write(out.toByteArray());
        outStream.flush();
        outStream.close();
        return new ResponseEntity<>("paiement effectué avec succés, vous pouvez télécharger votre facture", HttpStatus.OK);
    }
}
