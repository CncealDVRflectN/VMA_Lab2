package by.vma.lab2;

public class Matrix {
    public double[][] matrix;
    private int lines;
    private int columns;

    public Matrix() {
        this.lines = 0;
        this.columns = 0;
        this.matrix = null;
    }

    public Matrix(int lines, int columns) throws Exception {
        if (lines < 1 || columns < 1) {
            throw new Exception("Incorrect size.");
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

    public void setLine(int index, Vector line) throws Exception {
        if (line.getLength() != columns) {
            throw new Exception("Incorrect vector.");
        }
        for (int i = 0; i < columns; i++) {
            matrix[index][i] = line.vector[i];
        }
    }

    public void setColumn(int index, Vector column) throws Exception {
        if (column.getLength() != lines) {
            throw new Exception("Incorrect vector.");
        }
        for (int i = 0; i < lines; i++) {
            matrix[i][index] = column.vector[i];
        }
    }

    public void print() {
        for (double[] i : matrix) {
            for (double j : i) {
                System.out.printf("%.5f", j);
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    public void swap(int fi, int fj, int si, int sj) {
        double tmp = matrix[fi][fj];
        matrix[fi][fj] = matrix[si][sj];
        matrix[si][sj] = tmp;
    }

    public void swapLines(int fline, int sline) {
        for (int i = 0; i < columns; i++) {
            swap(fline, i, sline, i);
        }
    }

    public void swapColumns(int fcolumn, int scolumn) {
        for (int i = 0; i < lines; i++) {
            swap(i, fcolumn, i, scolumn);
        }
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

    public void fillE(int n) {
        lines = n;
        columns = n;
        matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public double normI() {
        double maxSum = -1;
        double sum;
        for (int i = 0; i < lines; i++) {
            sum = 0;
            for (int j = 0; j < columns; j++) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > maxSum) {
                maxSum = sum;
            }
        }
        return maxSum;
    }

    public Vector mul(Vector vector) throws Exception {
        if (columns != vector.getLength()) {
            throw new Exception("Incorrect matrix or vector.");
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
            throw new Exception("Incorrect matrix.");
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

    public Matrix subtract(Matrix mtr) throws Exception {
        if (lines != mtr.getLines() || columns != mtr.getColumns()) {
            throw new Exception("Incorrect matrix.");
        }
        Matrix result = new Matrix(lines, columns);
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                result.matrix[i][j] = this.matrix[i][j] - mtr.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix transpose() throws Exception {
        if (lines != columns) {
            throw new Exception("Incorrect matrix.");
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
