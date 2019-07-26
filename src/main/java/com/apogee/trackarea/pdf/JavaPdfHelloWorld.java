package com.apogee.trackarea.pdf;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JavaPdfHelloWorld {

    @Value("${access}")
            private String accessKey;
    @Value("${secret}")
            private String secretKey;

    @Autowired
            private ScatterChart01 scatterChart01;
    String bucketName = "jar-storage-easy";
   public void writeFile() throws IOException, InvalidPortException, InvalidEndpointException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, DocumentException {
       // Set s3 endpoint, region is calculated automatically
       MinioClient s3Client = new MinioClient("https://s3.amazonaws.com", accessKey, secretKey);
       Document document = new Document();

       File file = File.createTempFile("AtulGupta", ".pdf");
       file.deleteOnExit();


       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
       document.open();
       document.add(new Paragraph("A Hello World PDF document."));
       document.close();
       writer.close();

       // create object
       s3Client.putObject(bucketName, file.getName(), file.getAbsolutePath());

   }

   public void writeFileAws() throws IOException, DocumentException {
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
       String key = "a-66";
       File file = File.createTempFile("AtulGupta-00-", ".pdf");
       file.deleteOnExit();

       Document document = new Document();

       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
       document.open();
       Image header = Image.getInstance("https://www.mpdage.org/LatestContent/img/img-mp-logo-en.gif");
       header.scaleToFit(500,400);
       document.add(header);
       Map<String,String> tableCells = new LinkedHashMap<>();
       PdfPTable table = new PdfPTable(2);
       tableCells.put("Report No. : ", "123444");
       tableCells.put("Date : ", LocalDateTime.now().toString());
       tableCells.put("District : ", "Gwalior");
       tableCells.put("Village : ", "Datia");
       tableCells.put("Name : ", "Raghuveer");
       tableCells.put("Area (sq metres) : ", "193.34");

       for(String get : tableCells.keySet()){
           PdfPCell cellOne = new PdfPCell(new Phrase(get));
           PdfPCell cellTwo = new PdfPCell(new Phrase(tableCells.get(get)));
           cellOne.setBorder(Rectangle.NO_BORDER);
           cellOne.setBackgroundColor(BaseColor.WHITE);
           cellTwo.setBorder(Rectangle.NO_BORDER);
           cellTwo.setBackgroundColor(BaseColor.WHITE);
           table.addCell(cellOne);
           table.addCell(cellTwo);
       }
       XYChart y = scatterChart01.getChart();
       File imgFile = File.createTempFile("ramadhir", ".jpg");
       imgFile.deleteOnExit();
       BitmapEncoder.saveBitmap(y, imgFile.getAbsolutePath(),  BitmapEncoder.BitmapFormat.JPG);
//       VectorGraphicsEncoder.saveVectorGraphic(y, imgFile.getAbsolutePath(), VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
       Image image = Image.getInstance(imgFile.getAbsolutePath());
       document.add(table);
       document.add(image);
       document.close();
       writer.close();
       s3.putObject(new PutObjectRequest(bucketName, key, file.getAbsoluteFile()));
       System.out.println(s3.getResourceUrl(bucketName, key));


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