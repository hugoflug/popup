/* Authors: Hugo Sandelius and Fabian Maximilian */

#include <stdio.h>
#include <math.h>
#include "knapsack.h"

int main() {
    double cd;
    int n;
    int value[2001];
    int weight[2001];
    int chosen[2001];
    while (scanf("%lf %d", &cd, &n) == 2) {
        int c = (int)floor(cd);

        int i;
        for (i = 1; i <= n; i++) {
            scanf("%d %d", &value[i], &weight[i]);
        }

        int chosen_n = knapsack(weight, value, c, n, chosen);

        printf("%d\n", chosen_n);
        for (int i = 0; i < chosen_n; i++) {
            printf("%d ", chosen[i]-1);
        }
        printf("\n");
    }
}