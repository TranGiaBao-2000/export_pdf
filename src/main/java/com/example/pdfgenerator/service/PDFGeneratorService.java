package com.example.pdfgenerator.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@Service
public class PDFGeneratorService {
    @Autowired
    QRCodeService qrCodeService;
    public void export(HttpServletResponse response) throws IOException {
        BaseFont myfont = BaseFont.createFont(".\\Roboto\\Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(myfont, 20);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        byte[] qrCode = qrCodeService.generateQRCode();
        document.open();
        String imageUrl = "https://www.google.com/intl/en_ALL/images/logos/images_logo_lg.gif";

        Image logo = Image.getInstance(new URL(imageUrl));
        logo.setAlignment(Image.MIDDLE);
        logo.scaleToFit(300,300);


        Image image2 = Image.getInstance(qrCode);
        image2.setAlignment(Image.MIDDLE);
        Paragraph paragraph = new Paragraph("Xét nghiệm chẩn đoán", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(logo);
        document.add(paragraph);
        document.add(paragraph2);
        document.add(image2);
        document.close();
    }

}
