
public class Edge implements Comparable<Edge> {

    public Node target;
    public Node source;
    public int weight;

    public Edge(Node target, int weight) {
        this.target = target;
        this.weight = weight;
    }

    public Edge(Node source, Node target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }

}
