import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        // Create the main frame.
        Frame frame = new Frame();
        // Create the canvas to be shown.
        MyCanvas canvas = new MyCanvas();
        // Add the canvas as the main component of the frame.
        frame.add(canvas);
        // Create window adapter to control the closing event.
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

