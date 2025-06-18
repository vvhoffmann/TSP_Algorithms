package wit.wh.Algorithms.AntColony;

import java.util.stream.Stream;

public class Ant
{
    protected int trailSize;
    protected int[] trail;
    protected boolean[] visited;

    public Ant(int tourSize) 
    {
        this.trailSize = tourSize;
        this.trail = new int[tourSize];
        this.visited = new boolean[tourSize];
    }

    protected void visitCity(int currentIndex, int city) 
    {
        trail[currentIndex + 1] = city; //add to trail
        visited[city] = true;           //update flag
    }

    protected boolean visited(int i) 
    {
        return visited[i];
    }

    protected double trailLength(double[][] graph)
    {
        double length = graph[trail[trailSize - 1]][trail[0]];

        for(int i= 1; i < trailSize; i++)
            length += graph[trail[i-1]][trail[i]];

        return length;
    }

    protected void clear() 
    {
        for (int i = 0 ; i < trailSize ; i++)
            visited[i] = false;
    }
}