package com.tweetexport.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.social.twitter.api.Tweet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PdfGenerator {

    public static ByteArrayInputStream generatePdfStream(List<Tweet> tweets) {

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(50);
            table.setWidths(new int[]{2, 2});


            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, "UTF-8", true);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, "UTF-8", true);

            PdfPCell headingCell;
            headingCell = new PdfPCell(new Phrase("Date", headFont));
            headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headingCell);

            headingCell = new PdfPCell(new Phrase("Content", headFont));
            headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headingCell);

            for (Tweet tweet : tweets) {

                PdfPCell cell;

                cell = new PdfPCell(new Paragraph(tweet.getCreatedAt().toString(), contentFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);


                cell = new PdfPCell(new Paragraph(tweet.getText(), contentFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

            }

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
