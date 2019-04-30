public class Mathematics {
    /**
     * getVecNorm.
     * @param x is a vector.
     * @return the norm of the vector.
     */
    public double getVecNorm(double[] x) {
        return Math.sqrt(Math.pow(x[0], 2) + Math.pow(x[1], 2) + Math.pow(x[2], 2));
    }

    /**
     * multMatrix.
     * @param mat1 is a matrix.
     * @param mat2 is a matrix.
     * @return returns the product of the two matrices.
     */
    public double[][] multMatrix(double[][] mat1, double[][] mat2) {
        int i, j, k;
        int rowsA = mat1.length;
        int colsA = mat1[0].length;
        int colsB = mat2[0].length;
        double[][] resMatrix = new double[rowsA][colsB];
        for (i = 0; i < rowsA; i++) {
            for (j = 0; j < colsB; j++) {
                for (k = 0; k < colsA; k++) {
                    resMatrix[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return resMatrix;
    }

    /**
     * multPMatrix.
     * @param mat is a matrix.
     * @param p is a point.
     * @return the product of the matrix and the point.
     */
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

    /**
     * multPP.
     * @param p1 is a 3D-point.
     * @param p2 is a 3D-point.
     * @return the product of the two points.
     */
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

    /**
     * divideVec.
     * @param vec is a vector.
     * @param divider is a parameter.
     * @return the quotient of the vector with the parameter.
     */
    public double[] divideVec(double[] vec, double divider) {
        int size = vec.length;
        double[] res = new double[size];

        for (int i = 0; i < size; i++) {
            res[i] = vec[i] / divider;
        }
        return res;
    }

    /**
     * distance.
     * @param p1_x is the first point x coordinate.
     * @param p1_y is the first point y coordinate.
     * @param p2_x is the second point x coordinate.
     * @param p2_y is the second point y coordinate.
     * @return the distance between the two points.
     */
    public double distance(double p1_x, double p1_y, double p2_x, double p2_y) {
        return Math.sqrt(Math.pow(p1_x - p2_x, 2) + Math.pow(p1_y - p2_y, 2));
    }

    /**
     * vecLen.
     * @param vector is a vector.
     * @return the length of the vector.
     */
    public double vecLen(double[] vector) {
        double sum = multVV(vector, vector);
        return Math.sqrt(sum);
    }

    /**
     * multVV.
     * @param a is a vector.
     * @param b is a vector.
     * @return the product of the two vectors.
     */
    private double multVV(double[] a, double[] b) {
        int aSize = a.length;
        double sum = 0;
        for (int i = 0; i < aSize; ++i){
            sum += a[i] * b[i];
        }
        return sum;
    }

    /**
     * subtractVV.
     * @param a is a vector.
     * @param b is a
     * @return a vector of
     */
    public double[] subtractVV(double[] a, double[] b) {
        int aSize = a.length;
        double[] answer = new double[aSize];
        for (int i = 0; i < aSize; i++)
            answer[i] = a[i] - b[i];
        return answer;
    }

    /**
     * getAngleFromXAxisToVec.
     * @param vec is a vector.
     * @return the angle between the vector and the x axis.
     */
    public double getAngleFromXAxisToVec(double[] vec) {
        float radToDegFactor = 180.0f / (float)(Math.PI);
        double angle = Math.atan2(vec[1], vec[0]) * radToDegFactor;
        if (angle < 0) {
            angle += 360.0f;
        }
        return angle;
    }
}
