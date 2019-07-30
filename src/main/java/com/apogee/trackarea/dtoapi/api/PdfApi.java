package com.apogee.trackarea.dtoapi.api;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.apogee.trackarea.db.pojo.ReportPojo;
import com.apogee.trackarea.db.pojo.UserProfilePojo;
import com.apogee.trackarea.helpers.algo.Point;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PdfApi {

    @Value("${access}")
    private String accessKey;
    @Value("${secret}")
    private String secretKey;

    @Autowired
    private ScatterChartApi scatterChartApi;

    String bucketName = "jar-storage-easy";


    //Writes File to Aws and returnsUrl
    public String writeFileToAwsS3(java.util.List<Point>actual, java.util.List<Point> points, ReportPojo report, UserProfilePojo profile) throws IOException, DocumentException {
        AWSCredentials awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return accessKey;
            }

            @Override
            public String getAWSSecretKey() {
                return secretKey;
            }
        };
        AmazonS3Client s3 = new AmazonS3Client(awsCredentials);
        String key = UUID.randomUUID().toString().substring(0,8);
        File file = File.createTempFile("AtulGupta-00-", ".pdf");
        file.deleteOnExit();

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        Image header = Image.getInstance("https://www.mpdage.org/LatestContent/img/img-mp-logo-en.gif");
        header.scaleToFit(500, 400);
        document.add(header);
        Map<String, String> tableCells = new LinkedHashMap<>();
        PdfPTable table = new PdfPTable(2);
        tableCells.put("Report No. : ", report.getReportId().toString());
        tableCells.put("Date : ", LocalDateTime.now().toString());
        tableCells.put("Name : ", profile.getName());
        tableCells.put("Geo Co-ordinate :", report.getStartGeoCordinate());
        tableCells.put("Area (sq metres) : ", report.getCalculatedArea().toString());

        for (String get : tableCells.keySet()) {
            PdfPCell cellOne = new PdfPCell(new Phrase(get));
            PdfPCell cellTwo = new PdfPCell(new Phrase(tableCells.get(get)));
            cellOne.setBorder(Rectangle.NO_BORDER);
            cellOne.setBackgroundColor(BaseColor.WHITE);
            cellTwo.setBorder(Rectangle.NO_BORDER);
            cellTwo.setBackgroundColor(BaseColor.WHITE);
            table.addCell(cellOne);
            table.addCell(cellTwo);
        }
        XYChart y = scatterChartApi.getChart(actual,points);
        File imgFile = File.createTempFile("ramadhir", ".jpg");
        imgFile.deleteOnExit();
        BitmapEncoder.saveBitmap(y, imgFile.getAbsolutePath(), BitmapEncoder.BitmapFormat.JPG);
//       VectorGraphicsEncoder.saveVectorGraphic(lon, imgFile.getAbsolutePath(), VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
        Image image = Image.getInstance(imgFile.getAbsolutePath());
        document.add(table);
        document.add(image);
        document.close();
        writer.close();
        s3.putObject(new PutObjectRequest(bucketName, key, file.getAbsoluteFile()));
        return s3.getResourceUrl(bucketName, key);
    }


    public Document generatePdfReportFile() throws IOException, DocumentException {
        Document document = new Document();
        document.open();
        PdfPTable table = new PdfPTable(2);
        PdfPCell cellOne = new PdfPCell(new Phrase("Report No."));
        PdfPCell cellTwo = new PdfPCell(new Phrase("123444"));

        cellOne.setBorder(Rectangle.NO_BORDER);
        cellOne.setBackgroundColor(BaseColor.BLACK);

        cellTwo.setBorder(Rectangle.BOX);

        table.addCell(cellOne);
        table.addCell(cellTwo);
        table.addCell(new PdfPCell(new Phrase("Date:")));
        table.addCell(new PdfPCell(new Phrase(LocalDateTime.now().toString())));

        document.add(table);
        document.close();
        return document;
    }
}