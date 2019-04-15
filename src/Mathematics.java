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
        int i, k;
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

    public void printMatrix(double[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void printVec(double[] mat) {
        for (int i = 0; i < mat.length; i++) {
            System.out.print(mat[i] + " ");
        }
        System.out.println();
    }
}
