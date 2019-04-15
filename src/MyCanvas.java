import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {
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
    private double Sx;
    private double Sy;
    private double Dx;
    private double Dy;
    private char curPosTrans;
    private double[][] VM1;
    private double[][] P;
    private double[][] VM2;
    private double[][] T1;
    private double[][] T2;
    private double[][] AT;

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
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        double[][] T = createT();
        double[][] R = createR();
        VM1 = math.multMatrix(R, T);
        P = matrix.create3DMatrix();
        P[2][2] = 0;

        double wcx = l + ((r - l) / 2);
        double wcy = b + ((t - b) / 2);
        double[][] S = trans.Scale(vw / (r - l),- vh / (t - b),1);
        T1 = trans.Translate(-wcx,-wcy,0);;
        T2 = trans.Translate(20 + (vw / 2),20 + (vh / 2),0);
        VM2 = math.multMatrix(T2, math.multMatrix(S, T1));
        AT = TT;
    }

    private char findMousePos(double x,double y){
        if (x < 20 || y < 20 || x > vw + 20 || y > vh + 20) {
            System.out.println("-");
            return '-';
        } else if(x >= vw / 3 +20 && x <= 2 * vw / 3 + 20 && y >= vh / 3 +20 && y < 2 * vh / 3 + 20) {
            System.out.println("T");
            return 'T';
        } else if((x < vw / 3 +20 || x > 2 * vw / 3 + 20) && (y < vh / 3 + 20 || y> 2 * vh / 3 +20)) {
            System.out.println("R");
            return 'R';
        } else{
            System.out.println("S");
            return 'S';
        }
    }

    private void execAction(){
        switch(this.curPosTrans){
            case 'T':
                execTranslate();
                break;
            case 'R':
                execeRotation();
                break;
            case 'S':
                execScale();
                break;
            default:
                break;
        }
    }
    private void execTranslate() {
        //CT = trans.Translate(Dx-Sx, Dy-Sy,0);
        CT = trans.Translate(800/vw, -800/vh, 0);
    }

    public double getAngleFromVectorToXAxis(double[] vector) {
        double angle;
        float RAD2DEG = 180.0f / 3.14159f;
        // atan2 receives first Y second X
        angle = Math.atan2(vector[1], vector[0]) * RAD2DEG;
        if(angle < 0) angle += 360.0f;
        return angle;
    }

    private void execeRotation() {
        double[] vectorStart = new double[2];
        double[] vectorEnd = new double[2];

        //vector start = (x of start point - x of center point,y of start point - y of center point)
        vectorStart[0] = Sx - Px;
        vectorStart[1] = Sy - Py;
        //vector start = (x of end point - x of center point,y of end point - y of center point)
        vectorEnd[0] = Dx - Px;
        vectorEnd[1] = Dy - Py;

        double angleStart = getAngleFromVectorToXAxis(vectorStart);
        double angleEnd = getAngleFromVectorToXAxis(vectorEnd);
        double angleFinish = angleStart - angleEnd;

        double[][] Tl = matrix.create3DMatrix();
        double[] d = {Lx - Px, Ly - Py, Lz - Pz};
        Tl[2][3] = math.getVecNorm(d);
        CT = math.multMatrix(T2, math.multMatrix(trans.Rotate(Math.toRadians(angleFinish)), Tl));
        AT = math.multMatrix(CT, AT);
        // Reset CT to I.
        CT = matrix.create3DMatrix();
    }


    private void execScale() {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        Sx = e.getX();
        Sy = e.getY();
        curPosTrans = findMousePos(Sx, Sy);
    }
    public void mouseListener(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged");
        Dx=e.getX();
        Dy=e.getY();
        execAction();
        this.repaint();



    }
    public void mouseMoved(MouseEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
    public void paint(Graphics g) {
        g.drawRect(20, 20, vw, vh);

        double[][] TrM = math.multMatrix(VM2, (math.multMatrix(P, math.multMatrix(CT, (math.multMatrix(AT, VM1))))));

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
        double[] sub_p_l = {Px - Lx, Py - Ly, Pz - Lz};
        double z_norm = math.getVecNorm(sub_p_l);
        double[] zv = math.devideVec(sub_p_l, z_norm);

        Point3D zv_point = new Point3D(zv[0], zv[1], zv[2]);
        double[] mult_v_z = math.multPP(new Point3D(Vx, Vy, Vz), zv_point);
        double x_norm = math.getVecNorm(mult_v_z);
        double[] xv = math.devideVec(mult_v_z, x_norm);

        double[] yv = math.multPP(zv_point, new Point3D(xv[0], xv[1], xv[2]));

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