import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * All sounds sourced from soundbible.com, except impact sound from myinstants.com.
 * 
 * Game is won by completing all three levels. Advance forward in levels by
 * eliminating all robots on the current level without running out of health.
 * Points are awarded per elimination, and taken away for every hit on the player.
 * 
 * WASD to move. Mouse to aim, left mouse button to fire. Right mouse button to
 * pick up health packs. 'R' to reload.
 * 
 * KNOWN BUGS: Clunky collision with boxes. Players and robots will be clipped in
 * if they hug the box and turn at an angle. Workaround: Turn parallel to box side
 * and move away.
 * 
 * @author DAVID YAO
 * @version 15 June 2017
 */
public class Level extends World
{
    // Declaring class varaibles
    private int level;
    private int robotCount;
    private int maxLevelDelay, levelDelay; // Delay in acts between eliminating all robots and starting new level
    
    private boolean advancingLevel;
    private boolean gameOver;
    // Declaring class objects
    private Player player;
    private InfoBar infoBar;
    /**
     * Constructor for objects of class Level.
     */
    public Level()
    {
        // Create 800x600 world, cell size 1
        super(800, 600, 1);
        
        player = new Player();
        infoBar = new InfoBar();
        
        maxLevelDelay = 150;
        levelDelay = maxLevelDelay;
        advancingLevel = false;
        gameOver = false;
        
        // Begin setting up first level
        level = 1;
        robotCount = 4;
        // Put player at coordinates 405, 500
        addObject (player, 405, 500);
        // Add four robots at coordinates specified
        addObject (new Robot(player, false), 200, 100);
        addObject (new Robot(player, true), 300, 100);
        addObject (new Robot(player, false), 500, 100);
        addObject (new Robot(player, true), 600, 200);
        // Add three boxes at coordinates specified
        addObject (new Box(), 400, 400);
        addObject (new Box(), 200, 400);
        addObject (new Box(), 600, 400);
        // Place HUD bar at bottom middle
        addObject (infoBar, 400, 585);
        
        infoBar.update("COUNTER-STRAFE");
        // Prevent health kids from being drawn over important elements
        setPaintOrder(Player.class, Robot.class, GameOver.class, NextLevel.class, HealthKit.class);
    }

    public void act()
    {
        // Update HUD bar with current info of player (will update when health first drops to 0)
        if (player != null && player.getWorld() != null && player.getHealth() > -1 && !gameOver)
            infoBar.update(player.getHealth(), player.getScore(), player.getShots(), player.getMaxShots());
        // Begin process of advancing to next level if all robots are eliminated
        if (robotCount == 0)
        {
            advanceLevel();
        }
    }

    /**
     * Draws advancing level overlay to notify player. Calls nextLevel() method to set up following level.
     */
    private void advanceLevel()
    {
        if (!advancingLevel) // Ensures next level overlay is drawn only once
        {
            addObject (new NextLevel(), 400, 300);
            advancingLevel = true;
        }
        levelDelay--;
        if (levelDelay == 0) // 150 acts
        {
            levelDelay = maxLevelDelay;
            if (level < 3)
            {
                level++;
                nextLevel();
            }
            else
                gameOver(); // Ends game upon completion of level 3
        }
    }

    /**
     * Sets up level following previous level by removing boxes and overlays and replacing them with boxes and robots of new level.
     */
    private void nextLevel()
    {
        advancingLevel = false;
        // Removing parts of previous level
        this.removeObjects(this.getObjects(Box.class));
        this.removeObjects(this.getObjects(NextLevel.class));
        
        if (level == 2)
        {
            player.restoreAmmo(); // Resets ammo to maximum
            robotCount = 5;
            player.setLocation(100, 300);
            addObject (new Box(), 200, 500);
            addObject (new Box(), 200, 300);
            addObject (new Box(), 200, 100);
            addObject (new Robot(player, false), 400, 200);
            addObject (new Robot(player, true), 500, 50);
            addObject (new Robot(player, false), 500, 300);
            addObject (new Robot(player, true), 600, 400);
            addObject (new Robot(player, false), 400, 500);
        }
        else
        {
            player.restoreAmmo();
            robotCount = 5;
            player.setLocation(100, 300);
            addObject (new Box(), 200, 500);
            addObject (new Box(), 200, 300);
            addObject (new Box(), 200, 100);
            addObject (new Robot(player, true), 400, 200);
            addObject (new Robot(player, true), 500, 50);
            addObject (new Robot(player, true), 500, 300);
            addObject (new Robot(player, true), 600, 400);
            addObject (new Robot(player, true), 400, 500);
        }
    }

    /**
     * Called if the player dies or completes the third level. Adds game over button to allow restart.
     */
    public void gameOver()
    {
        gameOver = true; // Stops updating of info bar
        infoBar.update("GAME OVER");
        addObject (new GameOver(player.getHealth(), player.getScore()), 400, 300);
    }

    /**
     * Subtract one robot from counter. Level advances if counter drops to 0.
     */
    public void loseRobot()
    {
        robotCount--;
    }
}
