package com.apogee.trackarea;

import com.apogee.trackarea.dtoapi.api.PointApi;
import com.apogee.trackarea.dtoapi.dto.DeviceDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.helpers.util.StartUpScript;
import com.itextpdf.text.DocumentException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class DeviceDtoTest extends TrackAreaApplicationTests {

    @Autowired
    private DeviceDto deviceDto;

    @Autowired
    private PointApi pointApi;
    @Autowired
    private StartUpScript startUpScript;
    @Test
    public void testReports() throws IOException, ApiException, DocumentException {
        startUpScript.createUsers();
        deviceDto.runJob();
    }
}
