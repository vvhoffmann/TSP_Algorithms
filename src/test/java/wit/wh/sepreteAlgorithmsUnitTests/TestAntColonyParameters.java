package wit.wh.sepreteAlgorithmsUnitTests;

import org.junit.Test;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.ArrayList;
import java.util.Collections;

public class TestAntColonyParameters {

    @Test
    public void testBestAntParamsChoice()
    {
        //given
        ArrayList<Point> inputPoints = PointUtils.generateRandomPoints(15);

        //when
        double solution1 = TSPSolutionFactory.createSolution(SolutionType.ANT_COLONY_OPTIMIZATION_ALGORITHM, inputPoints).getRouteLength();
        double solution2 = TSPSolutionFactory.createSolution(SolutionType.ANT_COLONY_OPTIMIZATION_ALGORITHM, inputPoints).getRouteLength();
        Collections.shuffle(inputPoints);
        double solution3 = TSPSolutionFactory.createSolution(SolutionType.ANT_COLONY_OPTIMIZATION_ALGORITHM, inputPoints).getRouteLength();
        Collections.shuffle(inputPoints);
        double solution4 = TSPSolutionFactory.createSolution(SolutionType.ANT_COLONY_OPTIMIZATION_ALGORITHM, inputPoints).getRouteLength();
        ArrayList<Double> solutions = new ArrayList<>();
        solutions.add(solution1);
        solutions.add(solution2);
        solutions.add(solution3);
        solutions.add(solution4);

        int bestSolutionIndex = 0;
        for (int i = 0; i < solutions.size(); i++){
            if (solutions.get(i) < solutions.get(bestSolutionIndex)){
                bestSolutionIndex = i;
            }
        }
        solutions.remove(bestSolutionIndex);
        //then
    }
    
}
