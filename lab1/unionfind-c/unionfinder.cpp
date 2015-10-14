#include "unionfind.h"
#include <cstdio>

/* Authors: Hugo Sandelius and Fabian Maximilian */

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