import java.awt.*;
import java.util.ArrayList;


public class MyCanvas extends Canvas {
    private double Px, Py, Pz;
    private double Lx, Ly, Lz;
    private double Vx, Vy, Vz;
    private double l, r, b, t;
    private double[][] CT = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0},{0, 0, 0, 1}};
    private double[][] TT = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0},{0, 0, 0, 1}};
    private int vw;
    private int vh;
    private ArrayList<Point3D> vertexes;
    private ArrayList<int[]> polygons;
    private Mathematics math = new Mathematics();
    private Transform trans = new Transform();

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
    }

    public void paint(Graphics g) {
        g.drawRect(20, 20, vw, vh);
        double[][] T = createT();
        double[][] R = createR();
        System.out.println("R:");
        math.printMatrix(R);
        double[][] VM1 = math.multMatrix(R, T);

        //double[][] T1 = new double[4][4];
        //double[][] T2 = new double[4][4];
        //double[][] S =new double[4][4];
        double[][] P = new double[4][4];
        for(int i = 0; i < 4; i++) {
            P[i][i] = 1;
        }
        P[2][2] = 0;
        double wcx=l + ((r - l) / 2);
        double wcy=b + ((t - b) / 2);
        //T1[0][3] = - (1 + (r - l) / 2);
       // T1[1][3] = - (b + (t - b) / 2);
       // T2[0][3] = 20 + (vw / 2);
        //T2[1][3] = 20 + (vh / 2);
       // S[0][0] = vw / (r - l);
       // S[1][1] = - vh / (t - b);
        double[][] S =trans.Scale(vw / (r - l),- vh / (t - b),1);
        double[][] T1=trans.Translate(-wcx,-wcy,0);;
        double[][] T2=trans.Translate(20 + (vw / 2),20 + (vh / 2),0);


        double[][] VM2 = math.multMatrix(T2, math.multMatrix(S, T1));

        double[][] TrM = math.multMatrix(VM2, (math.multMatrix(P, math.multMatrix(CT,(math.multMatrix(TT, VM1))))));
        System.out.println("TrM:");
        math.printMatrix(TrM);
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
//        System.out.println("T2:");
//        math.printMatrix(T2);
//        System.out.println("S:");
//        math.printMatrix(S);
//        System.out.println("T1:");
//        math.printMatrix(T1);
          //System.out.println("VM2:");
          //math.printMatrix(VM2);
//        System.out.println("P:");
//        math.printMatrix(P);
//        System.out.println("CT:");
//        math.printMatrix(CT);
//        System.out.println("TT:");
//        math.printMatrix(TT);
          //System.out.println("VM1:");
          //math.printMatrix(VM1);
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
        double[][] transMatrix = new double[4][4];
        for(int i = 0; i < 4; i++) {
            transMatrix[i][i] = 1;
        }
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
        System.out.println("xv:");
        math.printVec(xv);
        double[] yv = math.multPP(zv_point,new Point3D(xv[0], xv[1], xv[2]));
        System.out.println("yv:");
        math.printVec(yv);
        double[][] transMatrix = new double[4][4];
        transMatrix[0][0] = xv[0];
        transMatrix[0][1] = xv[1];
        transMatrix[0][2] = xv[2];
        transMatrix[1][0] = yv[0];
        transMatrix[1][1] = yv[1];
        transMatrix[1][2] = yv[2];
        transMatrix[2][0] = zv[0];
        transMatrix[2][1] = zv[1];
        transMatrix[2][2] = zv[2];
        transMatrix[3][3] = 1;
        return transMatrix;
    }
}
