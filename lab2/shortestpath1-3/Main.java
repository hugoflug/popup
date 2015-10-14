import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by fabianschilling on 10/7/15.
 */
public class Main {

    static IO io = new IO(System.in, System.out);
    //static IO io = new IO("/Users/fabianschilling/Downloads/shortestpath1-sample-data/shortestpath1.in");
    //static IO io = new IO("/Users/fabianschilling/Downloads/shortestpath2-sample-data/shortestpath2.in");
    //static IO io = new IO("/Users/fabianschilling/Downloads/shortestpath3-sample-data/shortestpath3.in");
    //static IO io = new IO("/Users/fabianschilling/Downloads/minspantree-sample-data/minspantree-test.in");
    //static IO io = new IO("/Users/fabianschilling/Downloads/allpairspath-sample-data/allpairspath.in");

    public static void shortestpath1() {

        while (io.hasMoreTokens()) {

            int n = io.getInt(); // number of nodes in the graph
            if (n == 0) break;
            int m = io.getInt(); // number of edges
            int q = io.getInt(); // number of queries
            int s = io.getInt(); // index of the starting node

            Node[] nodes = new Node[n];

            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i);
            }

            for (int i = 0; i < m; i++) {
                int u = io.getInt();
                int v = io.getInt();
                int w = io.getInt();

                nodes[u].adjacencies.add(new Edge(nodes[v], w));

            }

            nodes[s].dijkstra();

            for (int i = 0; i < q; i++) {
                int query = io.getInt();

                int distance = nodes[query].distance;

                if (distance == Integer.MAX_VALUE) {
                    io.println("Impossible");
                } else {
                    io.println(distance);
                }

            }

            io.println();
        }
    }

    public static void shortestpath2() {

        while (io.hasMoreTokens()) {

            int n = io.getInt(); // number of nodes in the graph
            if (n == 0) break;
            int m = io.getInt(); // number of edges
            int q = io.getInt(); // number of queries
            int s = io.getInt(); // index of the starting node

            Node[] nodes = new Node[n];

            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i);
            }

            for (int i = 0; i < m; i++) {
                int u = io.getInt();
                int v = io.getInt();
                int t0 = io.getInt();
                int P = io.getInt();
                int d = io.getInt();

                nodes[u].adjacencies.add(new Edge(nodes[v], d, t0, P));

            }

            nodes[s].dijkstraTimetable();

            for (int i = 0; i < q; i++) {
                int query = io.getInt();

                int distance = nodes[query].distance;

                if (distance == Integer.MAX_VALUE) {
                    io.println("Impossible");
                } else {
                    io.println(distance);
                }

            }

            io.println();
        }
    }

    public static void shortestpath3() {

        while (io.hasMoreTokens()) {

            int n = io.getInt(); // number of nodes in the graph
            if (n == 0) break;
            int m = io.getInt(); // number of edges
            int q = io.getInt(); // number of queries
            int s = io.getInt(); // index of the starting node

            Node[] nodes = new Node[n];

            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i);
            }

            Edge[] edges = new Edge[m];

            for (int i = 0; i < m; i++) {
                int u = io.getInt();
                int v = io.getInt();
                int w = io.getInt();

                edges[i] = new Edge(nodes[u], nodes[v], w);

                //nodes[u].adjacencies.add(new Edge(nodes[v], w));

            }

            nodes[s].bellmanFord(nodes, edges);

            for (int i = 0; i < q; i++) {
                int query = io.getInt();

                int distance = nodes[query].distance;

                if (distance == Integer.MAX_VALUE) {
                    io.println("Impossible");
                } else if (distance == -Integer.MIN_VALUE) {
                    io.println("-Infinity");
                } else {
                    io.println(distance);
                }

            }

            io.println();
        }
    }

    public static void minspantree() {

        while (io.hasMoreTokens()) {

            int n = io.getInt(); // number of nodes in the graph
            if (n == 0) break;
            int m = io.getInt(); // number of edges

            if (m == 0) { // no edges
                io.println("Impossible");
                continue;
            }

            if (m < n - 1) { // not enough edges
                io.println("Impossible");
                continue;
            }

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

            nodes[0].prim(nodes);

            Vector<Edge> edges = new Vector<>();
            int total = 0;

            for (Node node: nodes) {
                if (node.previous != null) {
                    total += node.distance;
                    edges.add(new Edge(node.previous, node, node.distance));
                }
            }
            if (edges.isEmpty() || (edges.size() != n - 1)) {
                io.println("Impossible");
            } else {
                Collections.sort(edges);
                io.println(total);
                for (Edge e: edges) {
                    io.println(e.source.index + " " + e.target.index);
                }
            }
        }
    }

    public static void allpairspath() {

        while (io.hasMoreTokens()) {
            int n = io.getInt();
            if (n == 0) break;
            int m = io.getInt();
            int q = io.getInt();

            int[][] G = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    G[i][j] = Integer.MAX_VALUE;
                }
            }

            for (int i = 0; i < m; i++) {
                int u = io.getInt();
                int v = io.getInt();
                int w = io.getInt();
                G[u][v] = w;
            }

            int[][] dist = floydWarshall(G);

            for (int i = 0; i < q; i++) {
                int u = io.getInt();
                int v = io.getInt();
                if (dist[u][v] == Integer.MAX_VALUE) {
                    io.println("Impossible");
                } else if (dist[u][v] == Integer.MIN_VALUE) {
                    io.println("-Infinity");
                } else {
                    io.println(dist[u][v]);
                }
            }

            io.println();
        }
    }

    public static int[][] floydWarshall(int[][] G) {

        int[][] dist = new int[G.length][G.length];

        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                if (i == j) dist[i][j] = 0;
                else dist[i][j] = Integer.MAX_VALUE;
                if (dist[i][j] > G[i][j]) dist[i][j] = G[i][j];
            }
        }

        for (int k = 0; k < dist.length; k++) {
            for (int i = 0; i < dist.length; i++) {
                for (int j = 0; j < dist.length; j++) {

                    if (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE) {
                        continue;
                    }

                    if (dist[i][k] == Integer.MIN_VALUE || dist[k][j] == Integer.MIN_VALUE) {
                        dist[i][j] = Integer.MIN_VALUE;
                    }

                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }

                    if (i == j && dist[i][j] < 0) {
                        dist[i][j] = Integer.MIN_VALUE;
                    }
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {

        //shortestpath1();
        //shortestpath2();
        //shortestpath3();
        //minspantree();
        //allpairspath();

        io.close();
    }
}
