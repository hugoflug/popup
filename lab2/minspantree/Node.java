import java.util.PriorityQueue;
import java.util.Vector;

public class Node implements Comparable<Node>{

    public int index;
    public Vector<Edge> adjacencies;
    public int distance;
    public Node previous;
    public boolean visited;

    public Node(int index) {
        this.index = index;
        this.adjacencies = new Vector<>();
        this.distance = Integer.MAX_VALUE;
        this.previous = null;
        this.visited = false;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(distance, other.distance);
    }

}
