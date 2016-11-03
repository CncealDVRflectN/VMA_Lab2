package by.vma.lab2;

public class Vector {
    public double[] vector;
    private int length;

    public Vector() {
        this.length = 0;
        this.vector = null;
    }

    public Vector(int length) throws Exception {
        if (length < 1) {
            throw new Exception("Incorrect size.");
        }
        this.length = length;
        vector = new double[length];
    }

    public Vector(Vector init) {
        this.length = init.getLength();
        for (int i = 0; i < length; i++) {
            this.vector[i] = init.vector[i];
        }
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

    public void swap(int i, int j) {
        double tmp = vector[i];
        vector[i] = vector[j];
        vector[j] = tmp;
    }

    public void fillDefault() {
        double[] b = {1.2677, 1.6819, -2.3657, -6.5369, 2.8351};
        this.length = 5;
        this.vector = b;
    }

    public Vector subtract(Vector sub) throws Exception {
        if (length != sub.getLength()) {
            throw new Exception("Incorrect vector.");
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
