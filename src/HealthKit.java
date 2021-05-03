import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Dropped by heavy (red) robots. Restores 5 health to player.
 * Right click to use.
 * 
 * @DAVID YAO
 * @15 June 2017
 */
public class HealthKit extends Actor
{
    // Declare class objects
    private Player player;
    private GreenfootImage sprite;
    private GreenfootSound pickup;
    private MouseInfo m;
    
    /**
     * Constructor for HealthKit
     */
    public HealthKit()
    {
        sprite = new GreenfootImage ("healthkit.png");
        setImage(sprite);
        
        pickup = new GreenfootSound ("powerup.mp3");
        pickup.setVolume(25);
    }

    public void act() 
    {
        checkMouse(); // Check if player has attempted to use kit
    }    

    /**
     * Player cna only use kit when they right click while touching it
     */
    private void checkMouse()
    {
        m = Greenfoot.getMouseInfo();
        if (m != null && m.getButton() == 3)
        {
            player = (Player)getOneIntersectingObject(Player.class); // Check if player is touching kit
            if (player != null)
            {
                pickup.play();
                player.restoreHealth(); // Calls restoreHealth() method in class Player
                getWorld().removeObject(this);
            }
        }
    }
}
