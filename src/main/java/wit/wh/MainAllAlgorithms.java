package wit.wh;

import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainAllAlgorithms extends JFrame {

    private final ArrayList<Point> points;
    private Map<SolutionType, ArrayList<Point>> solutions = new HashMap<>();

    public MainAllAlgorithms() {
        points = PointUtils.generateRandomPoints(20);
        solutions = TSPSolutionFactory.getAllSolutions(points);
        setLayout(new GridLayout(2, 3)); // Układ siatki 2x3
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (SolutionType type : SolutionType.values()) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            createPointConnections(solutions.get(type), dataset);
            JFreeChart chart = createChart(dataset, type);
            add(new ChartPanel(chart));
        }
    }

    private JFreeChart createChart(XYSeriesCollection dataset, SolutionType solutionType) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                solutionType.name(), "x", "y", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

        for(int i = 0; i< solutions.get(solutionType).size(); ++i) {
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