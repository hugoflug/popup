import java.util.DoubleSummaryStatistics;

public class EquationSolver {

    static final double EPS = 1e-10;

    static Kattio io = new Kattio(System.in, System.out);

    public static double[] solve(double A[][], double[] b) {

        int n = b.length;

        // for k 1...n
        for (int k = 0; k < n; k++) {

            // find the kth pivot
            int iMax = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[iMax][k])) {
                    iMax = i;
                }
            }

            // Swap rows k and iMax
            double[] temp = A[k];
            A[k] = A[iMax];
            A[iMax] = temp;

            double t = b[k];
            b[k] = b[iMax];
            b[iMax] = t;

            // Check for singularity
            if (Math.abs(A[k][k]) <= EPS) {
                // singular or multiple
                return new double[0];
            }

            // Do for all rows below pivot
            for (int i = k + 1; i < n; i++) {
                double alpha = A[i][k] / A[k][k];
                b[i] -= alpha * b[k];
                for (int j = k; j < n; j++) {
                    A[i][j] -= alpha * A[k][j];

                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + i; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }

        return x;
    }

    public static void main(String[] args) {

        while (io.hasMoreTokens()) {
            
            int n = io.getInt();

            if (n == 0) {
                break;
            }

            double[][] A = new double[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = io.getDouble();
                   // io.print(A[i][j] + " ");
                }
                //io.println();
            }

            double[] b = new double[n];

            for (int i = 0; i < n; i++) {
                b[i] = io.getDouble();
                //io.print(b[i] + " ");
            }
            //io.println();
            //io.println();

            double[] sol = solve(A, b);

            if (sol.length == 0) {
                io.print("multiple or inconsistent");
            } else {
                for (int i = 0; i < sol.length; i++) {
                    io.print(sol[i] + " ");
                }
            }


            io.println();
        }
        
        io.close();
    }
}
