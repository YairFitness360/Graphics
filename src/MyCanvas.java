import java.awt.*;
import java.util.ArrayList;


public class MyCanvas extends Canvas {
    private double Px, Py, Pz;
    private double Lx, Ly, Lz;
    private double Vx, Vy, Vz;
    private double l, r, b, t;
    private double[][] CT;
    private double[][] TT;
    private int vw;
    private int vh;
    private ArrayList<Point3D> vertexes;
    private ArrayList<int[]> polygons;
    private Mathematics math = new Mathematics();
    private Transform trans = new Transform();
    private Matrix matrix;

    public MyCanvas(ArrayList<Point3D> vertexes, ArrayList<int[]> polygons,
                    Point3D position, Point3D lookAt, Point3D up, double l, double r,
                    double b, double t, int vw, int vh) {
        this.vertexes = vertexes;
        this.polygons = polygons;
        this.Px = position.getX();
        this.Py = position.getY();
        this.Pz = position.getZ();
        this.Lx = lookAt.getX();
        this.Ly = lookAt.getY();
        this.Lz = lookAt.getZ();
        this.Vx = up.getX();
        this.Vy = up.getY();
        this.Vz = up.getZ();
        this.l = l;
        this.r = r;
        this.b = b;
        this.t = t;
        this.vw = vw;
        this.vh = vh;
        this.matrix = new Matrix();
        this.CT = matrix.create3DMatrix();
        this.TT = matrix.create3DMatrix();
    }

    public void paint(Graphics g) {
        g.drawRect(20, 20, vw, vh);
        double[][] T = createT();
        double[][] R = createR();
        double[][] VM1 = math.multMatrix(R, T);
        double[][] P = matrix.create3DMatrix();

        P[2][2] = 0;
        double wcx = l + ((r - l) / 2);
        double wcy = b + ((t - b) / 2);
        double[][] S = trans.Scale(vw / (r - l),- vh / (t - b),1);
        double[][] T1 = trans.Translate(-wcx,-wcy,0);;
        double[][] T2 = trans.Translate(20 + (vw / 2),20 + (vh / 2),0);
        double[][] VM2 = math.multMatrix(T2, math.multMatrix(S, T1));
        double[][] TrM = math.multMatrix(VM2, (math.multMatrix(P, math.multMatrix(CT,(math.multMatrix(TT, VM1))))));

        ArrayList<Point2D> vertexesTag = new ArrayList<>();
        for (Point3D p : vertexes) {
            int[] pTag = math.multPMatrix(TrM, p);
            vertexesTag.add(new Point2D(pTag[0], pTag[1]));
        }

        for (int[] p : polygons) {
            Point2D startLine = vertexesTag.get(p[0]);
            Point2D endLine = vertexesTag.get(p[1]);
            g.drawLine(startLine.getX(), startLine.getY(), endLine.getX(), endLine.getY());
        }
    }

    public double[][] createT() {
        double[][] transMatrix = matrix.create3DMatrix();
        transMatrix[0][3] = -Px;
        transMatrix[1][3] = -Py;
        transMatrix[2][3] = -Pz;
        return transMatrix;
    }

    public double[][] createR() {
        double z_norm = Math.sqrt(Math.pow(Px-Lx, 2) + Math.pow(Py-Ly, 2) + Math.pow(Pz-Lz, 2));
        double[] sub_p_l = {Px - Lx, Py - Ly, Pz - Lz};
        double[] zv = math.devideVec(sub_p_l, z_norm);

        Point3D zv_point = new Point3D(zv[0], zv[1], zv[2]);
        double[] mult_v_z = math.multPP(new Point3D(Vx, Vy, Vz), zv_point);
        double x_norm = Math.sqrt(Math.pow(mult_v_z[0], 2) + Math.pow(mult_v_z[1], 2) + Math.pow(mult_v_z[2], 2));
        double[] xv = math.devideVec(mult_v_z, x_norm);

        double[] yv = math.multPP(zv_point,new Point3D(xv[0], xv[1], xv[2]));

        double[][] transMatrix = matrix.create3DMatrix();
        transMatrix[0][0] = xv[0];
        transMatrix[0][1] = xv[1];
        transMatrix[0][2] = xv[2];
        transMatrix[1][0] = yv[0];
        transMatrix[1][1] = yv[1];
        transMatrix[1][2] = yv[2];
        transMatrix[2][0] = zv[0];
        transMatrix[2][1] = zv[1];
        transMatrix[2][2] = zv[2];
        return transMatrix;
    }
}
