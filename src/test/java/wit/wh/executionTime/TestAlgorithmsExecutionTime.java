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
    static final int[] pointCounts = { 5, 7, 10, 12, 15, 20, 24};

    @Test
    public void timeMeasure() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeTitleToFile(writer);

            for (int pointCount : pointCounts) {
                ArrayList<Point> points = PointUtils.generateRandomPoints(pointCount);
                ArrayList<Long>times = new ArrayList<>();
                writer.write("" + pointCount);
                for (SolutionType solutionType : SolutionType.values()) {

                    // początkowy czas w milisekundach
                    long startTime = System.currentTimeMillis();

                    //wykonanie algorytmu
                    TSPSolutionFactory.createSolution(solutionType, points);

                    // czas wykonania algorytmu w milisekundach.
                    long executionTime = System.currentTimeMillis() - startTime;

                    times.add(executionTime);
                }
                writeToFile(writer, times);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void writeToFile(BufferedWriter writer, ArrayList<Long> times) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Long time : times)
            sb.append("\t").append(time);

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


