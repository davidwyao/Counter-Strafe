import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; // Need Color and Font for drawing ScoreBar
import java.awt.Font;

/**
 * Displays player health, score, and ammo
 * 
 * InfoBar class borrowed from Mr. Cohen
 * @Jordan Cohen
 * @15 June 2017
 */
public class InfoBar extends Actor
{
    
    private GreenfootImage scoreBoard;
    private Color background;
    private Color foreground;
    private Font textFont;
    private String text;

    public InfoBar ()
    {
        // Set up the image to be 800 wide and 30 tall
        scoreBoard = new GreenfootImage (800, 30);
        // Declare colour objects for use within this class (red and white)
        background = new Color (150, 150, 150);
        foreground = new Color (255, 255, 255);
        // Initialize the font - chose Courier because it's evenly spaced
        textFont = new Font ("Skia", Font.BOLD, 24);
        // Set the colour to red and fill the background of this rectangle
        scoreBoard.setColor(background);
        scoreBoard.fill();
        // Assign the image we just created to be the image representing THIS actor
        this.setImage (scoreBoard);
        // Prepare the font for use within the code
        scoreBoard.setFont(textFont);
    }

    /**
     * Method to update the value shown on the score board
     * 
     * @param   hp      The amount of HP the player has
     * @param   level   The current level
     * @param   score   The player's current score
     * @param   ammo    The remaining amount of ammo
     */
    public void update (int hp, /*int level,*/ int score, int ammo, int maxAmmo)
    {
        // In order to make uniform sizes and preceding zeros:
        String hpString, scoreString;
        // If there is only one digit
        if (hp / 10 == 0)
            hpString = "0" + hp;
        else
            hpString = Integer.toString(hp);
            
        // If there are 3 digits
        if (score >= 100)
            scoreString = Integer.toString(score);
        // Two digits - insert one zero
        else if (score >= 10)
            scoreString = "0" + score;
        // One digit - insert two zeros
        else
            scoreString = "00" + score;
        
        text = "HEALTH: " + hpString + "  SCORE: " + scoreString + "  AMMO: " + ammo + "/" + maxAmmo;
            
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
        scoreBoard.setColor(background);
        scoreBoard.fill();
        
        // Write text over the solid background
        scoreBoard.setColor(foreground);  
        // Smart piece of code that centers text
        int centeredY = 400 - ((output.length() * 14)/2);
        // Draw the text onto the image
        scoreBoard.drawString(output, centeredY, 22);
    }
}
