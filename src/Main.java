import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MyCanvas canvas = new MyCanvas();
        JFrame frame = new JFrame();
        frame.setSize(800 + 40,800 + 40);

        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}

