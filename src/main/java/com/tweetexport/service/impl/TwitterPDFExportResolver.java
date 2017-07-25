package com.tweetexport.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tweetexport.service.PDFExportResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@Qualifier("twitterPdfResolver")
public class TwitterPDFExportResolver implements PDFExportResolver<List<Tweet>> {

    @Override
    public ByteArrayInputStream generatePdf(List<Tweet> inputData) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


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

        for (Tweet tweet : inputData) {
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


        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
