import java.util.ArrayList;
import java.util.List;

/* Authors: Hugo Sandelius and Fabian Schilling */

/* Solves 'mincut' problem on Kattis */
public class MinCut {
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
            edges.get(vertex).add(new Edge(vertex, vertex2, cap));
        }

        MaxFlowSolver maxFlow = new MaxFlowSolver(v, source, sink, edges, false);
        maxFlow.solve();;

        List<Integer> u = maxFlow.getSourceSideOfMinCut();

        io.println(u.size());
        for (int vertex : u) {
            io.println(vertex);
        }

        io.flush();
    }
}