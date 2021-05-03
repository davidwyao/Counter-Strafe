import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Main menu on which the game should start. No gameplay.
 * Instructions and a button to begin play are accessible.
 * 
 * @author DAVID YAO 
 * @version 15 June 2017
 */
public class Menu extends World
{
    // Declaring class objects
    private InfoBar infoBar;
    private HelpButton helpButton;
    private PlayButton playButton;

    /**
     * Constructor for objects of class Menu.
     * 
     */
    public Menu()
    {    
        // Create a new world with 800x600 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 

        infoBar = new InfoBar();
        helpButton = new HelpButton();
        playButton = new PlayButton();

        addObject (infoBar, 400, 585);
        infoBar.update("COUNTER-STRAFE");

        // Adding clickable objects to world (buttons)
        addObject (playButton, 400, 200);
        addObject (helpButton, 400, 400);
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(playButton))
            Greenfoot.setWorld(new Level()); // Sets world to Level, where gameplay begins immediately
            
        else if (Greenfoot.mouseClicked(helpButton))
            addObject (new Help(), 400, 400); // Draws help text over help button.
    }
}
