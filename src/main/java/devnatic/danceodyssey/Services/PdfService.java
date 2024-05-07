package devnatic.danceodyssey.Services; // Assuming this is your correct package structure

import devnatic.danceodyssey.DAO.Entities.Feedback;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateFeedbackPdf(List<Feedback> feedbackList) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Feedback Report");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                int yPosition = 650; // Starting y position for feedback entries

                for (Feedback feedback : feedbackList) {
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Feedback ID: " + feedback.getFeedbackID());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Rating: " + feedback.getRating());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Message: " + feedback.getFeedbackMessage());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Resolved: " + (feedback.isResolved() ? "Yes" : "No"));
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Comments: " + feedback.getFeedbackcomments());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Created At: " + feedback.getCreatedAt());
                    contentStream.newLineAtOffset(0, -30);

                    yPosition -= 110; // Adjusting y position for next feedback entry
                }

                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}
