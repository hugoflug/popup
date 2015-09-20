public class UnionFinder {

    private static class DSNode {
        public DSNode parent;
        public int value;
        public int height;

        public DSNode(int value) {
            this.value = value;
            this.parent = this;
            this.height = 1;
        }
    }

    private int[] parents;
    private int[] heights;

    public UnionFinder(int howManyNodes) {
        parents = new int[howManyNodes]; /* TODO: fill with 0...howManyNodes */

        for (int i = 0; i < howManyNodes; i++) {
            parents[i] = i;
        }

        heights = new int[howManyNodes];
    }

    public int find(int val) {
        if (parents[val] != val) {
            parents[val] = find(parents[val]);
        }
        return parents[val];
    }

    public boolean same(int a, int b) {
        return find(a) == find(b);
    }

    public void union(int a, int b) {

        int aRoot = find(a);
        int bRoot = find(b);

        if (aRoot == bRoot) {
            return;
        }

        if (heights[aRoot] < heights[bRoot]) {
            parents[aRoot] = bRoot;
        } else if (heights[bRoot] < heights[aRoot]) {
            parents[bRoot] = aRoot;
        } else {
            parents[bRoot] = aRoot;
            heights[aRoot] += 1;
        }

    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int N = io.getInt();
        int Q = io.getInt();

        UnionFinder unionFinder = new UnionFinder(N);

        for (int i = 0; i < Q; i++) {
            String op = io.getWord();
            int val1 = io.getInt();
            int val2 = io.getInt();

            if (op.equals("?")) {
                if (unionFinder.same(val1, val2)) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            } else {
                unionFinder.union(val1, val2);
            }
        }
    }
}
