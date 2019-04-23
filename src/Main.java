import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        MyCanvas canvas = new MyCanvas();
        Frame frame = new Frame();
        frame.add(canvas);
        WindowAdapter winAdapter = new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(winAdapter);
        frame.pack();
        frame.setVisible(true);
    }
}

