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

    public void dijkstra() {

        this.distance = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(this);

        while (!queue.isEmpty()) {

            Node u = queue.poll();

            for (Edge e: u.adjacencies) {

                Node v = e.target;
                int distance = u.distance + e.weight;

                if (distance < v.distance) {

                    queue.remove(v);
                    v.distance = distance;
                    v.previous = u;
                    queue.add(v);
                }
            }
        }
    }

    public void dijkstraTimetable() {

        this.distance = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(this);

        while (!queue.isEmpty()) {

            Node u = queue.poll();

            for (Edge e: u.adjacencies) {

                int distance = e.t0 + e.weight;

                if (u.distance > e.t0) {
                    if (e.P == 0) { // cannot be reached
                        distance = Integer.MAX_VALUE;
                    } else { // find the next possible time to traverse
                        int steps = 1 + (((u.distance - e.t0) - 1) / e.P);
                        distance = e.t0 + steps * e.P + e.weight;
                    }
                }

                Node v = e.target;
                if (distance < v.distance) {
                    queue.remove(v);
                    v.distance = distance;
                    v.previous = u;
                    queue.add(v);
                }
            }
        }
    }

    public void bellmanFord(Node[] nodes, Edge[] edges) {

        this.distance = 0;

        for (int i = 0; i < nodes.length; i++) {
            boolean updated = false;
            for (Edge e: edges) {
                if (e.source.distance != Integer.MAX_VALUE && e.source.distance + e.weight < e.target.distance) {
                    e.target.distance = e.source.distance + e.weight;
                    e.target.previous = e.source;
                }
            }
            if (updated) {
                //System.err.println("Converged. Exiting early.");
                break;
            }
        }

        for (int i = 0; i < nodes.length; i++) {
            for (Edge e: edges) {
                if (e.source.distance == Integer.MIN_VALUE) {
                    e.target.distance = Integer.MIN_VALUE;
                } else if (e.source.distance != Integer.MAX_VALUE && e.source.distance + e.weight < e.target.distance) {
                    e.target.distance = Integer.MIN_VALUE;
                }
            }
        }
    }

    public void prim() {

        this.distance = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(this);

        while (!queue.isEmpty()) {

            Node u = queue.poll();

            for (Edge e: u.adjacencies) {

                Node v = e.target;

                if (!v.visited && u.distance < v.distance) {

                    queue.remove(v);
                    v.distance = e.weight;
                    v.previous = u;
                    u.visited = true;
                    queue.add(v);
                }
            }
        }
    }

    public void prim(Node[] nodes) {

        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Node node: nodes) {
            queue.add(node);
        }

        Node peek = queue.peek();
        peek.distance = 0;

        while (!queue.isEmpty()) {
            Node u = queue.poll();

            for (Edge edge: u.adjacencies) {

                Node v = edge.target;

                //if (queue.contains(v) && edge.weight < v.distance) {
                if (!v.visited && edge.weight < v.distance) {
                    queue.remove(v);
                    v.distance = edge.weight;
                    v.previous = u;
                    u.visited = true;
                    queue.add(v);
                }
            }
        }
    }
}
