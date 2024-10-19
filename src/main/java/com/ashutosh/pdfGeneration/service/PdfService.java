package com.ashutosh.pdfGeneration.service;

import com.ashutosh.pdfGeneration.model.Pdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
public class PdfService {
    public static final String PDF_DIRECTORY = "generated_pdfs/";

    private String generateFileName(Pdf pdf) {
        return pdf.getSeller().replaceAll("\\s+", "_") + "_" +
                pdf.getBuyer().replaceAll("\\s+", "_") + "_" +
                System.currentTimeMillis() + ".pdf";
    }



    public String generatePdf(Pdf pdf) throws IOException, DocumentException {
        String fileName = generateFileName(pdf);
        String filePath = PDF_DIRECTORY + fileName;


        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            return filePath;
        }

        File dir = new File(PDF_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("set size of document");
        OutputStream outputStream = new FileOutputStream(filePath);
        PdfWriter.getInstance(document,outputStream);
        document.open();

        PdfPTable seller_buyer = new PdfPTable(2);
        seller_buyer.setWidthPercentage(80);
        float[] columnWidths = {1f, 1f};
        seller_buyer.setWidths(columnWidths);
        PdfPCell seller = new PdfPCell(new Phrase("Seller:"));
        seller.setBorder(0);
        PdfPCell buyer = new PdfPCell(new Phrase("Buyer:"));
        buyer.setBorder(0);
        PdfPCell sname = new PdfPCell(new Phrase(pdf.getSeller()));
        sname.setBorder(0);
        PdfPCell bname = new PdfPCell(new Phrase(pdf.getBuyer()));
        bname.setBorder(0);
        PdfPCell saddress = new PdfPCell(new Phrase(pdf.getSellerAddress()));
        saddress.setBorder(0);
        PdfPCell baddress = new PdfPCell(new Phrase(pdf.getBuyerAddress()));
        baddress.setBorder(0);
        PdfPCell gstin_seller = new PdfPCell(new Phrase("GSTIN: "+pdf.getSellerGstin()));
        gstin_seller.setBorder(0);
        PdfPCell gstin_buyer = new PdfPCell(new Phrase("GSTIN : "+pdf.getBuyerGstin()));
        gstin_buyer.setBorder(0);
        seller_buyer.addCell(seller);
        seller_buyer.addCell(buyer);
        seller_buyer.addCell(sname);
        seller_buyer.addCell(bname);
        seller_buyer.addCell(saddress);
        seller_buyer.addCell(baddress);
        seller_buyer.addCell(gstin_seller);
        seller_buyer.addCell(gstin_buyer);
        document.add(seller_buyer);

        PdfPTable divider = new PdfPTable(1);
        divider.setWidthPercentage(80);
        PdfPCell dividerCell = new PdfPCell();
        dividerCell.setBorderWidth(1f);
        dividerCell.setBorderColor(BaseColor.BLACK);
        divider.addCell(dividerCell);
        document.add(divider);

        PdfPTable itemsTable = new PdfPTable(4);
        itemsTable.setWidthPercentage(80);

        PdfPCell itemcol = new PdfPCell(new Phrase("Item"));
        itemcol.setBorder(0);

        PdfPCell quantity = new PdfPCell(new Phrase("Quantity"));
        quantity.setBorder(0);

        PdfPCell rate = new PdfPCell(new Phrase("Rate"));
        rate.setBorder(0);

        PdfPCell amount = new PdfPCell(new Phrase("Amount"));
        amount.setBorder(0);

        itemsTable.addCell(itemcol);
        itemsTable.addCell(quantity);
        itemsTable.addCell(rate);
        itemsTable.addCell(amount);

        pdf.getItems().forEach(item -> {
            PdfPCell itemName = new PdfPCell(new Phrase(item.getName()));
            itemName.setBorder(0);

            PdfPCell itemQuantity = new PdfPCell(new Phrase(item.getQuantity()));
            itemQuantity.setBorder(0);

            PdfPCell itemRate = new PdfPCell(new Phrase(String.valueOf(item.getRate())));
            itemRate.setBorder(0);

            PdfPCell itemAmount = new PdfPCell(new Phrase(String.valueOf(item.getAmount())));
            itemAmount.setBorder(0);

            itemsTable.addCell(itemName);
            itemsTable.addCell(itemQuantity);
            itemsTable.addCell(itemRate);
            itemsTable.addCell(itemAmount);
        });

        document.add(itemsTable);
        document.close();
        return filePath;
    }





}
