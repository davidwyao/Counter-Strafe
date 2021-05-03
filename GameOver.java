import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; // Need Color and Font for drawing ScoreBar
import java.awt.Font;

/**
 * Displays game over message
 * 
 * GameOver class adapted from InfoBar class borrowed from Mr. Cohen
 * @ David Yao, adapted from Jordan Cohen
 * 15 June 2017
 */
public class GameOver extends Actor
{
    // Declaring class objects
    private String text;
    private GreenfootImage message;
    private Color background;
    private Color foreground;
    private Font textFont;

    /**
     * Constructor for GameOver. Requires player health and score.
     */
    public GameOver (int health, int score)
    {
        // Set up the image to be 800 wide and 120 tall
        message = new GreenfootImage (800, 120);
        // Declare colour objects for use within this class (red and white)
        background = new Color (180, 60, 60);
        foreground = new Color (255, 255, 255);
        // Initialize the font
        textFont = new Font ("Skia", Font.BOLD, 30);
        // Set the colour to red and fill the background of this rectangle
        message.setColor(background);
        message.fill();
        // Assign the image we just created to be the image representing THIS actor
        this.setImage (message);
        // Prepare the font for use within the code
        message.setFont(textFont);
        this.update(health, score);
    }

    public void act()
    {
        // If player clicks this object they are returned to world Menu
        if (Greenfoot.mouseClicked(this))
            Greenfoot.setWorld(new Menu());
    }

    /**
     * Method to customize game over message
     * 
     * @param   health  The amount of HP the player has
     * @param   score   The player's current score
     */
    public void update (int health, int score)
    {
        if (health > 0) // Player won
            text = "CONGRATS! Final score: " + score + ". Click to play again.";
        else // Player lost
            text = "YOU DIED. Final score: " + score + ". Click to play again...";
        // Now that we have built the text to output...

        // this.update (String) calls the other version of update(), in this case
        // update(String) - see below
        this.update (text);
    }

    /**
     * Takes a String and displays it centered to the screen. Note this gets called bu the other
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
        int centeredY = 400 - ((output.length() * 14)/2);
        // Draw the text onto the image
        message.drawString(output, centeredY, 70);
    }
}
