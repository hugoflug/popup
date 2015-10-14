/* Authors: Hugo Sandelius and Fabian Maximilian */

#include "knapsack.h"
#include <math.h>

/*
    Solve the knapsack problem with 'n' objects for a knapsack
    with capacity 'c'.
    Weights and values of the objects are passed in the 'weights' and
    'values' arrays. The chosen objects are put in the 'chosen' array.
    Return value is the length of 'chosen'.
*/
int knapsack(int *weights, int *values, int c, int n, int *chosen) {
    int j;

    int backpointers[n+1][c+1];
    int chosen_object[n+1][c+1];
    int m[n+1][c+1];

    int w ;
    for (w = 0; w <= c; w++) {
        m[0][w] = 0;
        chosen_object[0][w] = -1;
    }

    for (int j = 1; j <= n; j++) {
        int w;
        for (w = 0; w <= c; w++) {
            if (weights[j] <= w) {
                int item_values = m[j-1][w-weights[j]] + values[j];
                int prev_item_values = m[j-1][w];

                if (item_values > prev_item_values) {
                    m[j][w] = item_values;
                    backpointers[j][w] = w-weights[j];
                    chosen_object[j][w] = j;
                } else {
                    m[j][w] = prev_item_values;
                    backpointers[j][w] = w;
                    chosen_object[j][w] = chosen_object[j-1][w];
                }

            } else {
                m[j][w] = m[j-1][w];
                backpointers[j][w] = w;
                chosen_object[j][w] = chosen_object[j-1][w];
            }
        }
    }

    int chosen_n = 0;
    w = c;
    for (int i = n; i >= 0; i--) {
        if (chosen_object[i][w] == -1) {
            break;
        }
        if (chosen_n == 0 || chosen_object[i][w] != chosen[chosen_n - 1]) {
            chosen[chosen_n] = chosen_object[i][w];
            chosen_n++;
        }
        w = backpointers[i][w];
    }
    
    return chosen_n;
}