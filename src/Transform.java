/*
Name: Yuval Hoch Id: 204468474
Name: Yair Shlomo Id: 308536150
 */
public class Transform {
    private Matrix matrix = new Matrix();

    /**
     * Translate.
     * @param delta_x x axis direction
     * @param delta_y y axis direction
     * @return the translation matrix that moves every point by the same distance
     *          in a given direction. The matrix:
     *          |1  0  0  delta_x|
     *          |0  1  0  delta_y|
     *          |0  0  1     0   |
     *          |0  0  0     1   |
     */
    public double[][] Translate(double delta_x, double delta_y){
        double[][] transMatrix = matrix.createInitMatrix();
        transMatrix[0][3] = delta_x;
        transMatrix[1][3] = delta_y;
        return transMatrix;
    }

    /**
     * Scale.
     * @param a the first scale factor.
     * @param b the second scale factor.
     * @param c the third scale factor.
     * @return the scaling matrix as a linear transformation that enlarges or
     * shrinks objects by a scale factors. The matrix:
     *          |a  0  0  0|
     *          |0  b  0  0|
     *          |0  0  c  0|
     *          |0  0  0  1|
     */
    public double[][] Scale(double a, double b, double c) {
        double[][] scaleMatrix = matrix.createInitMatrix();
        scaleMatrix[0][0] = a;
        scaleMatrix[1][1] = b;
        scaleMatrix[2][2] = c;
        return scaleMatrix;
    }

    /**
     * Rotate.
     * @param teta is the amount of rotation.
     * @param rotateAxis the axis of rotation.
     * @return a counter-clockwise rotation matrix about the rotateAxis.
     */
    public double[][] Rotate(double teta, char rotateAxis) {
        double[][] rotationMatrix = matrix.createInitMatrix();
        double angleInRadian = Math.toRadians(teta);
        switch(rotateAxis) {
            // A counter-clockwise rotation about the z-axis.
            case 'z':
                rotationMatrix[0][0] = Math.cos(angleInRadian);
                rotationMatrix[0][1] = -Math.sin(angleInRadian);
                rotationMatrix[1][0] = Math.sin(angleInRadian);
                rotationMatrix[1][1] = Math.cos(angleInRadian);
                break;
            // A counter-clockwise rotation about the x-axis.
            case 'x':
                rotationMatrix[1][1] = Math.cos(angleInRadian);
                rotationMatrix[1][2] = -Math.sin(angleInRadian);
                rotationMatrix[2][1] = Math.sin(angleInRadian);
                rotationMatrix[2][2] = Math.cos(angleInRadian);
                break;
            // A counter-clockwise rotation about the y-axis.
            case 'y':
                rotationMatrix[0][0] = Math.cos(angleInRadian);
                rotationMatrix[0][2] = -Math.sin(angleInRadian);
                rotationMatrix[2][0] = Math.sin(angleInRadian);
                rotationMatrix[2][2] = Math.cos(angleInRadian);
                break;
            default:
                break;
        }
        return rotationMatrix;
    }
}
