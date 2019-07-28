package com.apogee.trackarea.model.data;

import com.apogee.trackarea.db.pojo.ReportPojo;
import lombok.Data;

import java.util.List;

@Data
public class ReportDetailsData {
    private List<ReportPojo> reports;
}
