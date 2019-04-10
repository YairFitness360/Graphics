public class Mathematics {

    public Mathematics() {

    }

    public double[][] multMatrix(double[][] mat1, double[][] mat2) {
        int i, j, k, m, n;
        int rowsA = mat1.length;
        int colsA = mat1[0].length;
        int rowsB = mat2.length;
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

    public double[] multPMatrix(Point3D p, double[][] mat) {
        int i, j, k, m, n;
        double[] vecP = {p.getX(), p.getY(), p.getZ(), 1};
        int rowsA = mat.length;
        int colsA = mat[0].length;
        int rowsB = vecP.length;
        double[] resVec = new double[rowsA];
        for (i = 0; i < rowsA; i++) {
            for (k = 0; k < colsA; k++) {
                resVec[i] += mat[i][k] * vecP[k];
            }
        }
        return resVec;
    }


    public double[] multPP(Point3D p1, Point3D p2) {
        int i, j, k, m, n;
        double[] vecP1 = {p1.getX(), p1.getY(), p1.getZ(), 1};
        double[] vecP2 = {p1.getX(), p1.getY(), p1.getZ(), 1};
        /*
        int rowsA = vecP1.length;
        int colsB = vecP2.length;
        double[][] resMatrix = new double[rowsA][colsB];
        for (i = 0; i < rowsA; i++) {
            for (j = 0; j < colsB; j++) {
                resMatrix[i][j] = vecP1[i] * vecP2[j];
            }
        }
        */
        int rowsA = vecP1.length;
        double[] resMatrix = new double[rowsA];
        for (i = 0; i < rowsA; i++) {
            resMatrix[i] = vecP1[i] * vecP2[i];
        }
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

    public void printMatrix(double[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }


}
