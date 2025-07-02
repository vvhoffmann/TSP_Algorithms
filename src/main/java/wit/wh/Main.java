package wit.wh;


import wit.wh.algorithms.QuasiOptimizationSolution.GrahamAlgorithm;

import wit.wh.algorithms.SolutionType;

import wit.wh.algorithms.TSPSolution;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;
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


/**
 * Main application window that visualizes TSP solution and convex hull using JFreeChart.
 * The class generates random points, computes their convex hull and TSP solution,
 * and displays the result in a Swing window.
 */
public class Main extends JFrame {
    private static final int POINT_COUNT = 15;
    private static final int WINDOW_SIZE = 850;

    private final XYSeriesCollection dataset = new XYSeriesCollection();
    private final ArrayList<Point> allPoints;
    private final ArrayList<Point> convexHullPoints;
    private final ArrayList<Point> solutionPoints;
    private final String title;

    /**
     * Constructs the main window and initializes the TSP and Convex Hull solutions.
     */
    public Main() {
        this.allPoints = PointUtils.getReadyPoints();
        //this.allPoints = PointUtils.generateRandomPoints(POINT_COUNT);

        this.convexHullPoints = GrahamAlgorithm.getRoundConvexHull(allPoints);
        SolutionType solutionType = SolutionType.QUASI_OPTIMIZATION_ALGORITHM;
        //SAParameters parameters = new SAParameters(1000,0.01,10000, 0.995, 0.1);
        //this.solutionPoints = TSPSolutionFactory.createSolutionWithParams(SolutionType.SA_ALGORITHM, allPoints, parameters).getSolutionPoints();
        TSPSolution solution = TSPSolutionFactory.createSolution(solutionType, allPoints);
        solutionPoints = solution.getSolutionPoints();
        title = solutionType.getName();
        System.out.println(title + " - długość drogi: " +  solution.getRouteLength());

        addSeriesToDataset();
        JFreeChart chart = createChart();
        ChartPanel chartPanel = createChartPanel(chart);
        configurePlot(chart);

        setContentPane(chartPanel);
    }

    /**
     * Adds the TSP and convex hull point segments to the dataset for plotting.
     */
    private void addSeriesToDataset() {
        createPointConnections(solutionPoints, "TSPSolution");
        createPointConnections(convexHullPoints, "ConvexHull");
    }

    /**
     * Creates line segments between consecutive points and adds them to the dataset.
     *
     * @param seriesPoints Points representing either the convex hull or TSP path.
     * @param name         Name prefix for the series (used in chart labeling).
     */
    private void createPointConnections(ArrayList<Point> seriesPoints, String name) {
        XYSeries segment;

        for (int i = 0; i < seriesPoints.size() - 1; ++i) {
            segment = new XYSeries(name + " SEGMENT" + (i + 1));
            segment.add(seriesPoints.get(i).x, seriesPoints.get(i).y);
            segment.add(seriesPoints.get(i + 1).x, seriesPoints.get(i + 1).y);
            dataset.addSeries(segment);
        }
    }

    /**
     * Creates and configures the chart that visualizes the dataset.
     *
     * @return A configured {@link JFreeChart} object.
     */
    private JFreeChart createChart() {
        return ChartFactory.createXYLineChart(
                title,
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }

    /**
     * Creates and configures the chart panel.
     *
     * @param chart The chart to be embedded in the panel.
     * @return A configured {@link ChartPanel}.
     */
    private static ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        return chartPanel;
    }

    /**
     * Configures the plot style and annotations.
     *
     * @param chart The chart whose plot is to be configured.
     */
    private void configurePlot(JFreeChart chart) {
        XYLineAndShapeRenderer renderer = getXyLineAndShapeRenderer();

        final XYPlot plot = chart.getXYPlot();
        addAnnotation(plot);
        plot.setRenderer(renderer);
    }

    /**
     * Creates and configures the line renderer used for drawing the chart.
     *
     * @return A configured {@link XYLineAndShapeRenderer}.
     */
    private XYLineAndShapeRenderer getXyLineAndShapeRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        configureRerenderForSolutionPoints(renderer);
        configureRerenderForConvexHullPoints(renderer);
        return renderer;
    }

    /**
     * Configures the rendering style for the convex hull segments.
     *
     * @param renderer The renderer to be configured.
     */
    private void configureRerenderForConvexHullPoints(XYLineAndShapeRenderer renderer) {
        for (int i = solutionPoints.size() -1; i < solutionPoints.size() + convexHullPoints.size() ; ++i) {
            renderer.setSeriesPaint(i, Color.RED);
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }
    }

    /**
     * Configures the rendering style for the TSP solution segments.
     *
     * @param renderer The renderer to be configured.
     */
    private void configureRerenderForSolutionPoints(XYLineAndShapeRenderer renderer) {
        for (int i = 0; i < solutionPoints.size(); ++i) {
            renderer.setSeriesPaint(i, Color.YELLOW);
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }
    }

    /**
     * Adds coordinate annotations to each point on the chart for easier identification.
     *
     * @param plot The plot to which annotations are added.
     */
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

    /**
     * Application entry point.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main chartWindow = new Main();
            chartWindow.pack();
            chartWindow.setVisible(true);
        });
    }
}