package com.apogee.trackarea.dtoapi.api;

import com.apogee.trackarea.helpers.algo.Point;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;
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
public class ScatterChartApi {

    public XYChart getChart(List<Point>actual, List<Point>polygonPoints){
        XYChart chart = new XYChartBuilder().width(400).height(200).build();

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

        for(Point point : polygonPoints){
            xData.add(point.x);
            yData.add(point.y);
        }
        if(polygonPoints  != null && !polygonPoints.isEmpty()){
            xData.add(polygonPoints.get(0).x);
            yData.add(polygonPoints.get(0).y);

        }
        XYSeries series = chart.addSeries( "Area", xData, yData);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setMarkerColor(Color.RED);
        series.setLineColor(new Color(127,58,153));

        xData = new LinkedList<>();
        yData = new LinkedList<>();
        for(Point point : actual){
            xData.add(point.x);
            yData.add(point.y);
        }
        if(actual  != null && !actual.isEmpty()){
            xData.add(actual.get(0).x);
            yData.add(actual.get(0).y);

        }
        XYSeries series1 = chart.addSeries("ActualPoints", xData, yData);
        series1.setMarker(SeriesMarkers.DIAMOND);
        series1.setMarkerColor(Color.GREEN);
        return chart;

    }

}