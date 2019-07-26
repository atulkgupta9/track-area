package com.apogee.trackarea;

import com.apogee.trackarea.util.DegToUtm;
import com.apogee.trackarea.util.GeoConvert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

public class GeoUtmTest {

    @Test
    public void testConvertToUTM() throws Exception {

        double[] utm = GeoConvert.toUtm(48.188767, 16.418349);
        assertEquals(199753.6610440401, utm[0], 0.0001);
        assertEquals(1817294.8635275129, utm[1], 0.0001);
        // double xy1[] = { 48.201566, 16.353718 };
        // double xy2[] = { 48.205227, 16.363395 };
        // int[] cords = GeoConvert.getPos(48.2039039, 16.3574814, xy1, xy2,
        // 300, 300);
        // System.out.println("Cords: " + cords[0] + " " + cords[1] );
        BufferedReader br = new BufferedReader(new FileReader("/home/atul/Downloads/Terminal_52.log"));
        for(int i = 0;; i++){
            String line = br.readLine();
            if(line == null){
                break;
            }
            String values[] = line.split(",");
            line = br.readLine();
            String expected[] = line.split(",");

            //lat = 22, long = 72
            double lat = Double.parseDouble(values[2])*0.01;
            double lon = Double.parseDouble(values[4])*0.01;

            double transformedLat = (Math.floor(lat)*60+ ((lat-Math.floor(lat))*100) )/60;
            double transformedLon = (Math.floor(lon)*60+ ((lon-Math.floor(lon))*100) )/60;

            System.out.println("lat : "+transformedLat  +  " long : " + transformedLon );
            utm = GeoConvert.toUtm(transformedLon , transformedLat);
            System.out.println(utm[0] + " " + utm[1]);
            DegToUtm ans = new DegToUtm(transformedLat, transformedLon);
            System.out.println(ans.getEasting() + " " + ans.getNorthing());
        }


    }


}
