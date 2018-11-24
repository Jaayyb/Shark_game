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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

//Creating the Shark class and implementing the ActionListener and MouseListener
public class Shark implements ActionListener, MouseListener {
    //Main method
    public static void main(String args[]) {

        shark = new Shark();
    }


    public static Shark shark;

    //Declaring our int constants for width and height
    public final int WIDTH = 800, HEIGHT = 800;

    public Data data;

    //declaring boolean for when the game is started
    public boolean gameStart;
    //declaring boolean for when the game is lost
    public boolean gameEnd;

    //gameticks variable
    public int gameTick;
    //motion of the shark
    public int yMotion;

    //Creating a rectangle from awt import called sharkMain
    public Rectangle sharkMain;

    //Declaring an ArrayList named obstacles
    public ArrayList<Rectangle> obstacles;

    //Creating an instance of the Random class named rand
    public Random rand;

    public Shark() {
        //Creating a new JFrame object called frame
        JFrame frame = new JFrame();
        //Creating a timer object of the Timer class
        Timer timer = new Timer(20, this);

        //Making the data variable not null to avoid null pointer exception
        data = new Data();
        //rand is assigned to be a new Random
        rand = new Random();

        //Adding the data variable of type Data to the JFrame
        frame.add(data);
        /*Setting the title of our game.
        This will appear on the top left of the application window*/
        frame.setTitle("Deep Sea Shark Game");
        //Centering the application window
        frame.pack();
        frame.setLocationRelativeTo(null);
        //Jframe will exit when we hit the close button
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        //JFrame size is set to our constant int's which are declared above.
        frame.setSize(WIDTH, HEIGHT);
        //We do not want the JFrame to be resisable so its set to false
        frame.setResizable(false);

        //Making the JFrame visible
        frame.setVisible(true);

        //sharkMain is made a rectangle and given values
        sharkMain = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 40, 20);
        //obstacles is made an ArrayList of type Rectangle
        obstacles = new ArrayList<Rectangle>();

        //Adding our obstacles
        addObstacle(true);
        addObstacle(true);
        addObstacle(true);
        addObstacle(true);


        //Using the start method in the Timer class on the timer object
        timer.start();
    }

    //Creating the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {

        /*Declaring speed variable to determine how fast the obstacles approach
        If the game is too easy I can increase speed or if the game is too hard i can
        decrease the speed. */
        int speed = 10;
        gameTick++;

        if (gameStart) {

            //Keep loopoing until "i" gets to the same value as obstacles.size()
            for (int i = 0; i < obstacles.size(); i++) {
                Rectangle obstacle = obstacles.get(i);
                obstacle.x -= speed;
            }

            //If the remainder of ticks is = 0 and yMotion is less than 15 execute the statement
            if (gameTick % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            //
            for (int i = 0; i < obstacles.size(); i++) {
                Rectangle obstacle = obstacles.get(i);

                if (obstacle.x + obstacle.width < 0) {
                    obstacles.remove(obstacle);

                /*If its the top obstacle (y == 0) then add another obstacle
                /* Without this if statement the game will only provide
                a few obstacles. By using this if statement our game
                should provide obstacles infinitely
                 */
                    if (obstacle.y == 0) {
                        addObstacle(false);
                    }
                }
            }


            sharkMain.y += yMotion;

            //Checking for collision with obstacle
            for (Rectangle obstacle : obstacles) {
                if (obstacle.intersects(sharkMain)) {
                    gameEnd = true;

                    /*This will keep the shark from moving through obstacles
                    when the game is over.
                     */
                    sharkMain.x = obstacle.x - sharkMain.width;
                }
            }

            if (sharkMain.y > HEIGHT - 120 || sharkMain.y < 0) {
                gameEnd = true;
            }

            /*This if statement will execute when gameEnd is true.
            This will keep our shark on the screen just above
            the ground level.

            Without this code our shark falls through the world
            and dissapears at the end.
            */
            if (gameEnd)
            {
                sharkMain.y = HEIGHT - 120 - sharkMain.height;
            }

        }
        data.repaint();
    }

    //Creating the repaint method which comes from importing java.awt.*
    public void repaint(Graphics graphic) {
        //This will set the background colour for our rectangle
        graphic.setColor(Color.blue.darker());
        //This will set the amount of the rectangle we want to fill with colour
        graphic.fillRect(0, 0, WIDTH, HEIGHT);

        //Setting the colour of our sharkMain rectangle
        graphic.setColor(Color.white);
        //Filling our sharkMain rectangle with the colour
        graphic.fillRect(sharkMain.x, sharkMain.y, sharkMain.width, sharkMain.height);

        //This will set the colour of the ground floor
        graphic.setColor(Color.orange.darker());
        //This will fill the ground floor with our colour
        graphic.fillRect(0, HEIGHT - 120, WIDTH, 150);

        //Setting the colour of our seaweed on top of the ground floor
        graphic.setColor(Color.green.darker());
        //Adding the seaweed to just above the ground floor
        graphic.fillRect(0, HEIGHT - 120, WIDTH, 10);

        //for each loop to paint obstacles
        for(Rectangle obstacle : obstacles)
        {
            paintObstacle(graphic, obstacle);
        }

        //Setting the properties for our game over output
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Arial", 1, 100));


        if (!gameEnd)
        {
            graphic.drawString("Click to begin!", 50,  HEIGHT / 2 - 50);
        }

        /*If the boolean gameEnd is true then use the draw string method
        to output "Game Over" on the screen
         */
        if (gameEnd)
        {
            graphic.drawString("Game Over!!!", 100,  HEIGHT / 2 - 50);
        }
    }

    //A method for designing our obstacle
    public void paintObstacle(Graphics graphic, Rectangle obstacle)
    {
        //Setting the colour of our obstacle
        graphic.setColor(Color.red.darker());
        //Filling our obstacle rectangle
        graphic.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
    }

    //A method for adding obstacles to our game with a boolean parameter
    public void addObstacle(boolean start)
    {
        //Declaring variables
        int space = 300;
        int width = 100;
        /* When creating an obstacle we want the minimum to be 50
        and we want the maximum to be 300 */
        int height = 50 + rand.nextInt(300);


        //If statement for obstacles. If an obstacle is a starting obstacle do this. Otherwise execute the code in the else section of the statement
        if(start)
        {
            //Moving over by the width. Height is - 120 as we want it to be at the top of the seaweed
            obstacles.add(new Rectangle(WIDTH + width + obstacles.size() * 300, HEIGHT - height - 120, width, height));
            //Creating second obstacle
            obstacles.add(new Rectangle(WIDTH + width + (obstacles.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else{
            /* Getting a rectangle from the obstacles ArrayList. It takes from obstacles.size()
            therefore if we have an obstacle size 1 it will get index [1] of the ArrayList which
            we don't want therefore we - 1 from obstacles.size()
             */
            obstacles.add(new Rectangle(obstacles.get(obstacles.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            obstacles.add(new Rectangle(obstacles.get(obstacles.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    //MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
