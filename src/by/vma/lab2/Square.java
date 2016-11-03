package by.vma.lab2;

public class Square {
    private static Matrix s;
    private static Matrix a;
    private static Vector b;
    private static Vector x;
    private static Vector r;
    private static final int n = 5;

    public static void vma() {
        try {
            a = new Matrix(n, n);
            b = new Vector(n);
            r = new Vector(n);
            a.fillDefault();
            b.fillDefault();
            b = (a.transpose()).mul(b);
            a = (a.transpose()).mul(a);
            fillS();
            x = squareRootMethod();
            System.out.println("X: ");
            x.print(false);
            r = a.mul(x).subtract(b);
            System.out.println("r: ");
            r.print(true);
            System.out.println("||r|| = " + r.normI());
            System.out.println("det(A^t * A) = " + determinant());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double determinant() {
        double mul = 1;
        for (int i = 0; i < n; i++) {
            mul *= Math.pow(s.matrix[i][i], 2);
        }
        return mul;
    }

    private static Vector squareRootMethod() throws Exception {
        Vector result = new Vector(n);
        Vector y = new Vector(n);
        double sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int k = 0; k < i; k++) {
                sum += s.matrix[k][i] * y.vector[k];
            }
            y.vector[i] = (b.vector[i] - sum) / s.matrix[i][i];
        }
        for (int i = n - 1; i >= 0; i--) {
            sum = 0;
            for (int k = i + 1; k < n; k++) {
                sum += s.matrix[i][k] * result.vector[k];
            }
            result.vector[i] = (y.vector[i] - sum) / s.matrix[i][i];
        }
        return result;
    }

    private static void fillS() throws Exception {
        s = new Matrix(n, n);
        double sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int k = 0; k < i; k++) {
                sum += Math.pow(s.matrix[k][i], 2);
            }
            s.matrix[i][i] = Math.sqrt(Math.abs(a.matrix[i][i] - sum));
            for (int j = i + 1; j < n; j++) {
                sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += s.matrix[k][i] * s.matrix[k][j];
                }
                s.matrix[i][j] = (a.matrix[i][j] - sum) / s.matrix[i][i];
            }
        }
    }
}
