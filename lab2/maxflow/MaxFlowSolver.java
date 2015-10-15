import java.util.*;

/* Authors: Hugo Sandelius and Fabian Schilling */

/* solves maxflow mincost-maxflow, and min-cut problems on flow networks */
public class MaxFlowSolver {
    private int v;
    private int source;
    private int sink;
    private ArrayList<ArrayList<Edge>> edges;
    private int totalFlow;
    private int edgesN;
    private int totalCost;
    private boolean minCost;
    private List<Integer> sourceCut;

    /* create a solver with 'v' vertices, where 'source' is the source vertex, 'sink' is the sink vertex and
    * 'network' is the flow network in adjacency list form.
    * set 'minCost' to true if you want to solve the mincost-maxflow problem, this will be slower. */
    public MaxFlowSolver(int v, int source, int sink, ArrayList<ArrayList<Edge>> network, boolean minCost) {
        this.v = v;
        this.source = source;
        this.sink = sink;
        this.edges = network;
        this.minCost = minCost;
        sourceCut = new ArrayList<>();

        /* add reverse edges */
        for (int i = 0; i < network.size(); i++) {
            for (int j = 0; j < network.get(i).size(); j++) {
                Edge edge = network.get(i).get(j);
                if (edge.reverse == null) {
                    edge.reverse = new Edge(edge.to, edge.from, 0, -edge.cost);
                    edge.reverse.reverse = edge;
                    edges.get(edge.to).add(edge.reverse);
                }
            }
        }
    }

    /* solve the problems and populate the object with the return values */
    public void solve() {
        List<Edge> path;

        /* find a path from source to sink */
        while ((path = shortestPath(edges, source, sink, v)) != null) {

            /* find the minimum flow possible along it */
            int min = Integer.MAX_VALUE;
            for (Edge edge : path) {
                if (edge.rest() < min) {
                    min = edge.rest();
                }
            }

            /* send the flow */
            for (Edge edge : path) {
                edge.flow = edge.flow + min;
                edge.reverse.flow = -edge.flow;
            }

        }

        /* calculate amount of positive-flow edges and total cost (if applicable) */
        totalCost = 0;
        edgesN = 0;
        for (int i = 0; i < edges.size(); i++) {
            Iterator<Edge> iterator = edges.get(i).iterator();
            while (iterator.hasNext()) {
                Edge edge = iterator.next();
                if (edge.flow <= 0) {
                    iterator.remove();
                } else {
                    totalCost += edge.flow*edge.cost;
                    edgesN++;
                }
            }
        }

        /* calculate total flow */
        totalFlow = 0;
        for (Edge nearSource : edges.get(source)) {
            int f = nearSource.flow;
            if (f > 0) {
                totalFlow += f;
            }
        }
    }

    public int getEdgesN() {
        return edgesN;
    }

    public int getVerticesN() {
        return v;
    }

    public int getSource() {
        return source;
    }

    public int getSink() {
        return sink;
    }

    public int getMaxFlow() {
        return totalFlow;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<Integer> getSourceSideOfMinCut() {
        return sourceCut;
    }

    public ArrayList<ArrayList<Edge>> getEdges() {
        return edges;
    }

    /* find the shortest path from source to sink using 'vN' vertices. if maxFlow=true, uses Bellman-Ford, otherwise BFS*/
    private List<Edge> shortestPath(ArrayList<ArrayList<Edge>> graph, int source, int sink, int vN) {
        if (minCost) {
            return bellmanFord(graph, source, sink, vN);
        } else {
            return bfs(graph, source, sink, vN);
        }
    }

    private List<Edge> bfs(ArrayList<ArrayList<Edge>> graph, int source, int sink, int vN) {
        List<Edge> path = new ArrayList<>();
        Queue<Integer> q = new ArrayDeque<>(vN/3);
        Edge[] prevInfo = new Edge[vN + 1];
        boolean[] v = new boolean[vN + 1];
        q.add(source);
        v[source] = true;


        while (!q.isEmpty()) {
            int t = q.remove();


            if (t == sink) {
                /* backtrack prev-pointers to create path */
                Edge p = prevInfo[t];
                while (p != null) {
                    path.add(p);
                    p = prevInfo[p.from];
                }
                return path;
            }

            ArrayList<Edge> neighborList = graph.get(t);
            for (int i = 0; i < neighborList.size(); i++) {
                Edge e = neighborList.get(i);
                int vert = e.to;
                if (!v[vert] && e.rest() > 0) {
                    v[vert] = true;
                    q.add(vert);
                    prevInfo[vert] = e;
                }
            }
        }

        /* path can't be found - add all visited vertices to source-side-of-min-cut list */
        sourceCut = new ArrayList<>();
        for (int i = 0; i < v.length; i++) {
            if (v[i]) {
                sourceCut.add(i);
            }
        }

        return null;
    }

    private List<Edge> bellmanFord(ArrayList<ArrayList<Edge>> graph, int source, int sink, int vN) {
        long[] distance = new long[vN];
        Edge[] predecessor = new Edge[vN];

        for (int i = 0; i < vN; i++) {
            distance[i] = Integer.MAX_VALUE;
            predecessor[i] = null;
        }
        distance[source] = 0;

        boolean changes;

        for (int i = 0; i < vN; i++) {
            changes = false;
            for (int j = 0; j < graph.size(); j++) {
                ArrayList<Edge> neighbors = graph.get(j);
                for (Edge e : neighbors) {
                    if (e.rest() > 0 && distance[j] + e.cost < distance[e.to]) {
                        changes = true;
                        distance[e.to] = distance[j] + e.cost;
                        predecessor[e.to] = e;
                    }
                }
            }

            /* no change in last iteration, no need to keep looping */
            if (!changes) {
                break;
            }
        }

        ArrayList<Edge> path = new ArrayList<>();
        int v = sink;

        /* backtrack prev-pointers to build path */
        while (predecessor[v] != null) {
            path.add(predecessor[v]);
            v = predecessor[v].from;
        }

        Collections.reverse(path);

        if (path.isEmpty()) {
            return null;
        } else {
            return path;
        }
    }

}

class Edge {
    public Edge(int from, int to, int cap) {
        this(from, to, cap, 0);
    }

    public Edge(int from, int to, int cap, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = cap;
        this.flow = 0;
        this.cost = cost;
    }

    public int rest() {
        return capacity - flow;
    }

    public int capacity;
    public int flow;
    public int from, to;
    public int cost;
    public Edge reverse;
}
