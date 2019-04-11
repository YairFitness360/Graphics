public class Transform {
    public static final int SIZE_ROW = 4;
    public static final int SIZE_COL = 4;

    public double[][] Translate(int delta_x, int delta_y, int delta_z){
        double[][] transMatrix = new double[SIZE_ROW][SIZE_COL];
        transMatrix[0][0]=1;
        transMatrix[0][3]=delta_x;
        transMatrix[1][1]=1;
        transMatrix[1][3]=delta_y;
        transMatrix[2][2]=1;
        transMatrix[2][3]=delta_z;
        transMatrix[3][3]=1;
        return transMatrix;
    }

    public double[][] Scale(int a, int b, int c) {
        double[][] scaleMatrix = new double[SIZE_ROW][SIZE_COL];
        scaleMatrix[0][0] = a;
        scaleMatrix[1][1] = b;
        scaleMatrix[2][2] = c;
        scaleMatrix[3][3] = 1;
        return scaleMatrix;
    }

    public double[][] Rotate(double teta) {
        double[][] rotationMatrix = new double[SIZE_ROW][SIZE_COL];
        double angleInRadian = Math.toRadians(teta);
        rotationMatrix[0][0] = Math.cos(angleInRadian);
        rotationMatrix[0][1] = Math.sin(angleInRadian);
        rotationMatrix[1][0] = -Math.sin(angleInRadian);
        rotationMatrix[1][1] = Math.cos(angleInRadian);
        rotationMatrix[2][2] = 1;
        rotationMatrix[3][3] = 1;
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
