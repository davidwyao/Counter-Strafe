import greenfoot.*;

/**
 * My implementation of line of sight detection. Robot fires invisible, very fast projectile
 * that behaves much like Projectile class. Robot will only fire Projectiles if SightCheck has
 * touched Player.
 * 
 * @DAVID YAO
 * @16 June 2017
 */
public class SightCheck extends Actor
{
    // Declare class variables
    private boolean impact;

    // Declare class objects
    private Robot robot;
    private Robot firingRobot;
    private Box box;
    private Player player;
    private GreenfootImage sprite;

    /**
     * Constructor for SightCheck. Requires direction of robot and the specific
     * robot object that fired SightCheck, so the line of sight is not traced back
     * to the wrong robot.
     */
    public SightCheck (int direction, Robot robot)
    {
        setRotation (direction); // Faces and travels in same direction as actor who creates it
        impact = false; // Set to true on collision with any actor. Object deletes itself if true.
        sprite = new GreenfootImage("sightcheck.png"); // Blank image
        setImage (sprite);
        firingRobot = robot;
    }

    /**
     * Lines up projectiles the same way objects of class Projectile are to maximize
     * accuracy of line of sight.
     */
    public void addedToWorld (World w)
    {
        // Lining up projectile with barrel
        turn(90);
        move(8);
        turn(-90);
        move(50);
    }

    public void act() 
    {
        move(100); // Projectile travels 100 pixels per frame
        checkCollisions(); // Check what projectile has hit
    }   

    /**
     * Robot can only fire if object of class Player has been hit. If
     * SightCheck hits anything else, robot can no longer fire.
     */
    private void checkCollisions()
    {
        robot = (Robot)getOneIntersectingObject(Robot.class);
        if (robot != null)
        {
            firingRobot.cantFire();
            impact = true;
        }
        box = (Box)getOneIntersectingObject(Box.class);
        if (box != null)
        {
            firingRobot.cantFire();
            impact = true;
        }
        player = (Player)getOneIntersectingObject(Player.class);
        if (player != null)
        {
            firingRobot.canFire();
            impact = true;
        }
        if (isatEdge())
        {
            firingRobot.cantFire();
            impact = true;
        }
        if (impact)
            getWorld().removeObject(this);
    }

    /**
     * Check if projectile has touched edge of world.
     */
    private boolean isatEdge()
    {
        if (getX() <= 0 || getX() >= getWorld().getBackground().getWidth() - 1)
            return true;
        if (getY() <= 0 || getY() >= getWorld().getBackground().getHeight() - 45)
            return true;
        return false;
    }
}
