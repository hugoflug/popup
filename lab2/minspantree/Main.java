import java.util.*;

public class Main {

    static IO io = new IO(System.in, System.out);

    public static void prim(Node[] nodes) {

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

    public static void main(String[] args) {

        while (io.hasMoreTokens()) {

            int n = io.getInt(); // number of nodes in the graph
            if (n == 0) break;
            int m = io.getInt(); // number of edges

            Node[] nodes = new Node[n];

            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i);
            }

            for (int i = 0; i < m; i++) {
                int u = io.getInt();
                int v = io.getInt();
                int w = io.getInt();

                nodes[u].adjacencies.add(new Edge(nodes[v], w));
                nodes[v].adjacencies.add(new Edge(nodes[u], w)); // undirected
            }

            prim(nodes); // PRIM CALL

            Vector<Edge> edges = new Vector<>();
            long total = 0;

            for (Node node: nodes) {
                if (node.previous != null) {
                    total += node.distance;
                    edges.add(new Edge(node.previous, node, node.distance));
                }
            }
            if (edges.isEmpty()) {
                io.println("Impossible");
            } else {
                // lexicographic sort
                Collections.sort(edges, new Comparator<Edge>() {
                    @Override
                    public int compare(Edge o1, Edge o2) {
                        if (o1.source.index != o2.source.index) {
                            return Integer.compare(o1.source.index, o2.source.index);
                        } else {
                            return Integer.compare(o1.target.index, o2.target.index);
                        }
                    }
                });
                io.println(total);
                for (Edge e: edges) {
                    io.println(e.source.index + " " + e.target.index);
                }
            }
        }

        io.close();
    }

}
