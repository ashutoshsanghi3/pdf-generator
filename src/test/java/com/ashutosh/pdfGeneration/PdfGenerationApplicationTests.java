package com.ashutosh.pdfGeneration;

import com.ashutosh.pdfGeneration.model.Pdf;
import com.ashutosh.pdfGeneration.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PdfGenerationApplicationTests {

	@InjectMocks
	private PdfService pdfService;

	public PdfGenerationApplicationTests() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGeneratePdf() throws IOException, DocumentException {
		Pdf request = new Pdf();
		request.setSeller("XYZ Pvt. Ltd.");
		request.setSellerGstin("29AABBCCDD121ZD");
		request.setSellerAddress("New Delhi, India");
		request.setBuyer("Vedant Computers");
		request.setBuyerGstin("29AABBCCDD131ZD");
		request.setBuyerAddress("New Delhi, India");

		// Add sample items
		Pdf.Item item = new Pdf.Item();
		item.setName("Product 1");
		item.setQuantity("12 Nos");
		item.setRate(123.00);
		item.setAmount(1476.00);
		request.setItems(List.of(item));

		String filePath = pdfService.generatePdf(request);
		assertNotNull(filePath);
	}

}
