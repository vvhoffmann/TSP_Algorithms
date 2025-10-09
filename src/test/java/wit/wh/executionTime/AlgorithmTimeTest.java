package wit.wh.executionTime;

import org.junit.jupiter.api.Test;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AlgorithmTimeTest {

    private class MeasureTime
    {
        private Runnable algorithm;

        public MeasureTime(Runnable algorithm) { this.algorithm=algorithm; }

        public double run()
        {
            long startTime = System.nanoTime();
            algorithm.run();
            double timeInNanoseconds = System.nanoTime() - startTime;
            return timeInNanoseconds/TimeUnit.NANOSECONDS.convert(1,TimeUnit.MILLISECONDS);
        }
    }

    public double solutionRuntime(final SolutionType solutionType, final ArrayList<Point> points) {
        return new MeasureTime(() -> TSPSolutionFactory.createSolution(solutionType, points)).run();
    }

    @Test
    public void timeMeasure()
    {
        ArrayList<Point> points = PointUtils.generateRandomPoints(5);
        for (SolutionType solutionType : SolutionType.values())
            System.out.println(solutionType + " " + solutionRuntime(solutionType, points));
    }
}