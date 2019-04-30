public class Point3D {
    private double x;
    private double y;
    private double z;

    /**
     * Constructor.
     * @param a is the x coordinate of the point.
     * @param b is the y coordinate of the point.
     * @param c is the z coordinate of the point.
     */
    public Point3D(double a, double b, double c) {
        x = a;
        y = b;
        z = c;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
