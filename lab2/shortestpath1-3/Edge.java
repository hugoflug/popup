
public class Edge implements Comparable<Edge> {

    public Node target;
    public Node source;
    public int weight;
    public int t0;
    public int P;

    public Edge(Node target, int weight) {
        this.target = target;
        this.weight = weight;
    }

    public Edge(Node source, Node target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Edge(Node target, int weight, int t0, int P) {
        this.target = target;
        this.weight = weight;
        this.t0 = t0;
        this.P = P;
    }

    @Override
    public int compareTo(Edge o) {
        if (source.index != o.source.index) {
            return Integer.compare(source.index, o.source.index);
        } else {
            return Integer.compare(target.index, o.target.index);
        }
    }
}
