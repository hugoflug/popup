#include <stdio.h>
#include <math.h>

int m[2001][2001];
int chosen_object[2001][2001];
int backpointers[2001][2001];
int weight[2001];
int value[2001];
int chosen[2001];

int main() {
    double cd;
    int n;
    while (scanf("%lf %d", &cd, &n) == 2) {
        int c = (int)floor(cd);
        
        int i;
        for (i = 1; i <= n; i++) {
            scanf("%d %d", &value[i], &weight[i]);
        }

        int w ;
        for (w = 0; w <= c; w++) {
            m[0][w] = 0;
            chosen_object[0][w] = -1;
        }

        int j;
        for (j = 1; j <= n; j++) {
            int w;
            for (w = 0; w <= c; w++) {
                if (weight[j] <= w) {
                    int item_value = m[j-1][w-weight[j]] + value[j];
                    int prev_item_value = m[j-1][w];

                    if (item_value > prev_item_value) {
                        m[j][w] = item_value;
                        backpointers[j][w] = w-weight[j];
                        chosen_object[j][w] = j;
                    } else {
                        m[j][w] = prev_item_value;
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
        for (i = n; i >= 0; i--) {
            if (chosen_object[i][w] == -1) {
                break;
            }
            if (chosen_n == 0 || chosen_object[i][w] != chosen[chosen_n - 1]) {
                chosen[chosen_n] = chosen_object[i][w];
                chosen_n++;
            }
            w = backpointers[i][w];
        }

        printf("%d\n", chosen_n);
        for (i = 0; i < chosen_n; i++) {
            printf("%d ", chosen[i]-1);
        }
        printf("\n");
    }
}