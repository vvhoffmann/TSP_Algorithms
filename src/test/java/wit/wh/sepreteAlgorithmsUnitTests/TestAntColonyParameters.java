package wit.wh.sepreteAlgorithmsUnitTests;

import org.junit.Test;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.PathUtils;
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
        double solution1 = PathUtils.getRouteLength(
                TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints)
        );

        double solution2 = PathUtils.getRouteLength(
                TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints)
                );

        Collections.shuffle(inputPoints);

        double solution3 = PathUtils.getRouteLength(
                TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints)
        );

        Collections.shuffle(inputPoints);

        double solution4 = PathUtils.getRouteLength(
                TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints)
        );

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
    }
    
}
