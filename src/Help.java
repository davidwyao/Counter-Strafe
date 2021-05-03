import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; // Need Color and Font for drawing ScoreBar
import java.awt.Font;

/**
 * Displays help message
 * 
 * Help class adapted from InfoBar class borrowed from Mr. Cohen
 * @DAVID YAO
 * @15 June 2017
 */
public class Help extends Actor
{
    // Initialize class objects
    private GreenfootImage message;
    private Color background;
    private Color foreground;
    private Font textFont;
    private String text;

    /**
     * Constructor for Help.
     */
    public Help ()
    {
        // Set up the image to be 800 wide and 120 tall
        message = new GreenfootImage (800, 120);
        // Declare colour objects for use within this class (red and white)
        background = new Color (180, 60, 60);
        foreground = new Color (255, 255, 255);
        // Initialize the font
        textFont = new Font ("Skia", Font.BOLD, 24);
        // Set the colour to red and fill the background of this rectangle
        message.setColor(background);
        message.fill();
        // Assign the image we just created to be the image representing THIS actor
        this.setImage (message);
        // Prepare the font for use within the code
        message.setFont(textFont);
        text = "LMB: Fire. RMB: Use powerup. WASD: Move. R: Reload.";
        this.update (text);
    }

    /**
     * Takes a String and displays it centered to the screen. Note this gets called by the other
     * update() method.
     * 
     * @param   output  A string to be output, centered on the screen.
     */
    public void update (String output)
    {
        // Refill the background with background color
        message.setColor(background);
        message.fill();

        // Write text over the solid background
        message.setColor(foreground);  
        // Smart piece of code that centers text
        int centeredY = 400 - ((output.length() * 12)/2);
        // Draw the text onto the image
        message.drawString(output, centeredY, 70);
    }
}
