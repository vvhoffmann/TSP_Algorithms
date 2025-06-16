package org.example;


import org.example.Algorithms.QuasiOptimizationAlgorithm.GrahamAlgorithm;
import org.example.pointUtils.Point;
import org.example.pointUtils.PointUtils;
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
    private static final int POINT_COUNT = 25;
    private static final int WINDOW_SIZE = 850;
    private static final int FONT_SIZE = 14;

    private final XYSeriesCollection dataset = new XYSeriesCollection();
    private final ArrayList<Point> allPoints;
    private final ArrayList<Point> convexHullPoints;
    private final ArrayList<Point> solutionPoints;

    public Main() {
        this.allPoints = PointUtils.createRandomPoints(POINT_COUNT);//Point.getReadyPoints();
        this.convexHullPoints = GrahamAlgorithm.getConvexHull(allPoints);
        this.solutionPoints = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, allPoints);

        addSeriesToDataset();
        JFreeChart chart = createChart();
        ChartPanel chartPanel = createChartPanel(chart);
        configurePlot(chart);

        setContentPane(chartPanel);
    }

    private void addSeriesToDataset() {
        createPointConnections(solutionPoints, "TSPSolution");
        createPointConnections(convexHullPoints, "ConvexHull");
    }

    private void createPointConnections(ArrayList<Point> seriesPoints, String name) {
        XYSeries segment;

        segment = new XYSeries(name + " SEGMENT 0");
        segment.add(seriesPoints.get(0).x, seriesPoints.get(0).y);
        segment.add(seriesPoints.get(seriesPoints.size() - 1).x, seriesPoints.get(seriesPoints.size() - 1).y);
        dataset.addSeries(segment);

        for (int i = 0; i < seriesPoints.size() - 1; ++i) {
            segment = new XYSeries(name + " SEGMENT" + (i + 1));
            segment.add(seriesPoints.get(i).x, seriesPoints.get(i).y);
            segment.add(seriesPoints.get(i + 1).x, seriesPoints.get(i + 1).y);
            dataset.addSeries(segment);
        }
    }

    private JFreeChart createChart() {
        return ChartFactory.createXYLineChart(
                "TSP Solver", // Chart title
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }

    private static ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        return chartPanel;
    }

    private void configurePlot(JFreeChart chart) {
        XYLineAndShapeRenderer renderer = getXyLineAndShapeRenderer();

        final XYPlot plot = chart.getXYPlot();
        addAnnotation(plot);
        plot.setRenderer(renderer);
    }

    private XYLineAndShapeRenderer getXyLineAndShapeRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        configureRerenderForSolutionPoints(renderer);
        configureRerenderForConvexHullPoints(renderer);
        return renderer;
    }

    private void configureRerenderForConvexHullPoints(XYLineAndShapeRenderer renderer) {
        for (int i = solutionPoints.size(); i < solutionPoints.size() + convexHullPoints.size() - 1; ++i) {
            renderer.setSeriesPaint(i, Color.RED);
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }
    }

    private void configureRerenderForSolutionPoints(XYLineAndShapeRenderer renderer) {
        for (int i = 0; i < solutionPoints.size(); ++i) {
            renderer.setSeriesPaint(i, Color.YELLOW);
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }
    }

    private void addAnnotation(XYPlot plot) {
        for (int i = 0; i < allPoints.size(); ++i) {
            Point2D p = new Point2D.Double(allPoints.get(i).x, allPoints.get(i).y);
            String label = i + " " + allPoints.get(i).toString();

            XYTextAnnotation annotation = new XYTextAnnotation(label, p.getX(), p.getY());
            annotation.setFont(new Font("TimesRoman", Font.PLAIN, 14));
            annotation.setPaint(Color.DARK_GRAY);
            annotation.setTextAnchor(TextAnchor.TOP_CENTER);
            plot.addAnnotation(annotation);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main chartWindow = new Main();
            chartWindow.pack();
            chartWindow.setVisible(true);
        });
    }
}