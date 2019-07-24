package com.apogee.trackarea;

import com.apogee.trackarea.util.DegToUtm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackAreaApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("threads" +Thread.activeCount());
			DegToUtm UTM = new DegToUtm(2231.6845713, 07255.1797747);
		System.out.println(UTM.getEasting()+ " "+ UTM.getNorthing());
	}

}
