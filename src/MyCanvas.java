import java.awt.*;
import java.util.ArrayList;


public class MyCanvas extends Canvas {
    private double Px,Py,Pz;
    private double Lx,Ly,Lz;
    private double Vx,Vy,Vz;
    private double l,r,b,t;
    private double[][] CT = {{1, 0, 0,0}, {0, 1, 0,0}, {0, 0, 1,0},{0, 0, 0,1}};
    private double[][] TT = {{1, 0, 0,0}, {0, 1, 0,0}, {0, 0, 1,0},{0, 0, 0,1}};
    private int viewportX;
    private int viewportY;
    private ArrayList<Point3D> vertexes;
    private ArrayList<int[]> polygons;
    private Point2D[] points2D;
    private Mathematics math=new Mathematics();

    public MyCanvas(ArrayList<Point3D> vertexes,ArrayList<int[]> polygons,
                    Point3D position,Point3D lookAt,Point3D up,double l,double r,double b,double t,int viewportX, int viewportY) {
        this.vertexes=vertexes;
        this.polygons=polygons;
        this.Px=position.getX();
        this.Py=position.getY();
        this.Pz=position.getZ();
        this.Lx=lookAt.getX();
        this.Ly=lookAt.getY();
        this.Lz=lookAt.getZ();
        this.Vx=up.getX();
        this.Vy=up.getY();
        this.Vz=up.getZ();
        this.l=l;
        this.r=r;
        this.b=b;
        this.t=t;
        this.viewportX=viewportX;
        this.viewportY=viewportY;


    }
    public void paint(Graphics g) {
        g.drawRect(20, 20, viewportX, viewportY);
        double[][] t = createT();
        double[][] r = createR();
        double[][] m = math.multMatrix(t, r);
        double[][] temp = math.multMatrix(TT, m);

        double[][] TrM = math.multMatrix(CT, temp);
        ArrayList<Point3D> vertexesTag = new ArrayList<>();
        for (Point3D p : vertexes) {
            double[] pTag = math.multPMatrix(p, TrM);
            vertexesTag.add(new Point3D(pTag[0], pTag[1], pTag[2]));
        }


        //Point2D wc=new Point2D(1+(y-1)/2,b+(t-b)/2);




        for (int[] p : polygons) {
            Point3D startLine=vertexesTag.get(p[0]);
            Point3D endLine=vertexesTag.get(p[1]);

           // g.drawLine(startLine.);
        }
    }
    public double[][] createT() {
        double[][] transMatrix=new double[4][4];
        transMatrix[0][0]=1;
        transMatrix[0][3]=-Px;
        transMatrix[1][1]=1;
        transMatrix[1][3]=-Py;
        transMatrix[2][2]=1;
        transMatrix[2][3]=-Pz;
        transMatrix[3][3]=1;
        return transMatrix;
    }
    public double[][] createR() {
        double zv_x=Px-Lx/Math.abs(Px-Lx);
        double zv_y=Py-Ly/Math.abs(Py-Ly);
        double zv_z=Pz-Lz/Math.abs(Pz-Lz);

        Point3D zv=new Point3D(zv_x,zv_y,zv_z);
        double[] mult_v_z = math.multPP(new Point3D(Vx,Vy,Vz),zv);
        int size=mult_v_z.length;
        double sumNormal=0;
        for (int i=0; i<size; i++) {
            sumNormal+=mult_v_z[i];
        }
        double[] xv=math.devideVec(mult_v_z,sumNormal);
        double[] yv=math.multPP(new Point3D(xv[0],xv[1],xv[2]),new Point3D(zv_x,zv_x,zv_z));

        double[][] transMatrix=new double[4][4];
        transMatrix[0][0]=xv[0];
        transMatrix[0][1]=xv[1];
        transMatrix[0][2]=xv[2];

        transMatrix[1][0]=yv[0];
        transMatrix[1][1]=yv[1];
        transMatrix[1][2]=yv[2];

        transMatrix[2][0]=zv_x;
        transMatrix[2][1]=zv_y;
        transMatrix[2][2]=zv_z;
        transMatrix[3][3]=1;

        return transMatrix;
    }

}
