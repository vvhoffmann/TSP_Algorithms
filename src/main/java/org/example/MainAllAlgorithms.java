package org.example;

import org.example.Algorithms.AntColony.AntColonyOptimization;
import org.example.Algorithms.HeldKarpAlgorithm;
import org.example.Algorithms.NearestNeighbourAlgorithm;
import org.example.Algorithms.QuasiOptimalAlgorithm.GrahamAlgorithm;
import org.example.Algorithms.QuasiOptimalAlgorithm.QuasiOptimalAlgorithm;
import org.example.Algorithms.RepetitiveNearestNeighbourAlgorithm;
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

import static org.example.Point.createRandomPoints;

public class MainAllAlgorithms extends JFrame {
    private final ArrayList<Point> points;
    private final ArrayList<ArrayList<Point>> solutions = new ArrayList<>();
    private final String[] algorithmNames = {"Held-Karp", "Nearest Neighbour", "Repetitive Nearest Neighbour", "Ant Colony", "Simulated Annealing", "Quasi Optimal"};

    public MainAllAlgorithms() {
        points = createRandomPoints(20);

        // Oblicz rozwiązania dla każdego algorytmu
        solutions.add(HeldKarpAlgorithm.getTSPSolution(points));
        solutions.add(NearestNeighbourAlgorithm.getTSPSolution(points));
        solutions.add(RepetitiveNearestNeighbourAlgorithm.getTSPSolution(points));
        solutions.add(new AntColonyOptimization(points).getTSPSolution());
        solutions.add(SAAlgorithm.getTSPSolution(points));
        solutions.add(new QuasiOptimalAlgorithm(points).getTSPSolution());

        setLayout(new GridLayout(2, 3)); // Układ siatki 2x3
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < solutions.size(); i++) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            createPointConnections(solutions.get(i), dataset);
            JFreeChart chart = createChart(dataset, algorithmNames[i], i);
            add(new ChartPanel(chart));
        }
    }

    private JFreeChart createChart(XYSeriesCollection dataset, String title, int order) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "x", "y", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

        for(int i = 0; i< solutions.get(order).size(); ++i) {
            renderer.setSeriesPaint( i , Color.RED );
            renderer.setSeriesItemLabelsVisible(i, true);
            renderer.setSeriesItemLabelFont(i, renderer.getBaseItemLabelFont().deriveFont(14f));
            renderer.setSeriesVisibleInLegend(i, true);
        }

        plot.setRenderer(renderer);
        return chart;
    }

    private void createPointConnections(ArrayList<Point> seriesPoints, XYSeriesCollection dataset) {
        XYSeries segment = new XYSeries("Path");
        for (Point p : seriesPoints) {
            segment.add(p.x, p.y);
        }
        segment.add(seriesPoints.get(0).x, seriesPoints.get(0).y); // Powrót do startu
        dataset.addSeries(segment);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main chart = new Main();
            chart.setVisible(true);
        });
    }
}