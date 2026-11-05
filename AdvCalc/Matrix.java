package AdvCalc;

public final class Matrix {
    public static final double EPS = 1e-10;
    private final int rows, cols;
    private final double[][] d;

    public Matrix(double[][] v) {
        rows = v.length;
        cols = v[0].length;
        d = new double[rows][];
        for (int i = 0; i < rows; i++) {
            if (v[i].length != cols) throw new IllegalArgumentException("Rows must have equal length");
            d[i] = v[i].clone();
        }
    }

    public static Matrix identity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) a[i][i] = 1;
        return new Matrix(a);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public Matrix add(Matrix o) { return combine(o, 1); }
    public Matrix subtract(Matrix o) { return combine(o, -1); }

    private Matrix combine(Matrix o, int sign) {
        if (rows != o.rows || cols != o.cols) throw new IllegalArgumentException("Size mismatch");
        double[][] r = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) r[i][j] = d[i][j] + sign * o.d[i][j];
        return new Matrix(r);
    }

    public Matrix multiply(double s) {
        double[][] r = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) r[i][j] = d[i][j] * s;
        return new Matrix(r);
    }

    public Matrix multiply(Matrix o) {
        if (cols != o.rows) throw new IllegalArgumentException("Cols of A must equal rows of B");
        double[][] r = new double[rows][o.cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < o.cols; j++)
                for (int k = 0; k < cols; k++) r[i][j] += d[i][k] * o.d[k][j];
        return new Matrix(r);
    }

    public Matrix transpose() {
        double[][] r = new double[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) r[j][i] = d[i][j];
        return new Matrix(r);
    }

    public double determinant() {
        square();
        double[][] a = copy();
        double det = 1;
        for (int c = 0; c < rows; c++) {
            int p = pivot(a, c);
            if (Math.abs(a[p][c]) < EPS) return 0;
            if (p != c) { swap(a, p, c); det = -det; }
            det *= a[c][c];
            for (int r = c + 1; r < rows; r++) {
                double f = a[r][c] / a[c][c];
                for (int k = c; k < rows; k++) a[r][k] -= f * a[c][k];
            }
        }
        return det;
    }

    public Matrix inverse() {
        square();
        int n = rows;
        double[][] a = copy(), inv = identity(n).copy();
        for (int c = 0; c < n; c++) {
            int p = pivot(a, c);
            if (Math.abs(a[p][c]) < EPS) throw new ArithmeticException("Matrix is singular");
            swap(a, p, c);
            swap(inv, p, c);
            double pv = a[c][c];
            for (int k = 0; k < n; k++) { a[c][k] /= pv; inv[c][k] /= pv; }
            for (int r = 0; r < n; r++) {
                if (r == c) continue;
                double f = a[r][c];
                for (int k = 0; k < n; k++) { a[r][k] -= f * a[c][k]; inv[r][k] -= f * inv[c][k]; }
            }
        }
        return new Matrix(inv);
    }

    public Matrix power(int e) {
        square();
        if (e < 0) return inverse().power(-e);
        Matrix res = identity(rows), base = this;
        for (; e > 0; e >>= 1, base = base.multiply(base))
            if ((e & 1) == 1) res = res.multiply(base);
        return res;
    }

    public double trace() {
        square();
        double s = 0;
        for (int i = 0; i < rows; i++) s += d[i][i];
        return s;
    }

    private void square() {
        if (rows != cols) throw new IllegalArgumentException("Matrix must be square");
    }

    private double[][] copy() {
        double[][] c = new double[rows][];
        for (int i = 0; i < rows; i++) c[i] = d[i].clone();
        return c;
    }

    private static int pivot(double[][] a, int c) {
        int p = c;
        for (int r = c + 1; r < a.length; r++)
            if (Math.abs(a[r][c]) > Math.abs(a[p][c])) p = r;
        return p;
    }

    private static void swap(double[][] a, int i, int j) {
        double[] t = a[i]; a[i] = a[j]; a[j] = t;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : d) {
            for (double x : row) sb.append(String.format("%10.4f", x));
            sb.append('\n');
        }
        return sb.toString();
    }
   public static void main(String[] args) {
       Matrix a = new Matrix(new double[][]{{4, 7}, {2, 6}});
       System.out.println(a.determinant());
       System.out.println(a.inverse());
       System.out.println(a.power(3));
   }
}
