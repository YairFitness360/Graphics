import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {
    private double l, r, b, t;
    private double Px, Py, Pz;
    private double Lx, Ly, Lz;
    private double Vx, Vy, Vz;
    private double[][] VM1, P, CT, TT, T1, T2, AT, VM2;
    private int vw, vh;
    private ArrayList<Point3D> vertexes = new ArrayList<>();;
    private ArrayList<int[]> polygons = new ArrayList<>();;
    private Mathematics math = new Mathematics();
    private Transform trans = new Transform();
    private Matrix matrix;
    private double Sx, Sy;
    private double Dx, Dy;
    private char curPosTrans;
    private File fileView = new File ("example3d.viw");
    private File fileScn= new File ("example3d.scn");

    public MyCanvas() {
        init();
    }

    private void init() {
        readView();
        readScn();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setSize(vw + 40,vh + 40);
        this.matrix = new Matrix();
        this.CT = matrix.create3DMatrix();
        this.TT = matrix.create3DMatrix();
        this.AT = TT;
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
        CT = trans.Translate(Dx - Sx, Dy - Sy,0);
    }

    public double getAngleFromVectorToXAxis(double[] vector) {
        double angle;
        float RAD2DEG = 180.0f / 3.14159f;
        // atan2 receives first Y second X
        angle = Math.atan2(vector[1], vector[0]) * RAD2DEG;
        if (angle < 0) angle += 360.0f;
        return angle;
    }

    private void execeRotation() {
        double[] vecStart = new double[2];
        double[] vecEnd = new double[2];
        
        double centerX = (vw / 2) + 20;
        double centerY = (vh / 2) + 20;

        //vector start = (x of start point - x of center point,y of start point - y of center point)
        vecStart[0] = Sx - centerX;
        vecStart[1] = Sy - centerY;

        //vector start = (x of end point - x of center point,y of end point - y of center point)
        vecEnd[0] = Dx - centerX;
        vecEnd[1] = Dy - centerY;

        double angleStart = getAngleFromVectorToXAxis(vecStart);
        double angleEnd = getAngleFromVectorToXAxis(vecEnd);
        double angleFinish = angleEnd - angleStart;

        CT = trans.Rotate(Math.toRadians(angleFinish));
        CT = math.multMatrix(trans.Translate(Lx, Ly, Lz), math.multMatrix(CT, trans.Translate(-Lx, -Ly, -Lz)));
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
        switch (e.getKeyChar()) {
            case 'C':
                break;
            case 'R':
                break;
            case 'L':
                loadFile();
                break;
            case 'X':
                break;
            case 'Y':
                break;
            case 'Z':
                break;
            case 'Q':
                System.exit(0);
            default:
                break;
        }
        System.out.println(e.getKeyChar());
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

    private String getExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    private void loadFile(){
        JFileChooser jfc = new JFileChooser();
        String workingDir = System.getProperty("user.dir");

        jfc.setCurrentDirectory(new File(workingDir));

        int returnValue = jfc.showOpenDialog(jfc.getParent());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String extension = getExtension(selectedFile.getName());
            if(extension.equals("scn")){
                fileScn= new File (selectedFile.getName());
                init();
                this.repaint();
            }else if (extension.equals("viw")){
                fileView= new File (selectedFile.getName());
                init();
                this.repaint();
            }
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    public void  readScn() {
        vertexes.clear();
        polygons.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileScn), "Cp1252"));
            String line;
            line = br.readLine();
            int size_ver = Integer.parseInt(line);
            System.out.println(size_ver);
            for (int i = 0; i < size_ver; i++) {
                line = br.readLine();
                String[] splitStr = line.split("\\s+");
                Point3D point = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
                vertexes.add(point);
            }
            line = br.readLine();
            int size_poly = Integer.parseInt(line);
            for (int i = 0; i < size_poly; i++) {
                line = br.readLine();
                String[] splitStr = line.split("\\s+");
                int[] edges={Integer.parseInt(splitStr[0]),Integer.parseInt(splitStr[1])};
                polygons.add(edges);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readView() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileView), "Cp1252"));
            String line;
            line = br.readLine();
            String position_str = line.replace("Position ","");
            String[] splitStr = position_str.split("\\s+");
            Point3D position = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            this.Px = position.getX();
            this.Py = position.getY();
            this.Pz = position.getZ();
            line = br.readLine();
            String lookAt_str = line.replace("LookAt ","");
            splitStr = lookAt_str.split("\\s+");
            Point3D lookAt = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            this.Lx = lookAt.getX();
            this.Ly = lookAt.getY();
            this.Lz = lookAt.getZ();
            line = br.readLine();
            String up_str = line.replace("Up ","");
            splitStr = up_str.split("\\s+");
            Point3D up = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            this.Vx = up.getX();
            this.Vy = up.getY();
            this.Vz = up.getZ();
            line = br.readLine();
            String Window_str = line.replace("Window ","");
            splitStr = Window_str.split("\\s+");
            l=Double.parseDouble(splitStr[0]);
            r=Double.parseDouble(splitStr[1]);
            b=Double.parseDouble(splitStr[2]);
            t=Double.parseDouble(splitStr[3]);
            line = br.readLine();
            String Viewport_str = line.replace("Viewport ","");
            splitStr = Viewport_str.split("\\s+");
            vw=Integer.parseInt(splitStr[0]);
            vh=Integer.parseInt(splitStr[1]);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
