package org.documentoviscode.splashyapi.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.documentoviscode.splashyapi.data.CustomMultipartFile;
import org.documentoviscode.splashyapi.utility.fileconversion.DataJSON;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class DocumentConversionController{
    private final ApplicationContext applicationContext;

    @GetMapping(value = { "/monthlyReportPartner/{reportId}" })
    public String generatePartnerMonthlyReport(@PathVariable(name = "reportId") String reportId) throws Exception {
        String reportPath = "src/main/resources/";
        String downloadedFileName = "downloaded.json";

        GoogleDriveController controller = applicationContext.getBean(GoogleDriveController.class);

        File downloaded = new File(reportPath + downloadedFileName);
        try (FileOutputStream outputStream = new FileOutputStream(downloaded)) {
            outputStream.write(controller.downloadFile(reportId).getBody());
        }

        org.documentoviscode.splashyapi.utility.fileconversion.Document jsonDoc =
                new org.documentoviscode.splashyapi.utility.fileconversion.Document();
        jsonDoc.readFrom(reportPath + downloadedFileName);
        JSONObject data = ((DataJSON)jsonDoc.getData()).getKeys();
        downloaded.delete();

        JSONObject partner = new JSONObject(((List<Map>) data.get("users")).get(0));
        String reportFileName = "monthlyReport_" + partner.get("id") + "_" + data.get("creationDate") + ".pdf";

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        FileOutputStream outputStream = new FileOutputStream(reportPath + reportFileName);
        PdfWriter.getInstance(document, outputStream);

        Font fontHeader1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 32, new BaseColor(68, 139, 187));
        Font fontHeader2 = FontFactory.getFont(FontFactory.HELVETICA, 20f, 4, BaseColor.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont("Verdana", 13, BaseColor.DARK_GRAY);
        document.open();

        Paragraph header = new Paragraph("Raport miesieczny partnera", fontHeader1);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Od: " + data.get("startDate"), fontNormal));
        document.add(new Paragraph("Do: " + data.get("endDate"), fontNormal));
        document.add(new Paragraph("\n\n"));

        document.add(new Paragraph("Dane partnera:", fontHeader2));
        fontNormal.isUnderlined();
        document.add(new Paragraph("Imie: " + partner.get("name"), fontNormal));
        document.add(new Paragraph("Nazwisko: " + partner.get("surname"), fontNormal));
        document.add(new Paragraph("E-mail: " + partner.get("email"), fontNormal));
        Paragraph p = new Paragraph("\n\nRozliczenie:\n\n", fontNormal);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(new float[]{0.7f, 0.3f});
        table.setPaddingTop(1);
        table.addCell(new Phrase("Liczba ogladajacych", fontTable));
        table.addCell(new Phrase(data.get("viewers").toString(), fontTable));
        table.addCell(new Phrase("Suma obejrzanych godzin", fontTable));
        table.addCell(new Phrase(data.get("hoursWatched").toString(), fontTable));
        table.addCell(new Phrase("Stawka za godzine ogladalnosci", fontTable));
        table.addCell(new Phrase("0,03 PLN", fontTable));
        table.addCell(new Phrase("Suma z dotacji", fontTable));
        table.addCell(new Phrase(data.get("donations") + " PLN", fontTable));
        float donation_tax = 0.3f;
        table.addCell(new Phrase("Procent z dotacji dla pracodawcy (" + (int)(donation_tax * 100) + "%)", fontTable));
        table.addCell(new Phrase(String.format("%.2f", ((Long)data.get("donations")).floatValue() * donation_tax) + " PLN", fontTable));
        table.addCell(new Phrase("Calkowite wynagrodzenie", fontTable));
        float reward = ((Long)data.get("donations")).floatValue() * (1.0f - donation_tax)
                + ((Long)data.get("hoursWatched")).floatValue() * 0.03f;
//                + ((Long)data.get("hoursWatched")).floatValue() * ((Long)data.get("hourRate")).floatValue();
        table.addCell(new Phrase( String.format("%.2f", reward) + " PLN", fontTable));
        document.add(table);
        document.add(new Paragraph("\n\n\n\n\n\n\n"));

        Path path = Paths.get("src/main/resources/images/documentovisco.png");
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scaleToFit(new Rectangle(0, 0, 210, 140));
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        Paragraph copyright = new Paragraph("Documentovisco ©", fontNormal);
        copyright.setAlignment(Element.ALIGN_CENTER);
        document.add(copyright);

        document.close();
        outputStream.close();

        String documentId = controller.createFile(new CustomMultipartFile(Paths.get(reportPath + reportFileName)));
        new File(reportPath + reportFileName).delete();
        return documentId;
    }
}
