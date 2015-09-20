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

    private DSNode[] nodes;

    public UnionFinder(int howManyNodes) {
        nodes = new DSNode[howManyNodes];
    }

    public void create(int val) {
        nodes[val] = new DSNode(val);
    }

    public DSNode find(DSNode node) {
        if (node.parent != node) {
            node.parent = find(node.parent);
        }
        return node.parent;
    }

    public boolean same(int a, int b) {
        if (a == b) {
            return true;
        }

        if (nodes[a] == null || nodes[b] == null) {
            return false;
        }

        return find(nodes[a]).value == find(nodes[b]).value;
    }

    public void union(int a, int b) {
        if (nodes[a] == null) {
            create(a);
        }

        if (nodes[b] == null) {
            create(b);
        }

        DSNode aRoot = find(nodes[a]);
        DSNode bRoot = find(nodes[b]);

        if (aRoot.value == bRoot.value) {
            return;
        }

        if (aRoot.height < bRoot.height) {
            aRoot.parent = bRoot;
        } else if (bRoot.height < aRoot.height) {
            bRoot.parent = aRoot;
        } else {
            bRoot.parent = aRoot;
            aRoot.height += 1;
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
