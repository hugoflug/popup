/* Authors: Hugo Sandelius and Fabian Maximilian */

#ifndef UNIONFIND_H
#define UNIONFIND_H

class DisjointSet {
private:
    int *parents;
    int *heights;
public:
    /* Create a set of 'n' disjoint sets */
    DisjointSet(int n);

    /* Find the representative for the set containing 'val' */
    int find(int val);

    /* Returns true if a and b are in the same set, false otherwise */
    bool same(int a, int b);

    /* Unions the set containing a with the set containing b */
    void unionize(int a, int b);
};

#endif