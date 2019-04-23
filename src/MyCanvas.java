import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;

public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener,ComponentListener {
    private double l, r, b, t;
    private double Px, Py, Pz;
    private double Lx, Ly, Lz;
    private double Vx, Vy, Vz;
    private double[][] VM1, P, CT, TT, VM2;
    private int vw, vh;
    private ArrayList<Point3D> vertexes = new ArrayList<>();
    private int z = 0;
    private ArrayList<int[]> polygons = new ArrayList<>();
    private Mathematics math = new Mathematics();
    private Transform trans = new Transform();
    private Matrix matrix;
    private double Sx, Sy;
    private double Dx, Dy;
    private char rotateAxis = 'z';
    private char curPosTrans;
    private File fileView = new File("example3d.viw");
    private File fileScn = new File("example3d.scn");
    private double centerX, centerY;
    private double[][] TrM;
    private boolean isClipping = true;

    private MyCanvas() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addComponentListener(this);
        init();
    }

    private void init() {
        readView();
        readScn();
        setSize(vw + 40, vh + 40);
        this.matrix = new Matrix();
        centerX = (vw / 2) + 20;
        centerY = (vh / 2) + 20;
        createTrM();
    }

    private void createTrM() {
        double[][] T1, T2;
        this.CT = matrix.createInitMatrix();
        this.TT = matrix.createInitMatrix();
        double[][] T = createT();
        double[][] R = createR();
        VM1 = math.multMatrix(R, T);
        P = matrix.createInitMatrix();
        P[2][2] = 0;
        double wcx = l + ((r - l) / 2);
        double wcy = b + ((t - b) / 2);
        double[][] S = trans.Scale(vw / (r - l), -vh / (t - b), 1);
        T1 = trans.Translate(-wcx, -wcy);
        T2 = trans.Translate(20 + (vw / 2), 20 + (vh / 2));
        VM2 = math.multMatrix(T2, math.multMatrix(S, T1));
    }

    private char findMousePos(double x, double y) {
        if (x < 20 || y < 20 || x > vw + 20 || y > vh + 20) {
            System.out.println("-");
            return '-';
        } else if (x >= vw / 3 + 20 && x <= 2 * vw / 3 + 20 && y >= vh / 3 + 20 && y < 2 * vh / 3 + 20) {
            System.out.println("T");
            return 'T';
        } else if ((x < vw / 3 + 20 || x > 2 * vw / 3 + 20) && (y < vh / 3 + 20 || y > 2 * vh / 3 + 20)) {
            System.out.println("R");
            return 'R';
        } else {
            System.out.println("S");
            return 'S';
        }
    }

    private void execAction() {
        switch (this.curPosTrans) {
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
        CT = trans.Translate((Dx - Sx) * ((r - l) / vw), (Dy - Sy) * (-((t - b) / vh)));
    }

    private double getAngleFromVectorToXAxis(double[] vector) {
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

        //vector start = (x of start point - x of center point,y of start point - y of center point)
        vecStart[0] = Sx - centerX;
        vecStart[1] = Sy - centerY;

        //vector start = (x of end point - x of center point,y of end point - y of center point)
        vecEnd[0] = Dx - centerX;
        vecEnd[1] = Dy - centerY;

        double angleStart = getAngleFromVectorToXAxis(vecStart);
        double angleEnd = getAngleFromVectorToXAxis(vecEnd);
        double angleFinish = angleStart - angleEnd;
        CT = trans.Rotate(angleFinish, rotateAxis);
        createCT();
    }

    private void createCT() {
        double[] LookAt = {Lx, Ly, Lz};
        double[] Position = {Px, Py, Pz};
        double d = math.vecLength(math.subtract(LookAt, Position));
        double[][] Tld = matrix.createInitMatrix();
        double[][] Tl_d = matrix.createInitMatrix();
        Tld[2][3] = d;
        Tl_d[2][3] = -d;
        CT = math.multMatrix(Tl_d, math.multMatrix(CT, Tld));
    }

    private void execScale() {
        double radiusPStart = math.distance(Sx, Sy, centerX, centerY);
        double radiusPEnd = math.distance(Dx, Dy, centerX, centerY);
        double scaleParameter = radiusPEnd / radiusPStart;
        CT = trans.Scale(scaleParameter, scaleParameter, scaleParameter);
        createCT();
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
        Dx = e.getX();
        Dy = e.getY();
        TT = math.multMatrix(CT, TT);
        CT = matrix.createInitMatrix();
        this.repaint();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged");
        Dx = e.getX();
        Dy = e.getY();
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
                this.isClipping = !this.isClipping;
                this.repaint();
                break;
            case 'R':
                createTrM();
                this.repaint();
                break;
            case 'L':
                loadFile();
                break;
            case 'X':
                this.rotateAxis = 'x';
                break;
            case 'Y':
                this.rotateAxis = 'y';
                break;
            case 'Z':
                this.rotateAxis = 'z';
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
        double[][] TrM = math.multMatrix(VM2, P);
        TrM = math.multMatrix(TrM, CT);
        TrM = math.multMatrix(TrM, TT);
        TrM = math.multMatrix(TrM, VM1);
        ArrayList<Point2D> vertexesTag = new ArrayList<>();
        for (Point3D p : vertexes) {
            int[] pTag = math.multPMatrix(TrM, p);
            vertexesTag.add(new Point2D(pTag[0], pTag[1]));
        }
        for (int[] p : polygons) {
            Line2D.Double line = new Line2D.Double();
            line.setLine( vertexesTag.get(p[0]).getX(),vertexesTag.get(p[0]).getY()
                    ,vertexesTag.get(p[1]).getX(), vertexesTag.get(p[1]).getY());
            if (isClipping) {
                if (clip(line)) {
                    g.drawLine((int)line.x1, (int)line.y1,(int) line.x2, (int)line.y2);
                }
            } else {
                g.drawLine((int)line.x1, (int)line.y1,(int) line.x2, (int)line.y2);
            }
        }
    }

    private double[][] createT() {
        double[][] transMatrix = matrix.createInitMatrix();
        transMatrix[0][3] = -Px;
        transMatrix[1][3] = -Py;
        transMatrix[2][3] = -Pz;
        return transMatrix;
    }

    private double[][] createR() {
        double[] sub_p_l = {Px - Lx, Py - Ly, Pz - Lz};
        double z_norm = math.getVecNorm(sub_p_l);
        double[] zv = math.devideVec(sub_p_l, z_norm);
        Point3D zv_point = new Point3D(zv[0], zv[1], zv[2]);
        double[] mult_v_z = math.multPP(new Point3D(Vx, Vy, Vz), zv_point);
        double x_norm = math.getVecNorm(mult_v_z);
        double[] xv = math.devideVec(mult_v_z, x_norm);
        double[] yv = math.multPP(zv_point, new Point3D(xv[0], xv[1], xv[2]));
        double[][] transMatrix = matrix.createInitMatrix();
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

    private String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    private void loadFile() {
        JFileChooser jfc = new JFileChooser();
        String workingDir = System.getProperty("user.dir");

        jfc.setCurrentDirectory(new File(workingDir));

        int returnValue = jfc.showOpenDialog(jfc.getParent());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String extension = getExtension(selectedFile.getName());
            if (extension.equals("scn")) {
                fileScn = new File(selectedFile.getName());
                init();
                this.repaint();
            } else if (extension.equals("viw")) {
                fileView = new File(selectedFile.getName());
                init();
                this.repaint();
            }
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    private void readScn() {
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
                int[] edges = {Integer.parseInt(splitStr[0]), Integer.parseInt(splitStr[1])};
                polygons.add(edges);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readView() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileView), "Cp1252"));
            String line;
            line = br.readLine();
            String position_str = line.replace("Position ", "");
            String[] splitStr = position_str.split("\\s+");
            Point3D position = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            this.Px = position.getX();
            this.Py = position.getY();
            this.Pz = position.getZ();
            line = br.readLine();
            String lookAt_str = line.replace("LookAt ", "");
            splitStr = lookAt_str.split("\\s+");
            Point3D lookAt = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            this.Lx = lookAt.getX();
            this.Ly = lookAt.getY();
            this.Lz = lookAt.getZ();
            line = br.readLine();
            String up_str = line.replace("Up ", "");
            splitStr = up_str.split("\\s+");
            Point3D up = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            this.Vx = up.getX();
            this.Vy = up.getY();
            this.Vz = up.getZ();
            line = br.readLine();
            String Window_str = line.replace("Window ", "");
            splitStr = Window_str.split("\\s+");
            l = Double.parseDouble(splitStr[0]);
            r = Double.parseDouble(splitStr[1]);
            b = Double.parseDouble(splitStr[2]);
            t = Double.parseDouble(splitStr[3]);
            line = br.readLine();
            String Viewport_str = line.replace("Viewport ", "");
            splitStr = Viewport_str.split("\\s+");
            vw = Integer.parseInt(splitStr[0]);
            vh = Integer.parseInt(splitStr[1]);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean clip(Line2D.Double line) {
        int[] bitsS = initBits(line.x1,line.y1);
        int[] bitsE = initBits(line.x2,line.y2);
        int[] bitsResultAnd = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            bitsResultAnd[i] = ((bitsS[i]!=0) && (bitsE[i]!=0))? 1:0;
        }
        if (checkBits(bitsResultAnd) != 0) {
            return false;
        }
        int[] bitsResultOr = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            bitsResultOr[i] = ((bitsS[i]!=0) || (bitsE[i]!=0))? 1:0;
        }
        if (checkBits(bitsResultOr) == 0) {
            return true;
        } else {

            return fixLine(line,bitsS,bitsE);
        }
    }

    private int checkBits(int[] bitsResult) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += bitsResult[i];
        }
        return sum;
    }

    private int[] initBits(double x,double y) {
        int[] bits = {0, 0, 0, 0};
        double xMin = 20, xMax = vw + 20, yMin = 20, yMax = vh + 20;
        if (y < yMin) {
            bits[0] = 1;
        }
        if (x > xMax) {
            bits[1] = 1;
        }
        if (y > yMax) {
            bits[2] = 1;
        }
        if (x < xMin) {
            bits[3] = 1;
        }
        return bits;
    }

    private boolean fixLine(Line2D.Double line ,int[] bitsS,int[] bitsE) {
        Point2D dL = new Point2D(20,20);
        Point2D uL = new Point2D(20,vw + 20);
        Point2D uR = new Point2D(vh + 20,vw + 20);
        Point2D dR = new Point2D(vh + 20,20);
        Point2D[] lines={dL,dR,uR,uL};
        while (checkBits(bitsS) != 0) {
            for (int i = 0; i < 4; i++) {
                if (bitsS[i] == 1) {
                    Point2D new_p = findIntersection(new Point2D((int)line.x1,(int)line.y1),
                            new Point2D((int)line.x2,(int)line.y2),lines[i],lines[(i+1)%4]);
                    line.setLine(new_p.getX(),new_p.getY(),line.x2,line.y2);
                    bitsS = initBits(line.x1,line.y1);
                    if (checkBits(bitsS)!=0){
                        return false;
                    }
                }
            }
        }
        while (checkBits(bitsE) != 0) {
            for (int i = 0; i < 4; i++) {
                if (bitsE[i] == 1) {
                    Point2D new_p = findIntersection(new Point2D((int)line.x1,(int)line.y1),
                            new Point2D((int)line.x2,(int)line.y2),lines[i],lines[(i+1)%4]);
                    line.setLine(line.x1,line.y1,new_p.getX(),new_p.getY());
                    bitsE = initBits(line.x2,line.y2);
                    if (checkBits(bitsE)!=0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Point2D findIntersection(Point2D p1x,Point2D p1y,Point2D p2x,Point2D p2y) {
        double a1 = p1y.getY() - p1x.getY();
        double b1 = p1x.getX() - p1y.getX();
        double c1 = a1*(p1x.getX()) + b1*(p1x.getY());
        double a2 = p2y.getY() - p2x.getY();
        double b2 = p2x.getX() - p2y.getX();
        double c2 = a2*(p2x.getX()) + b2*(p2x.getY());
        double determinant = a1*b2 - a2*b1;
        double x = (b2*c1 - b1*c2)/determinant;
        double y = (a1*c2 - a2*c1)/determinant;
        return new Point2D((int)x, (int)y);

    }

    @Override
    public void componentMoved(ComponentEvent arg0) {}

    @Override
    public void componentHidden(ComponentEvent arg0) {}

    @Override
    public void componentResized(ComponentEvent arg0) {
        if (z==20){
            int s=1;
        }
        System.out.println("componentResized");
        Component c = (Component)arg0.getSource();
        Dimension newSize = c.getSize();
        vw = (int)newSize.getWidth()-40;
        vh = (int)newSize.getHeight()-40;
        init();
        this.repaint();
        z++;
    }

    @Override
    public void componentShown(ComponentEvent e) {}
}
