package com.apogee.trackarea.model;

import lombok.Data;

import java.util.List;

@Data
public class PointsForm {
    private List<PointForm> points;

    @Data
    public static class PointForm{
        private Double x;
        private Double y;
    }
}
