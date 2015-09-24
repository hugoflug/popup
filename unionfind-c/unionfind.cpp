/* Authors: Hugo Sandelius and Fabian Maximilian */

#include "unionfind.h"
#include <cstdio>

/* Create a set of 'n' disjoint sets */
DisjointSet::DisjointSet(int n) {
    parents = new int[n];
    heights = new int[n];

    for (int i = 0; i < n; i++) {
        parents[i] = i;
    }

    for (int i = 0; i < n; i++) {
        heights[i] = i;
    }    
}

/* Find the representative for the set containing 'val' */
int DisjointSet::find(int val) {
    if (parents[val] != val) {
        parents[val] = find(parents[val]);
    }
    return parents[val];    
}

/* Returns true if a and b are in the same set, false otherwise */
bool DisjointSet::same(int a, int b) {
    return find(a) == find(b);    
}

/* Unions the set containing a with the set containing b */
void DisjointSet::unionize(int a, int b) {
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

