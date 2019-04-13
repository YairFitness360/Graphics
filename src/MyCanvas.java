import java.awt.*;
import java.util.ArrayList;

public class MyCanvas extends Canvas {
    private double Px, Py, Pz;
    private double Lx, Ly, Lz;
    private double Vx, Vy, Vz;
    private double l, r, b, t;
    private Matrix matrix;
    private double[][] CT;
    private double[][] TT;
    private int vw;
    private int vh;
    private ArrayList<Point3D> vertexes;
    private ArrayList<int[]> polygons;
    private Mathematics math;

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
        this.math = new Mathematics();
    }

    public void paint(Graphics g) {
        g.drawRect(20, 20, vw, vh);
        double[][] T = createT();
        double[][] R = createR();
        double[][] VM1 = math.multMatrix(R, T);

        double[][] T1 = matrix.create3DMatrix();
        double[][] T2 = matrix.create3DMatrix();
        double[][] S = matrix.create3DMatrix();
        double[][] P = matrix.create3DMatrix();

        P[2][2] = 0;
        T1[0][3] = - (l + (r - l) / 2);
        T1[1][3] = - (b + (t - b) / 2);
        T2[0][3] = 20 + (vw / 2);
        T2[1][3] = 20 + (vh / 2);
        S[0][0] = vw / (r - l);
        S[1][1] = - vh / (t - b);

        double[][] VM2 = math.multMatrix(T2, math.multMatrix(S, T1));
        double[][] TrM = math.multMatrix(VM2, (math.multMatrix(P, math.multMatrix(CT,(math.multMatrix(TT, VM1))))));

        //double[][] Tl_d = matrix.create3DMatrix();
        //double[][] Tl_omd;
        //double[][] AT;
        //Tl_d[2][3] = Math.sqrt(Math.pow(Lx-Px, 2) + Math.pow(Ly-Py, 2) + Math.pow(Lz-Pz, 2));
        //Tl_omd = Tl_d;
        //Tl_omd[2][3] = 1 - Tl_omd[2][3];
        //CT = math.multMatrix(Tl_omd, math.multMatrix(S, Tl_d));
        //AT = CT;
        //CT = TT;
        //double[][] TrM = math.multMatrix(VM2, (math.multMatrix(P, math.multMatrix(CT,(math.multMatrix(AT, VM1))))));

        ArrayList<Point2D> vertexesTag = new ArrayList<>();
        for (Point3D p : vertexes) {
            int[] pTag = math.multPMatrix(TrM, p);
            vertexesTag.add(new Point2D(pTag[0], pTag[1]));
        }

        for (int[] p : polygons) {
            Point2D startLine = vertexesTag.get(p[0]);
            Point2D endLine = vertexesTag.get(p[1]);
            g.drawLine(startLine.getX(), startLine.getY(), endLine.getY(), endLine.getY());
        }
//        System.out.println("T2:");
//        math.printMatrix(T2);
//        System.out.println("S:");
//        math.printMatrix(S);
//        System.out.println("T1:");
//        math.printMatrix(T1);
//        System.out.println("VM2:");
//        math.printMatrix(VM2);
//        System.out.println("P:");
//        math.printMatrix(P);
//        System.out.println("CT:");
//        math.printMatrix(CT);
//        System.out.println("TT:");
//        math.printMatrix(TT);
//        System.out.println("VM1:");
//        math.printMatrix(VM1);
//        System.out.println("R:");
//        math.printMatrix(R);
//        System.out.println("T:");
//        math.printMatrix(T);
//        System.out.println("TT*VM1:");
//        math.printMatrix(math.multMatrix(TT, VM1));
//        System.out.println("CT*TT*VM1:");
//        math.printMatrix(math.multMatrix(CT,(math.multMatrix(TT, VM1))));
//        System.out.println("P*CT*TT*VM1:");
//        math.printMatrix(math.multMatrix(P, math.multMatrix(CT,(math.multMatrix(TT, VM1)))));
        System.out.println("VM2*P*CT*TT*VM1:");
        math.printMatrix(math.multMatrix(VM2, (math.multMatrix(P, math.multMatrix(CT,(math.multMatrix(TT, VM1)))))));
    }

    public double[][] createT() {
        double[][] transMatrix = matrix.create3DMatrix();
        transMatrix[0][3] = -Px;
        transMatrix[1][3] = -Py;
        transMatrix[2][3] = -Pz;
        return transMatrix;
    }

    public double[][] createR() {
        double[] z = {Px-Lx, Py-Ly, Pz-Lz};
        double z_norm = math.getNorm(z);
        double[] sub_p_l = {Px - Lx, Py - Ly, Pz - Lz};
        double[] zv = math.devideVec(sub_p_l, z_norm);
        Point3D zv_point = new Point3D(zv[0], zv[1], zv[2]);

        double[] mult_v_z = math.multPP(new Point3D(Vx, Vy, Vz), zv_point);
        double x_norm = math.getNorm(mult_v_z);
        double[] xv = math.devideVec(mult_v_z, x_norm);

        double[] yv = math.multPP(new Point3D(xv[0], xv[1], xv[2]), zv_point);

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
