import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Transform test = new Transform();
        double[][] matrix = test.Translate(1, 2, 3);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        MyCanvas canvas = new MyCanvas();
        JFrame frame = new JFrame();
        frame.setSize(800 + 40,800 + 40);

        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}

