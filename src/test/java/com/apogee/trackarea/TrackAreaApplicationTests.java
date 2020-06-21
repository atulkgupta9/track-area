package com.apogee.trackarea;

import com.apogee.trackarea.config.SchedulerConfig;
import com.apogee.trackarea.helpers.util.StartUpScript;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ComponentScan(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        value = {SchedulerConfig.class, StartUpScript.class}))


public class TrackAreaApplicationTests {
    @Test
    public void init() {

    }
}
