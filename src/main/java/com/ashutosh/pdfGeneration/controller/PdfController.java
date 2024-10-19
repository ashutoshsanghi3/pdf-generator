package com.ashutosh.pdfGeneration.controller;

import com.ashutosh.pdfGeneration.model.Pdf;
import com.ashutosh.pdfGeneration.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public String generatePdf(@RequestBody Pdf pdf) {
        try {
            String filePath = pdfService.generatePdf(pdf);
            return "PDF generated at: " + filePath;
        } catch (IOException e) {
            return "Error generating PDF: " + e.getMessage();
        } catch (DocumentException e) {
            return "Error generating PDF document: " + e.getMessage();
        }
    }
}
