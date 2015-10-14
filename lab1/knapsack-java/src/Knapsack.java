import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 * Created by hugosa on 15/09/15.
 */

public class Knapsack {

    static class Item {
        public int index;
        public int value;
        public int weight;

        public Item (int index, int value, int weight) {
            this.index = index;
            this.value = value;
            this.weight = weight;
        }
    }

    public static int GCD(int a, int b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }

    static int[][] m = new int[2001][2001];
    static int[][] chosenObject = new int[2001][2001];
    static int[][] backPointers = new int[2001][2001];

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        while (io.hasMoreTokens()) {
            int c = (int)Math.floor(io.getDouble());
            int n = io.getInt();

            int[] weight = new int[n+1];
            int[] value = new int[n+1];

            for (int i = 0; i < n; i++) {
                value[i] = io.getInt();
                weight[i] = io.getInt();
            }

            /*
            int gcd = weight[0];
            for (int i = 1; i < n; i++) {
                gcd = GCD(gcd, weight[i]);
            }
            gcd = GCD(gcd, c);

            c = c/gcd;

            for (int i = 1; i < n; i++) {
                weight[i] = weight[i]/gcd;
            }
            */

            for (int w = 0; w <= c; w++) {
                m[0][w] = 0;
                chosenObject[0][w] = -1;
            }

            for (int i = 1; i <= n; i++) {
                for (int w = 0; w <= c; w++) {
                    if (weight[i] <= w) {
                        int itemValue = m[i-1][w-weight[i]] + value[i];
                        int prevItemValue = m[i-1][w];

                        if (itemValue > prevItemValue) {
                            m[i][w] = itemValue;
                            backPointers[i][w] = w-weight[i];
                            chosenObject[i][w] = i;
                        } else {
                            m[i][w] = prevItemValue;
                            backPointers[i][w] = w;
                            chosenObject[i][w] = chosenObject[i-1][w];
                        }

                    } else {
                        m[i][w] = m[i-1][w];
                        backPointers[i][w] = w;
                        chosenObject[i][w] = chosenObject[i-1][w];
                    }
                }
            }

            List<Integer> chosen = new ArrayList<Integer>();
            int w = (int)Math.floor(c);
            for (int i = n; i >= 0; i--) {
                if (chosenObject[i][w] == -1) {
                    break;
                }
                if (chosen.size() == 0 || chosenObject[i][w] != chosen.get(chosen.size() - 1)) {
                    chosen.add(chosenObject[i][w]);
                }
                w = backPointers[i][w];
            }

            System.out.println(chosen.size());
            for (int index : chosen) {
                System.out.print(index + " ");
            }

            System.out.println();

        }

    }

}
