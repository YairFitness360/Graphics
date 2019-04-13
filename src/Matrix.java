public class Matrix {
    public double[][] create2DMatrix() {
        double initMatrix[][] = new double[3][3];
        for(int i = 0; i < 3; i++) {
            initMatrix[i][i] = 1;
        }
        return initMatrix;
    }

    public double[][] create3DMatrix() {
        double initMatrix[][] = new double[4][4];
        for(int i = 0; i < 4; i++) {
            initMatrix[i][i] = 1;
        }
        return initMatrix;
    }
}