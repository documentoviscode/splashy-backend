package org.documentoviscode.splashyapi.utility.fileconversion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.documentoviscode.splashyapi.controllers.GoogleDriveController;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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

    public String generateMonthlyReportPDF(String fileId) throws IOException, DocumentException {
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
        PdfWriter.getInstance(document, new FileOutputStream(reportPath + reportFileName));

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

//        new File(reportPath + reportFileName).delete();
        return reportFileName;
    }

    public String generateMonthlyReportDOCX(String fileId) throws Exception {
        String reportPath = "src/main/resources/";
        String downloadedFileName = "downloaded.json";

        GoogleDriveController controller = new GoogleDriveController();
        File downloaded = new File(reportPath + downloadedFileName);
        try (FileOutputStream outputStream = new FileOutputStream(downloaded)) {
            outputStream.write(controller.downloadFile(fileId).getBody());
        }

        Document jsonDoc = new Document();
        jsonDoc.readFrom(reportPath + downloadedFileName);
        JSONObject data = ((DataJSON)jsonDoc.getData()).getKeys();
        downloaded.delete();

        JSONObject partner = new JSONObject(((List<Map>) data.get("users")).get(0));
        String reportFileName = "monthlyReport_" + partner.get("id") + "_" + data.get("creationDate") + ".docx";

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

        mainDocumentPart.addStyledParagraphOfText("Title", "Monthly Report");
        mainDocumentPart.addParagraphOfText("Date from: " + data.get("startDate"));
        mainDocumentPart.addParagraphOfText("Date to: " + data.get("endDate"));
        mainDocumentPart.addParagraphOfText("");
        mainDocumentPart.addParagraphOfText("Partner:");
        mainDocumentPart.addParagraphOfText("Name: " + partner.get("name"));
        mainDocumentPart.addParagraphOfText("Surname: " + partner.get("surname"));
        mainDocumentPart.addParagraphOfText("E-mail: " + partner.get("email"));

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
        byte[] fileContent = Files.readAllBytes(image.toPath());
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
                .createImagePart(wordPackage, fileContent);
        Inline inline = imagePart.createImageInline("", "", 1, 2,
                wordPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips() / 3,
                false);
        P Imageparagraph = addImageToParagraph(inline);
        mainDocumentPart.getContent().add(Imageparagraph);

        mainDocumentPart.addParagraphOfText("Documentovisco ©");


        File exportFile = new File(reportPath + reportFileName);
        wordPackage.save(exportFile);

//        new File(reportPath + reportFileName).delete();
        return reportFileName;
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
