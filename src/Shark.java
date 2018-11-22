/*
 Shark Game project by Jason Brennan.
 I am creating a game in which a shark has to swim through the sea
 avoiding all obstacles. The game should end if the shark collides with an obstacle
 and for each obstacle the shark successfully passes he will gain a point.
 */

// Importing various functionality for my program
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Creating the Shark class and implementing the ActionListener
public class Shark implements ActionListener {
    public static void main(String args[]) {

        shark = new Shark();
    }


    public static Shark shark;

    //Declaring our int constants for width and height
    public final int WIDTH = 800, HEIGHT = 800;

    public Data data;

    public Shark() {
        //Creating a new JFrame object called frame
        JFrame frame = new JFrame();
        //Creating a timer object of the Timer class
        Timer timer = new Timer(20, this);

        //Making the data variable not null to avoid null pointer exception
        data = new Data();

        //Adding the data variable of type Data to the JFrame
        frame.add(data);
        //Jframe will exit when we hit the close button
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        //JFrame size is set to our constant int's which are declared above.
        frame.setSize(WIDTH, HEIGHT);
        //We do not want the JFrame to be resisable so its set to false
        frame.setResizable(false);
        //Making the JFrame visible
        frame.setVisible(true);

        //Using the start method in the Timer class on the timer object
        timer.start();
    }

    //Creating the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        data.repaint();
    }

    //Creating the repaint method which comes from importing java.awt.*
    public void repaint(Graphics graphic) {
        System.out.println("testing timer");
    }

}
