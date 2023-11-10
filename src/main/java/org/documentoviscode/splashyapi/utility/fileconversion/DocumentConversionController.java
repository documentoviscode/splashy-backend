package org.documentoviscode.splashyapi.utility.fileconversion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.documentoviscode.splashyapi.controllers.GoogleDriveController;
import org.documentoviscode.splashyapi.data.CustomMultipartFile;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Component
public class DocumentConversionController{
    private final ApplicationContext applicationContext;

    public DocumentConversionController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String generateMonthlyReportPDF(String fileId) throws Exception {
        String reportPath = "src/main/resources/";
        String downloadedFileName = "downloaded.json";

        GoogleDriveController controller = applicationContext.getBean(GoogleDriveController.class);

        File downloaded = new File(reportPath + downloadedFileName);
        try (FileOutputStream outputStream = new FileOutputStream(downloaded)) {
            outputStream.write(controller.downloadFile(fileId).getBody());
        }

        Document jsonDoc = new Document();
        jsonDoc.readFrom(reportPath + downloadedFileName);
        JSONObject data = ((DataJSON)jsonDoc.getData()).getKeys();
        downloaded.delete();

        JSONObject partner = new JSONObject(((List<Map>) data.get("users")).get(0));
        String reportFileName = "monthlyReport_" + partner.get("id") + "_" + data.get("creationDate") + ".pdf";

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        FileOutputStream outputStream = new FileOutputStream(reportPath + reportFileName);
        PdfWriter.getInstance(document, outputStream);

        Font fontHeader1 = FontFactory.getFont(FontFactory.HELVETICA, 32, BaseColor.BLACK);
        Font fontHeader2 = FontFactory.getFont(FontFactory.HELVETICA, 24, BaseColor.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
        document.open();

        Paragraph header = new Paragraph("Monthly Report", fontHeader1);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date from: " + data.get("startDate"), fontNormal));
        document.add(new Paragraph("Date to: " + data.get("endDate"), fontNormal));
        document.add(new Paragraph("\n\n"));

        document.add(new Paragraph("Partner:", fontHeader2));
        document.add(new Paragraph("Name: " + partner.get("name"), fontNormal));
        document.add(new Paragraph("Surname: " + partner.get("surname"), fontNormal));
        document.add(new Paragraph("E-mail: " + partner.get("email"), fontNormal));
        document.add(new Paragraph("\n\n\n"));

        PdfPTable table = new PdfPTable(2);
        table.addCell("Number of viewers");
        table.addCell(data.get("viewers").toString());
        table.addCell("Number of hours watched");
        table.addCell(data.get("hoursWatched").toString());
        table.addCell("Total donations");
        table.addCell(data.get("donations") + " $");
        document.add(table);
        document.add(new Paragraph("\n\n\n\n\n\n\n"));

        Path path = Paths.get("src/main/resources/images/documentovisco.png");
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scaleToFit(new Rectangle(0, 0, 300, 200));
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        Paragraph copyright = new Paragraph("Documentovisco ©", fontNormal);
        copyright.setAlignment(Element.ALIGN_CENTER);
        document.add(copyright);

        document.close();
        outputStream.close();

        controller.createFile(new CustomMultipartFile(Paths.get(reportPath + reportFileName)));
        new File(reportPath + reportFileName).delete();
        return reportFileName;
    }
}
