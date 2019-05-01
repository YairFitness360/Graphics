/*
Name: Yuval Hoch Id: 204468474
Name: Yair Shlomo Id: 308536150
 */
public class Matrix {
    /**
     * createInitMatrix.
     * @return a 4x4 init matrix.
     */
    public double[][] createInitMatrix() {
        double initMatrix[][] = new double[4][4];
        for(int i = 0; i < 4; i++) {
            initMatrix[i][i] = 1;
        }
        return initMatrix;
    }
}