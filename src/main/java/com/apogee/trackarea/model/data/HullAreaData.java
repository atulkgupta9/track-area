package com.apogee.trackarea.model.data;


import com.apogee.trackarea.helpers.algo.Point;
import lombok.Data;

import java.util.List;

@Data
public class HullAreaData {
    private List<Point> polygon ;
    private Double area;
}
