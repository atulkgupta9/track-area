package com.apogee.trackarea.pdf;

import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.api.PointApi;
import com.apogee.trackarea.model.HullAreaData;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Gaussian Blob
 *
 * Demonstrates the following:
 * <ul>
 * <li>ChartType.Scatter
 * <li>Series data as a Set
 * <li>Setting marker size
 * <li>Formatting of negative numbers with large magnitude but small differences
 */
@Service
public class ScatterChart01 {

    @Autowired
    private PointApi pointApi;

    public XYChart getChart(){
        XYChart chart = new XYChartBuilder().width(500).height(300).build();

        // Customize Chart
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(07);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartFontColor(Color.BLACK);
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.setTitle("Points Covered by device");
        // Series
        List<Double> xData = new LinkedList<Double>();
        List<Double> yData = new LinkedList<Double>();
        HullAreaData areaData = pointApi.getConvexHullPoints2();

        for(Point point : areaData.getPolygon()){
            xData.add(point.x);
            yData.add(point.y);
        }
        if(areaData.getPolygon() != null && !areaData.getPolygon().isEmpty()){
            xData.add(areaData.getPolygon().get(0).x);
            yData.add(areaData.getPolygon().get(0).y);

        }
        XYSeries series = chart.addSeries( "Area", xData, yData);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setMarkerColor(Color.RED);
        series.setLineColor(new Color(127,58,153));
        return chart;

    }

}