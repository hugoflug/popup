#include <cstdio>


/*
    A datatype representing a set of disjoint sets with efficient operations
*/
class DisjointSet {
private:
    int *parents;
    int *heights;
public:
    /*
        Create a set of 'n' disjoint sets
    */
    DisjointSet(int n) {
        parents = new int[n];
        heights = new int[n];

        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < n; i++) {
            heights[i] = i;
        }
    }

    /*
        Find the representative for the set containing 'val'
    */
    int find(int val) {
        if (parents[val] != val) {
            parents[val] = find(parents[val]);
        }
        return parents[val];
    }

    /*
        Returns true if a and b are in the same set, false otherwise
    */
    int same(int a, int b) {
        return find(a) == find(b);
    }

    /*
        Unions the set containing a with the set containing b
    */
    void unionize(int a, int b) {
        int aroot = find(a);
        int broot = find(b);

        if (aroot == broot) {
            return;
        }

        if (heights[aroot] < heights[broot]) {
            parents[aroot] = broot;
        } else if (heights[broot] < heights[aroot]) {
            parents[broot] = aroot;
        } else {
            parents[broot] = aroot;
            heights[aroot] += 1;
        }

    }
};

int main() {
    int n, q;
    char op;
    int val1, val2;

    scanf("%d %d", &n, &q);
    DisjointSet ds(n);

    for (int j = 0; j < q; j++) {
        scanf(" %c %d %d", &op, &val1, &val2);

        if (op == '?') {
            if (ds.same(val1, val2)) {
                printf("yes\n");
            } else {
                printf("no\n");
            }
        } else if (op == '=') {
            ds.unionize(val1, val2);
        }
    }
}

