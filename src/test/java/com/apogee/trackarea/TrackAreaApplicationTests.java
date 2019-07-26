package com.apogee.trackarea;

import com.apogee.trackarea.api.PointApi;
import com.apogee.trackarea.pdf.JavaPdfHelloWorld;
import com.apogee.trackarea.pdf.ScatterChart01;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import io.minio.errors.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchart.VectorGraphicsEncoder;
import org.knowm.xchart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TrackAreaApplicationTests {

    @Autowired
    private PointApi pointApi;

    @Autowired
    private ScatterChart01 scatterChart01;

    @Autowired
    private JavaPdfHelloWorld javaPdfHelloWorld;
	@Test
	public void contextLoads() throws URISyntaxException {
        String topic        = "test";
        String content      = "Message from MqttPublishSample";
        int qos             = 2;
        String broker       = "tcp://localhost:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
	}


//	@Test
//    public void feedData() throws IOException {
//	    BufferedReader br = new BufferedReader( new FileReader("/home/atul/ramadhir.tsv"));
//	    String line = br.readLine();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
//
//        line = br.readLine();
//	    while (line != null){
//	        String[] values = line.split("\t");
//            PointPojo p = new PointPojo();
//            p.setX(Double.parseDouble(values[2]));
//            p.setY(Double.parseDouble(values[3]));
//            p.setReceivedAt(LocalDateTime.parse(values[4], formatter));
//            pointApi.savePoint(p);
//            line = br.readLine();
//        }
//    }
    @Test
    public void testPdf(){
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
            document.open();
            document.add(new Paragraph("A Hello World PDF document."));
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileCreation() throws IOException, InvalidKeyException, NoSuchAlgorithmException, XmlPullParserException, InvalidPortException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InvalidEndpointException, InternalException, DocumentException {
	    javaPdfHelloWorld.writeFile();
    }
    @Test
    public void testFileCreationAws() throws IOException, InvalidKeyException, NoSuchAlgorithmException, XmlPullParserException, InvalidPortException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InvalidEndpointException, InternalException, DocumentException {
        javaPdfHelloWorld.writeFileAws();
    }

    @Test
    public void testChart() throws IOException {
        XYChart y = scatterChart01.getChart();
        VectorGraphicsEncoder.saveVectorGraphic(y, "./ramadhirChar", VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
    }

}
