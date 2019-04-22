public class Matrix {
    public double[][] createInitMatrix() {
        double initMatrix[][] = new double[4][4];
        for(int i = 0; i < 4; i++) {
            initMatrix[i][i] = 1;
        }
        return initMatrix;
    }
}