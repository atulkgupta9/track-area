package com.apogee.trackarea;

import com.apogee.trackarea.api.PointApi;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackAreaApplicationTests {
	@Autowired
	private PointApi api;


	@Test
	public void contextLoads() throws IOException {
		api.readAndPlotFile();
	}

}
