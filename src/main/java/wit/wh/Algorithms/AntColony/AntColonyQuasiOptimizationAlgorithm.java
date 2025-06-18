package wit.wh.Algorithms.AntColony;

import wit.wh.TSPSolution;
import wit.wh.pointUtils.PathUtils;
import wit.wh.pointUtils.Point;
import wit.wh.pointUtils.PointUtils;

import java.util.*;

/*
 * source: https://github.com/RonitRay/Ant-Colony-Optimization/blob/master/src/AntColonyOptimization.java
 * https://www.baeldung.com/java-ant-colony-optimization#bd-introduction-2
 *
 *
 * private double c = 1.0;             //number of trails
 * private double alpha = 1;           //pheromone importance
 * private double beta = 5;            //distance priority
 * private double evaporation = 0.5;
 * private double Q = 500;             //pheromone left on trail per ant
 * private double antFactor = 0.8;     //no of ants per node
 * private double randomFactor = 0.01; //introducing randomness
 * private int maxIterations = 1000;
 */

public class AntColonyQuasiOptimizationAlgorithm extends TSPSolution {
    private ArrayList<Point> points;
    private final AntsParameters params;
    private int numberOfPoints;
    private int numberOfAnts;
    private double[][] graph;
    private double[][] trails;
    private List<Ant> ants = new ArrayList<>();
    private final Random random = new Random();
    private double[] probabilities;

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    public AntColonyQuasiOptimizationAlgorithm(AntsParameters params)
    {
        this.params = params;
    }

    /**
     * Perform ant optimization
     */
    @Override
    public ArrayList<Point> getTSPSolution(ArrayList<Point> points)
    {
        initializeInitialValues(points);
        int[] result = null;
        for(int i=1;i<=5;i++)
            result = solve();

        return PathUtils.returnRoundPath(makeSolutionList(result));
    }

    private void initializeInitialValues(ArrayList<Point> points) {
        this.points = points;
        numberOfPoints = points.size();

        graph = generateMatrix(points);
        numberOfAnts = (int) (numberOfPoints * params.antFactor());

        trails = new double[numberOfPoints][numberOfPoints];
        probabilities = new double[numberOfPoints];

        for(int i=0;i<numberOfAnts;i++)
            ants.add(new Ant(numberOfPoints));
    }

    /**
     * Generate initial solution
     */
    public double[][] generateMatrix(ArrayList<Point> points)
    {
        int n= points.size();
        double[][] matrix = new double[n][n];
        
        for(int i=0 ; i<n ; i++)
            for(int j=0 ; j<n ; j++)
                matrix[i][j] = i==j ? 0 : PointUtils.distance(points.get(i),points.get(j));

        return matrix;
    }



    private ArrayList<Point> makeSolutionList(int[] result) {
        ArrayList<Point> solution = new ArrayList<>();
        for(int i =0 ; i<result.length;i++){
            solution.add(points.get(result[i]));
        }
        return solution;
    }

    /**
     * Use this method to run the main logic
     */
    public int[] solve()
    {
        setupAnts();
        clearTrails();
        for(int i=0;i<params.maxIterations();i++)
        {
            moveAnts();
            updateTrails();
            updateBest();
        }

        return bestTourOrder;
    }

    /**
     * Prepare ants for the simulation
     */
    private void setupAnts() 
    {
        for(int i=0 ; i<numberOfAnts ; ++i)
        {
            for(Ant ant : ants)
            {
                ant.clear();
                ant.visitCity(-1, random.nextInt(numberOfPoints));
            }
        }
        currentIndex = 0;
    }

    /**
     * At each iteration, move ants
     */
    private void moveAnts() 
    {
        for(int i=currentIndex;i<numberOfPoints-1;i++)
        {
            for(Ant ant:ants)
            {
                ant.visitCity(currentIndex,selectNextCity(ant));
            }
            currentIndex++;
        }
    }

    /**
     * Select next city for each ant
     */
    private int selectNextCity(Ant ant) 
    {
        int t = random.nextInt(numberOfPoints - currentIndex);
        if (random.nextDouble() < params.randomFactor())
        {
            int cityIndex=Integer.MIN_VALUE;
            for(int i=0;i<numberOfPoints;i++)
            {
                if(i==t && !ant.visited(i))
                {
                    cityIndex=i;
                    break;
                }
            }
            if(cityIndex!=Integer.MIN_VALUE)
                return cityIndex;
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfPoints; i++) 
        {
            total += probabilities[i];
            if (total >= r) 
                return i;
        }
        throw new RuntimeException("There are no other Points");
    }

    /**
     * Calculate the next city picks probabilites
     */
    public void calculateProbabilities(Ant ant) 
    {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfPoints; l++) 
        {
            if (!ant.visited(l)) 
                pheromone += Math.pow(trails[i][l], params.alpha()) * Math.pow(1.0 / graph[i][l], params.beta());
        }
        for (int j = 0; j < numberOfPoints; j++) 
        {
            if (ant.visited(j)) 
                probabilities[j] = 0.0;
            else 
            {
                double numerator = Math.pow(trails[i][j], params.alpha()) * Math.pow(1.0 / graph[i][j], params.beta());
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    /**
     * Update trails that ants used
     */
    private void updateTrails() 
    {
        for (int i = 0; i < numberOfPoints; i++) 
        {
            for (int j = 0; j < numberOfPoints; j++)
                trails[i][j] *= params.evaporation();
        }
        for (Ant a : ants) 
        {
            double contribution = params.Q() / a.trailLength(graph);
            for (int i = 0; i < numberOfPoints - 1; i++)
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            trails[a.trail[numberOfPoints - 1]][a.trail[0]] += contribution;
        }
    }

    /**
     * Update the best solution
     */
    private void updateBest() 
    {
        if (bestTourOrder == null) 
        {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(graph);
        }
        
        for (Ant a : ants) 
        {
            if (a.trailLength(graph) < bestTourLength) 
            {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
    }

    /**
     * Clear trails after simulation
     */
    private void clearTrails() 
    {
        for(int i=0;i<numberOfPoints;i++)
        {
            for(int j=0;j<numberOfPoints;j++)
                trails[i][j]=params.c();
        }
    }
}