import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {
    public static double l;
    public static double r;
    public static double b;
    public static double t;
    public static Point3D position;
    public static Point3D lookAt;
    public static Point3D up;
    public static int vw;
    public static int vh;
    public static  ArrayList<Point3D> vertexes = new ArrayList<>();
    public static  ArrayList<int[]>polygons = new ArrayList<>();

    public static void main(String[] args) {
        Transform test = new Transform();
        double[][] matrix = test.Translate(1, 2, 3);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        readScn();
        readView();
        MyCanvas canvas=new MyCanvas(vertexes,polygons,position,lookAt,up,l,r,b,t,vw,vh);
        JFrame frame = new JFrame();

        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(vw+40,vh+40);
        frame.setVisible(true);
    }
    public static void  readScn() {
        String filePath = "example3d.scn";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));
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

    public static void readView() {
        String filePath = "example3d.viw";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));
            String line;
            line = br.readLine();
            String position_str = line.replace("Position ","");
            String[] splitStr = position_str.split("\\s+");
            position = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            line = br.readLine();
            String lookAt_str = line.replace("LookAt ","");
            splitStr = lookAt_str.split("\\s+");
            lookAt = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            line = br.readLine();
            String up_str = line.replace("Up ","");
            splitStr = up_str.split("\\s+");
            up = new Point3D(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]));
            line = br.readLine();
            String Window_str = line.replace("Window ","");
            splitStr = Window_str.split("\\s+");
            l=Double.parseDouble(splitStr[0]);
            r=Double.parseDouble(splitStr[1]);
            b=Double.parseDouble(splitStr[2]);
            t=b=Double.parseDouble(splitStr[3]);
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

