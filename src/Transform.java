public class Transform {
    private Matrix matrix = new Matrix();

    public double[][] Translate(double delta_x, double delta_y){
        double[][] transMatrix = matrix.createInitMatrix();
        transMatrix[0][3] = delta_x;
        transMatrix[1][3] = delta_y;
        return transMatrix;
    }

    public double[][] Scale(double a, double b, double c) {
        double[][] scaleMatrix = matrix.createInitMatrix();
        scaleMatrix[0][0] = a;
        scaleMatrix[1][1] = b;
        scaleMatrix[2][2] = c;
        return scaleMatrix;
    }

    public double[][] Rotate(double teta, char rotateAxis) {
        double[][] rotationMatrix = matrix.createInitMatrix();
        double angleInRadian = Math.toRadians(teta);
        switch(rotateAxis) {
            case 'z':
                rotationMatrix[0][0] = Math.cos(angleInRadian);
                rotationMatrix[0][1] = -Math.sin(angleInRadian);
                rotationMatrix[1][0] = Math.sin(angleInRadian);
                rotationMatrix[1][1] = Math.cos(angleInRadian);
                break;
            case 'x':
                rotationMatrix[1][1] = Math.cos(angleInRadian);
                rotationMatrix[1][2] = -Math.sin(angleInRadian);
                rotationMatrix[2][1] = Math.sin(angleInRadian);
                rotationMatrix[2][2] = Math.cos(angleInRadian);
                break;
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
