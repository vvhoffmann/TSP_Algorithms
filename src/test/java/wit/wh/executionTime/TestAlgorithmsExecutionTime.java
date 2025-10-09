package wit.wh.executionTime;

import org.junit.Test;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestAlgorithmsExecutionTime {

    final static String fileName = "AlgorithmTimeTest.txt";
    static final int MAX_POINTS_NUMBER = 24;
    static final int[] pointCounts = { 5, 7, 10, 12, 15, 20, MAX_POINTS_NUMBER};
    private final AlgorithmTimeTest algorithmTimeTest = new AlgorithmTimeTest();

    @Test
    public void timeMeasure() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeTitleToFile(writer);

            for (int pointCount : pointCounts) {
                ArrayList<Point> points = PointUtils.generateRandomPoints(pointCount);
                ArrayList<Double> times = new ArrayList<>();
                writer.write("" + pointCount);
                for (SolutionType solutionType : SolutionType.values()) {
                    double executionTime = algorithmTimeTest.solutionRuntime(solutionType, points);
                    if(pointCount!=6)
                        times.add(executionTime);
                }

                writeToFile(writer, times);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void writeToFile(BufferedWriter writer, ArrayList<Double> times) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Double time : times) {
            double rounded = Math.round(time* 100.0)  / 100.0;
            sb.append("\t").append(rounded);
        }

        writer.write(sb.toString());
        writer.newLine();
    }

    private void writeTitleToFile(BufferedWriter writer) throws IOException {
        StringBuilder fileTitle = new StringBuilder();
        fileTitle.append("Points");
        for (SolutionType type : SolutionType.values())
            fileTitle.append('\t').append(type.name());

        writer.write(fileTitle.toString());
        writer.newLine();
    }
}


