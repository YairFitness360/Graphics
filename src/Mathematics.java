public class Mathematics {
    public double getVecNorm(double[] x) {
        return Math.sqrt(Math.pow(x[0], 2) + Math.pow(x[1], 2) + Math.pow(x[2], 2));
    }

    public double[][] multMatrix(double[][] mat1, double[][] mat2) {
        int i, j, k;
        int rowsA = mat1.length;
        int colsA = mat1[0].length;
        int colsB = mat2[0].length;
        double[][] resMatrix = new double[rowsA][colsB];
        for (i = 0; i < rowsA; i++) {
            for (j = 0; j < colsB; j++) {
                for (k = 0; k < colsA; k++) {
                    double a = mat1[i][k];
                    double b = mat2[k][j];
                    double c = resMatrix[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return resMatrix;
    }

    public int[] multPMatrix(double[][] mat, Point3D p) {
        int i, k;
        int[] vecP = {(int)p.getX(), (int)p.getY(), (int)p.getZ(), 1};
        int rowsA = mat.length;
        int colsA = mat[0].length;
        int[] resVec = new int[rowsA];
        for (i = 0; i < rowsA; i++) {
            for (k = 0; k < colsA; k++) {
                resVec[i] += mat[i][k] * vecP[k];
            }
        }
        return resVec;
    }

    public double[] multPP(Point3D p1, Point3D p2) {
        double[] vecP1 = {p1.getX(), p1.getY(), p1.getZ()};
        double[] vecP2 = {p2.getX(), p2.getY(), p2.getZ()};

        int rowsA = vecP1.length;
        double[] resMatrix = new double[rowsA];

        resMatrix[0] = vecP1[1]*vecP2[2] - vecP1[2]*vecP2[1];
        resMatrix[1] = vecP1[2]*vecP2[0] - vecP1[0]*vecP2[2];
        resMatrix[2] = vecP1[0]*vecP2[1] - vecP1[1]*vecP2[0];
        return resMatrix;
    }

    public double[] devideVec(double[] mult_v_z, double devider) {
        int size = mult_v_z.length;
        double[] res = new double[size];

        for (int i = 0; i < size; i++) {
            res[i] = mult_v_z[i] / devider;
        }
        return res;
    }

    public double distance(double p1_x, double p1_y, double p2_x, double p2_y) {
        return Math.sqrt(Math.pow(p1_x - p2_x, 2) + Math.pow(p1_y - p2_y, 2));
    }

    public double vecLen(double[] vector) {
        double sum = multVV(vector, vector);
        return Math.sqrt(sum);
    }

    private double multVV(double[] a, double[] b) {
        int aSize = a.length;
        double sum = 0;
        for (int i = 0; i < aSize; ++i){
            sum += a[i] * b[i];
        }
        return sum;
    }

    public double[] subtractVV(double[] a, double[] b) {
        int aSize = a.length;
        double[] answer = new double[aSize];
        for (int i = 0; i < aSize; i++)
            answer[i] = a[i] - b[i];
        return answer;
    }

    public double getAngleFromXAxisToVec(double[] vec) {
        float radToDegFactor = 180.0f / (float)(Math.PI);
        double angle = Math.atan2(vec[1], vec[0]) * radToDegFactor;
        if (angle < 0) {
            angle += 360.0f;
        }
        return angle;
    }
}
