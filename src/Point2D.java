public class Point2D {
    private int x;
    private int y;

    public Point2D(int a,int b) {
        x=a;
        y=b;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void set(int x,int y) {
        this.x=x;
        this.y=y;
    }
    public void set(Point2D p) {
        this.x=p.getX();
        this.y=p.getY();
    }


}
