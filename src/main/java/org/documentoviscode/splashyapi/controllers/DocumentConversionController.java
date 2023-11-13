package org.documentoviscode.splashyapi.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.documentoviscode.splashyapi.data.CustomMultipartFile;
import org.documentoviscode.splashyapi.data.requests.MonthlyReportDTO;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.services.MonthlyReportService;
import org.documentoviscode.splashyapi.services.PartnershipContractService;
import org.documentoviscode.splashyapi.utility.fileconversion.DataJSON;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class DocumentConversionController{
    private final MonthlyReportService monthlyReportService;
    private final GoogleDriveController googleDriveController;
    private final PartnershipContractService partnershipContractService;

    private String generateMonthlyReport(Long reportId) throws Exception {
        MonthlyReport report = monthlyReportService.findMonthlyReportById(reportId).get();
        String template = "src/main/resources/monthlyReport_" + reportId + ".json";
        JSONObject json = new JSONObject();
        PartnershipContract contract = new PartnershipContract();
        for (org.documentoviscode.splashyapi.domain.Document d: report.getUser().getDocuments()) {
            Optional<PartnershipContract> c = partnershipContractService.findPartnershipContractById(d.getId());
            if (c.isPresent() && c.get().getEndDate().equals(report.getEndDate())) {
                contract = c.get();
                break;
            }
        }

        json.put("id", report.getId());
        json.put("type", "JSON");
        json.put("creationDate", report.getCreationDate().toString());
        json.put("partnerName", report.getUser().getName());
        json.put("partnerSurname", report.getUser().getSurname());
        json.put("partnerEmail", report.getUser().getEmail());
        json.put("startDate", report.getStartDate().toString());
        json.put("endDate", report.getEndDate().toString());
        json.put("viewers", report.getViewers());
        json.put("hoursWatched", report.getHoursWatched());
        json.put("donations", report.getDonations());
        json.put("rate", contract.getRate());
        json.put("donationPercentage", contract.getDonationPercentage());
        DataJSON data = new DataJSON();
        data.setKeys(json);

        org.documentoviscode.splashyapi.utility.fileconversion.Document docJSON =
                new org.documentoviscode.splashyapi.utility.fileconversion.Document(data);
        docJSON.saveTo(template);

        String fileId = googleDriveController.createFile(new CustomMultipartFile(Path.of(template)), report.getUser().getId());
        report.setGDriveLink(fileId);
        MonthlyReportDTO updated = new MonthlyReportDTO();
        updated.setGDriveLink(fileId);
        monthlyReportService.updateMonthlyReport(reportId, updated);
        new File(template).delete();
        return fileId;
    }

    @GetMapping(value = { "/monthlyReportPartner/{reportId}" })
    public ResponseEntity<byte[]> generatePartnerMonthlyReport(@PathVariable(name = "reportId") Long reportId) throws Exception {
        String reportPath = "src/main/resources/";
        String downloadedFileName = "downloaded.json";

        String fileId = monthlyReportService.findMonthlyReportById(reportId).get().getGDriveLink();
        if (fileId == null || fileId.startsWith("<GLinkDrive")) fileId = generateMonthlyReport(reportId);

        File downloaded = new File(reportPath + downloadedFileName);
        try (FileOutputStream outputStream = new FileOutputStream(downloaded)) {
            outputStream.write(googleDriveController.downloadFile(fileId).getBody());
        }

        org.documentoviscode.splashyapi.utility.fileconversion.Document jsonDoc =
                new org.documentoviscode.splashyapi.utility.fileconversion.Document();
        jsonDoc.readFrom(reportPath + downloadedFileName);
        JSONObject data = ((DataJSON)jsonDoc.getData()).getKeys();
        downloaded.delete();

        String reportFileName = "monthlyReport_" + data.get("creationDate") + ".pdf";

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
        document.add(new Paragraph("Imie: " + data.get("partnerName"), fontNormal));
        document.add(new Paragraph("Nazwisko: " + data.get("partnerSurname"), fontNormal));
        document.add(new Paragraph("E-mail: " + data.get("partnerEmail"), fontNormal));
        Paragraph p = new Paragraph("\n\nRozliczenie:\n\n", fontNormal);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        double donation_tax = (double) data.get("donationPercentage");
        double rate = (double) data.get("rate");
        PdfPTable table = new PdfPTable(new float[]{0.7f, 0.3f});
        table.setPaddingTop(1);
        table.addCell(new Phrase("Liczba ogladajacych", fontTable));
        table.addCell(new Phrase(data.get("viewers").toString(), fontTable));
        table.addCell(new Phrase("Suma obejrzanych godzin", fontTable));
        table.addCell(new Phrase(data.get("hoursWatched").toString(), fontTable));
        table.addCell(new Phrase("Stawka za godzine ogladalnosci", fontTable));
        table.addCell(new Phrase(String.format("%.2f", rate) + " PLN", fontTable));
        table.addCell(new Phrase("Suma z dotacji", fontTable));
        table.addCell(new Phrase(String.format("%.2f", (double)data.get("donations")) + " PLN", fontTable));
        table.addCell(new Phrase("Procent z dotacji dla pracodawcy (" + (int)(donation_tax) + "%)", fontTable));
        table.addCell(new Phrase(String.format("%.2f", ((double )data.get("donations")) * donation_tax / 100) + " PLN", fontTable));
        table.addCell(new Phrase("Calkowite wynagrodzenie", fontTable));
        double reward = ((double)data.get("donations")) * (1.0 - donation_tax / 100.0)
                + ((double)data.get("hoursWatched")) * rate;
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

        byte[] fileContent = Files.readAllBytes(Path.of(reportPath + reportFileName));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("attachment", reportFileName);

        new File(reportPath + reportFileName).delete();
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping(value = { "/monthlyReportCompany/{reportId}" })
    public ResponseEntity<byte[]> generateMonthlyReportDOCX(@PathVariable(name = "reportId") Long reportId) throws Exception {
        String reportPath = "src/main/resources/";
        String downloadedFileName = "downloaded.json";

        String fileId = monthlyReportService.findMonthlyReportById(reportId).get().getGDriveLink();
        if (fileId == null || fileId.startsWith("<GLinkDrive")) fileId = generateMonthlyReport(reportId);

        File downloaded = new File(reportPath + downloadedFileName);
        try (FileOutputStream outputStream = new FileOutputStream(downloaded)) {
            outputStream.write(googleDriveController.downloadFile(fileId).getBody());
        }

        org.documentoviscode.splashyapi.utility.fileconversion.Document jsonDoc =
                new org.documentoviscode.splashyapi.utility.fileconversion.Document();
        jsonDoc.readFrom(reportPath + downloadedFileName);
        JSONObject data = ((DataJSON)jsonDoc.getData()).getKeys();
        downloaded.delete();

        String reportFileName = "monthlyReport_" + data.get("creationDate") + ".pdf";

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

        mainDocumentPart.addStyledParagraphOfText("Title", "Monthly Report");
        mainDocumentPart.addParagraphOfText("Date from: " + data.get("startDate"));
        mainDocumentPart.addParagraphOfText("Date to: " + data.get("endDate"));
        mainDocumentPart.addParagraphOfText("");
        mainDocumentPart.addParagraphOfText("Partner:");
        mainDocumentPart.addParagraphOfText("Name: " + data.get("partnerName"));
        mainDocumentPart.addParagraphOfText("Surname: " + data.get("partnerSurname"));
        mainDocumentPart.addParagraphOfText("E-mail: " + data.get("partnerEmail"));

        ObjectFactory factory = Context.getWmlObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        r.getContent().add(t);
        p.getContent().add(r);

        int writableWidthTwips = wordPackage.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();
        int columnNumber = 2;
        Tbl tbl = TblFactory.createTable(3, columnNumber, writableWidthTwips/columnNumber);
        addCellContent(tbl, 0, 0, "Number of viewers");
        addCellContent(tbl, 0, 1, data.get("viewers").toString());
        addCellContent(tbl, 1, 0, "Number of hours watched");
        addCellContent(tbl, 1, 1, data.get("hoursWatched").toString());
        addCellContent(tbl, 2, 0, "Total donations");
        addCellContent(tbl, 2, 1, data.get("donations") + " $");

        mainDocumentPart.addParagraphOfText("");
        mainDocumentPart.getContent().add(tbl);
        mainDocumentPart.addParagraphOfText("");
        mainDocumentPart.addParagraphOfText("");
        mainDocumentPart.addParagraphOfText("");

        File image = new File("src/main/resources/images/documentovisco.png");
        byte[] imageContent = Files.readAllBytes(image.toPath());
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
                .createImagePart(wordPackage, imageContent);
        Inline inline = imagePart.createImageInline("", "", 1, 2,
                wordPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips() / 3,
                false);
        P Imageparagraph = addImageToParagraph(inline);
        mainDocumentPart.getContent().add(Imageparagraph);

        mainDocumentPart.addParagraphOfText("Documentovisco ©");


        File exportFile = new File(reportPath + reportFileName);
        wordPackage.save(exportFile);

        byte[] fileContent = Files.readAllBytes(Path.of(reportPath + reportFileName));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDispositionFormData("attachment", reportFileName);

        new File(reportPath + reportFileName).delete();
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    private static void addCellContent(Tbl table, int row, int col, String value) {
        ObjectFactory factory = Context.getWmlObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue(value);
        r.getContent().add(t);
        p.getContent().add(r);

        ((Tc)((Tr)table.getContent().get(row)).getContent().get(col)).getContent().add(p);
    }

    private static P addImageToParagraph(Inline inline) {
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Drawing drawing = factory.createDrawing();
        r.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }
}
