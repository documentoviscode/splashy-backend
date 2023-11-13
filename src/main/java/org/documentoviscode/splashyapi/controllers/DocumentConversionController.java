package org.documentoviscode.splashyapi.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.documentoviscode.splashyapi.data.CustomMultipartFile;
import org.documentoviscode.splashyapi.data.requests.MonthlyReportDTO;
import org.documentoviscode.splashyapi.domain.AdditionalPackage;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.services.AdditionalPackageService;
import org.documentoviscode.splashyapi.services.MonthlyReportService;
import org.documentoviscode.splashyapi.services.PartnershipContractService;
import org.documentoviscode.splashyapi.services.UserService;
import org.documentoviscode.splashyapi.utility.EmailService;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class DocumentConversionController{
    private final MonthlyReportService monthlyReportService;
    private final GoogleDriveController googleDriveController;
    private final PartnershipContractService partnershipContractService;
    private final EmailService emailService;
    private final AdditionalPackageService additionalPackageService;
    private final UserService userService;

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

    public void sendInvoice(Long packageId) throws MessagingException, IOException, DocumentException {
        AdditionalPackage addPackage = additionalPackageService.findAdditionalPackageById(packageId).get();
        User user = addPackage.getUser();

        String fileName = "src/main/resources/faktura.pdf";

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        FileOutputStream outputStream = new FileOutputStream(fileName);
        PdfWriter.getInstance(document, outputStream);

        Font fontHeader1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font fontHeader2 = FontFactory.getFont(FontFactory.HELVETICA, 16, 4, BaseColor.BLACK);
        Font fontBig = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont("Verdana", 11, BaseColor.DARK_GRAY);
        document.open();

        document.add(new Paragraph("Faktura nr: " + packageId + "/2023", fontHeader1));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Wystawiona w dniu: " + LocalDate.now() + ", Gdańsk", fontNormal));
        document.add(new Paragraph("\n\n\n"));

        PdfPTable table = new PdfPTable(2);
        table.addCell(new Phrase("Sprzedawca", fontHeader2));
        table.addCell(new Phrase("Nabywca", fontHeader2));
        table.addCell(new Paragraph("""
                splashyTV sp. z o.o.
                al. Grunwaldzka 420/69
                80-888 Gdańsk
                NIP 887-116-00-53
                splashytv.net""", fontBig));
        table.addCell(new Paragraph(user.getName() + "\n" + user.getSurname() + "\n" + user.getEmail(), fontBig));
        document.add(table);
        Paragraph p = new Paragraph("\n\n\n\nSposób zapłaty: karta płatnicza o nr **** **** **** 1234\n\n", fontNormal);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        double price = addPackage.getPrice();
        double vat = 23.0 / 77.0;
        table = new PdfPTable(6);
        table.addCell(new Phrase("Nazwa usługi", fontTable));
        table.addCell(new Phrase("Ilość", fontTable));
        table.addCell(new Phrase("Cena netto", fontTable));
        table.addCell(new Phrase("VAT %", fontTable));
        table.addCell(new Phrase("Kwota VAT", fontTable));
        table.addCell(new Phrase("Wartość brutto", fontTable));

        table.addCell(new Phrase(addPackage.getPackageType(), fontTable));
        table.addCell(new Phrase("1", fontTable));
        table.addCell(new Phrase(String.format("%.2f", price) + " PLN", fontTable));
        table.addCell(new Phrase("23 %", fontTable));
        table.addCell(new Phrase(String.format("%.2f", price * vat) + " PLN", fontTable));
        table.addCell(new Phrase(String.format("%.2f", price * (1.0 + vat)) + " PLN", fontTable));

        table.addCell("");
        table.addCell("");
        table.addCell(new Phrase("Razem:", fontTable));
        table.addCell("");
        table.addCell(new Phrase(String.format("%.2f", price * vat) + " PLN", fontTable));
        table.addCell(new Phrase(String.format("%.2f", price * (1.0 + vat)) + " PLN", fontTable));

        table.addCell("");
        table.addCell("");
        table.addCell(new Phrase("W tym:", fontTable));
        table.addCell("23 %");
        table.addCell(new Phrase(String.format("%.2f", price * vat) + " PLN", fontTable));
        table.addCell(new Phrase(String.format("%.2f", price * (1.0 + vat)) + " PLN", fontTable));

        document.add(table);
        document.add(new Paragraph("\n\n\nRazem do zapłaty: " + String.format("%.2f", price * (1.0 + vat))
                + " PLN\n\n\n", fontBig));

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

        emailService.sendInvoice("documentovisco@gmail.com", user.getName(),
                addPackage.getPackageType(), String.format("%.2f", price * (1.0 + vat)), fileName);
        new File(fileName).delete();
    }
}
