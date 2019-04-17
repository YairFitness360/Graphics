public class Transform {
    public static final int SIZE_ROW = 4;
    public static final int SIZE_COL = 4;
    private Matrix matrix = new Matrix();

    public double[][] Translate(double delta_x, double delta_y, double delta_z){
        double[][] transMatrix = matrix.create3DMatrix();
        transMatrix[0][3] = delta_x;
        transMatrix[1][3] = delta_y;
        transMatrix[2][3] = delta_z;
        return transMatrix;
    }

    public double[][] Scale(double a, double b, double c) {
        double[][] scaleMatrix = matrix.create3DMatrix();
        scaleMatrix[0][0] = a;
        scaleMatrix[1][1] = b;
        scaleMatrix[2][2] = c;
        return scaleMatrix;
    }

    public double[][] Rotate(double teta, char rotateAxis) {
        double[][] rotationMatrix = matrix.create3DMatrix();
        double angleInRadian = Math.toRadians(teta);
        switch(rotateAxis) {
            case 'z':
                rotationMatrix[0][0] = Math.cos(angleInRadian);
                rotationMatrix[0][1] = Math.sin(angleInRadian);
                rotationMatrix[1][0] = -Math.sin(angleInRadian);
                rotationMatrix[1][1] = Math.cos(angleInRadian);
            case 'x':
                rotationMatrix[1][1] = Math.cos(angleInRadian);
                rotationMatrix[2][1] = Math.sin(angleInRadian);
                rotationMatrix[1][2] = -Math.sin(angleInRadian);
                rotationMatrix[2][2] = Math.cos(angleInRadian);
            case 'y':
                rotationMatrix[0][0] = Math.cos(angleInRadian);
                rotationMatrix[0][2] = -Math.sin(angleInRadian);
                rotationMatrix[2][0] = Math.sin(angleInRadian);
                rotationMatrix[2][2] = Math.cos(angleInRadian);
            default:
                break;
        }

        return rotationMatrix;
    }

    public double[][] Reflection() {
        double[][] refMatrix = new double[SIZE_ROW][SIZE_COL];
        refMatrix[0][0] = 1;
        refMatrix[1][1] = 1;
        refMatrix[2][2] = -1;
        refMatrix[3][3] = 1;
        return refMatrix;
    }

    public Point2D point3DTo2D(Point3D p3) {
        double x = p3.getX();
        double y = p3.getY();
        double z = p3.getZ();
        //not done
        return new Point2D(2,4);
    }
}
