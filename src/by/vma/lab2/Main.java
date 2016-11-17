package by.vma.lab2;

public class Main {
    private static class Matrix {
        public double[][] matrix;
        private int lines;
        private int columns;

        public Matrix(int lines, int columns) throws Exception {
            if (lines < 1 || columns < 1) {
                throw new Exception("Неверный размер.");
            }
            this.lines = lines;
            this.columns = columns;
            this.matrix = new double[lines][columns];
        }

        public Matrix(Matrix init) throws Exception {
            this(init.getLines(), init.getColumns());
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < columns; j++) {
                    this.matrix[i][j] = init.matrix[i][j];
                }
            }
        }

        public int getLines() {
            return lines;
        }

        public int getColumns() {
            return columns;
        }

        public void swap(int fi, int fj, int si, int sj) {
            double tmp = matrix[fi][fj];
            matrix[fi][fj] = matrix[si][sj];
            matrix[si][sj] = tmp;
        }

        public void fillDefault() {
            double[][] a = {{0.6444, 0.0000, -0.1683, 0.1184, 0.1973},
                    {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263},
                    {0.0132, -0.1184, 0.7627, 0.0145, 0.0460},
                    {0.0395, 0.0000, -0.0960, 0.7627, 0.0000},
                    {0.0263, -0.0395, 0.1907, -0.0158, 0.5523}};
            this.lines = 5;
            this.columns = 5;
            this.matrix = a;
        }

        public Vector mul(Vector vector) throws Exception {
            if (columns != vector.getLength()) {
                throw new Exception("Неверная матрица или вектор.");
            }
            Vector result = new Vector(vector.getLength());
            for (int i = 0; i < lines; i++) {
                result.vector[i] = 0;
                for (int j = 0; j < columns; j++) {
                    result.vector[i] += matrix[i][j] * vector.vector[j];
                }
            }
            return result;
        }

        public Matrix mul(Matrix mtr) throws Exception {
            if (columns != mtr.getLines()) {
                throw new Exception("Неверная матрица.");
            }
            Matrix result = new Matrix(lines, mtr.getColumns());
            for (int i = 0; i < result.getLines(); i++) {
                for (int j = 0; j < result.getColumns(); j++) {
                    result.matrix[i][j] = 0;
                    for (int k = 0; k < columns; k++) {
                        result.matrix[i][j] += this.matrix[i][k] * mtr.matrix[k][j];
                    }
                }
            }
            return result;
        }

        public Matrix transpose() throws Exception {
            if (lines != columns) {
                throw new Exception("Неверная матрица.");
            }
            Matrix result = new Matrix(this);
            for (int i = 0; i < lines; i++) {
                for (int j = i + 1; j < columns; j++) {
                    result.swap(i, j, j, i);
                }
            }
            return result;
        }
    }

    private static class Vector {
        public double[] vector;
        private int length;

        public Vector(int length) throws Exception {
            if (length < 1) {
                throw new Exception("Неверный размер.");
            }
            this.length = length;
            vector = new double[length];
        }

        public int getLength() {
            return length;
        }

        public void print(boolean exponent) {
            for (double item : vector) {
                if (exponent) {
                    System.out.printf("%e\n", item);
                } else {
                    System.out.printf("%.5f\n", item);
                }
            }
        }

        public void fillDefault() {
            double[] b = {1.2677, 1.6819, -2.3657, -6.5369, 2.8351};
            this.length = 5;
            this.vector = b;
        }

        public Vector subtract(Vector sub) throws Exception {
            if (length != sub.getLength()) {
                throw new Exception("Неверный вектор.");
            }
            Vector result = new Vector(length);
            for (int i = 0; i < length; i++) {
                result.vector[i] = this.vector[i] - sub.vector[i];
            }
            return result;
        }

        public double normI() {
            double max = Math.abs(vector[0]);
            for (int i = 1; i < length; i++) {
                if (Math.abs(vector[i]) > max) {
                    max = Math.abs(vector[i]);
                }
            }
            return max;
        }
    }

    private static Matrix s;
    private static Matrix a;
    private static Vector b;
    private static final int n = 5;

    public static void main(String[] args) {
        Vector r;
        Vector x;
        try {
            a = new Matrix(n, n);
            b = new Vector(n);
            a.fillDefault();
            b.fillDefault();
            b = (a.transpose()).mul(b);
            a = (a.transpose()).mul(a);
            fillS();
            x = squareRootMethod();
            System.out.println("Решение X: ");
            x.print(false);
            r = a.mul(x).subtract(b);
            System.out.println("Вектор невязки r: ");
            r.print(true);
            System.out.println("Норма вектора невязки ||r|| = " + r.normI());
            System.out.println("Определитель det(A^t * A) = " + determinant());
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
