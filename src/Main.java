import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();

        MyCanvas canvas = new MyCanvas();

        frame.add(canvas);
        WindowAdapter winAdapter = new WindowAdapter(){
            private void winClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        frame.addWindowListener(winAdapter);

        frame.pack();

        frame.setVisible(true);
    }
}

