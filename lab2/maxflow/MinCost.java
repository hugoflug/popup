import java.util.ArrayList;

/* Authors: Hugo Sandelius and Fabian Schilling */

/* Solves 'mincostmaxflow' problem on Kattis */
public class MinCost {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        int v = io.getInt();
        int e = io.getInt();

        int source = io.getInt();
        int sink = io.getInt();

        ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

        for (int i = 1; i <= v + 1; i++) {
            edges.add(new ArrayList<Edge>());
        }

        for (int i = 0; i < e; i++) {
            int vertex = io.getInt();
            int vertex2 = io.getInt();
            int cap = io.getInt();
            int cost = io.getInt();

            Edge newEdge = new Edge(vertex, vertex2, cap, cost);
            edges.get(vertex).add(newEdge);
        }

        MaxFlowSolver maxFlow = new MaxFlowSolver(v, source, sink, edges, true);
        maxFlow.solve();

        io.println(maxFlow.getMaxFlow() + " " + maxFlow.getTotalCost());


        io.flush();
    }
}