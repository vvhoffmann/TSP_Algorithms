package org.example;


import org.example.Algorithms.AntColony.AntColonyOptimization;
import org.example.Algorithms.QuasiOptimalAlgorithm.QuasiOptimalAlgorithm;
import org.example.Algorithms.SAAlgorithm.SAAlgorithm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Main extends JFrame {
    private final XYSeriesCollection dataset = new XYSeriesCollection();
    private final ArrayList<Point> points;
    private ArrayList<Point> convexHullPoints;
    private ArrayList<Point> solutionPoints;

    public Main() {
        points = Point.getReadyPoints(); //createRandomPoints(100);
        QuasiOptimalAlgorithm quasiOptimalAlgorithm = new QuasiOptimalAlgorithm(points);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(points);
        convexHullPoints = quasiOptimalAlgorithm.getConvexHull();
        solutionPoints =
                //HeldKarpAlgorithm.getTSPSolution(points);
                //
                //NearestNeighbourAlgorithm.getTSPSolution(points);
                //
                //NearestNeighbourAlgorithm.getTSPSolutionKK(points);
                //
                //antColonyOptimization.getTSPSolution();
                //
                SAAlgorithm.getTSPSolution(points);
                //
                //quasiOptimalAlgorithm.getTSPSolution();
                //
                //GrahamAlgorithm.convexHullFinder(points);

        createPointConnections(solutionPoints,"RESULT");
        createPointConnections(convexHullPoints, "CH");

        JFreeChart chart = ChartFactory.createXYLineChart(
                "TSP", // Chart title
                "x", // X-Axis Label
                "y", // Y-Axis Label
                dataset ,
                PlotOrientation.VERTICAL ,
                false , true , false);


        final XYPlot plot = chart.getXYPlot( );
        addAnnotation(plot);
        ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new Dimension( 850 , 850 ) );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true,false );

        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        for(int i = 0; i< solutionPoints.size(); ++i) {
            renderer.setSeriesPaint( i , Color.YELLOW );
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }
        for(int i = solutionPoints.size(); i< solutionPoints.size()+convexHullPoints.size()-1; ++i) {
            renderer.setSeriesPaint( i , Color.RED );
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }

        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private void addAnnotation(XYPlot plot)
    {
        for(int i=0;i<points.size();++i)
        {
            Point2D p = new Point2D.Double(points.get(i).x, points.get(i).y);

            String label = i + " " + points.get(i).toString();
            XYTextAnnotation annotation = new XYTextAnnotation(label, p.getX(), p.getY());
            annotation.setFont(new Font("TimesRoman", Font.PLAIN, 14));
            annotation.setPaint(Color.DARK_GRAY);
            annotation.setTextAnchor(TextAnchor.TOP_CENTER);
            plot.addAnnotation(annotation);
        }
    }

    private void createPointConnections(ArrayList<Point> seriesPoints, String name)
    {
        XYSeries segment;

        segment = new XYSeries( name+" SEGMENT 0" );
        segment.add(seriesPoints.get(0).x, seriesPoints.get(0).y);
        segment.add(seriesPoints.get(seriesPoints.size()-1).x, seriesPoints.get(seriesPoints.size()-1).y);
        dataset.addSeries(segment);

        for(int i = 0; i< seriesPoints.size()-1; ++i)
        {
            segment = new XYSeries( name + " SEGMENT"+ (i+1));
            segment.add(seriesPoints.get(i).x, seriesPoints.get(i).y);
            segment.add(seriesPoints.get(i+1).x, seriesPoints.get(i+1).y);
            dataset.addSeries(segment);
        }
    }

    public static void main(String[] args) {
        Main chart = new Main();
        chart.pack( );
        chart.setVisible( true );
    }
}