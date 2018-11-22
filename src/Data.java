import javax.swing.*;
import java.awt.*;

public class Data extends JPanel {


    @Override
    protected void paintComponent(Graphics g) {
        /*SUPER refers to the JComponent will call code from there first
        before it calls our code
         */
        super.paintComponent(g);

        Shark.shark.repaint(g);
    }
}
