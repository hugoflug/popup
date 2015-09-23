#include <stdio.h>


class DisjointSet {
private:
    int parents[1000000];
    int heights[1000000];
public:
    DisjointSet(int n) {
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < n; i++) {
            heights[i] = i;
        }
    }

    int find(int val) {
        if (parents[val] != val) {
            parents[val] = find(parents[val]);
        }
        return parents[val];
    }

    int same(int a, int b) {
        return find(a) == find(b);
    }

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
    int N, Q;
    scanf("%d %d", &N, &Q);
    char op;
    int val1, val2;
    int i, j;

    DisjointSet ds(N);

    for (j = 0; j < Q; j++) {
        scanf(" %c %d %d", &op, &val1, &val2);

        if (op == '?') {
            if (ds.same(val1, val2)) {
                printf("yes\n");
            } else {
                printf("no\n");
            }
        } else {
            ds.unionize(val1, val2);
        }
    }
}

