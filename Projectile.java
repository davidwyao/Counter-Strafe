import greenfoot.*;

/**
 * Projectile moves very fast in a straight line until it touches a target or the edge of the world.
 * 
 * @DAVID YAO
 * @15 June 2017
 */
public class Projectile extends Actor
{
    // Declaring class variables
    private boolean impact;
    
    // Declaring class objects
    private Robot robot;
    private Box box;
    private Player player;
    private GreenfootImage sprite;
    private GreenfootSound fireSound, hitSound, boxHitSound;
    
    /**
     * Constructor for Projectile, requires a direction to face and move.
     */
    public Projectile (int direction)
    {
        setRotation (direction); // Faces and travels in same direction as actor who creates it
        impact = false; // Set to true on collision with target. Object deletes itself if true.
        sprite = new GreenfootImage("projectile.png");
        setImage (sprite);
        
        fireSound = new GreenfootSound("playerfire.mp3");
        hitSound = new GreenfootSound("impact.mp3");
        boxHitSound = new GreenfootSound("boximpact.mp3");
        fireSound.setVolume(30);
        hitSound.setVolume(30);
        boxHitSound.setVolume(30);
        
        fireSound.play();
    }

    /**
     * Lines up projectile with end of barrel of weapon upon creation.
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
        move(25); // Projectile travels 25 pixels per frame
        checkCollisions();
    }   

    /**
     * Check if projectile has intersected with any Robot, Box, Player, or edge of world.
     * Projectile is deleted if it has.
     */
    private void checkCollisions()
    {
        robot = (Robot)getOneIntersectingObject(Robot.class);
        if (robot != null)
        {
            robot.removeHealth(); // Take health from robot
            hitSound.stop();
            hitSound.play();
            impact = true;
        }
        box = (Box)getOneIntersectingObject(Box.class);
        if (box != null)
        {
            boxHitSound.stop();
            boxHitSound.play();
            impact = true;
        }
        player = (Player)getOneIntersectingObject(Player.class);
        if (player != null)
        {
            player.lowerScore(5); // Take score from robot
            player.removeHealth(); // Take player from robot
            hitSound.stop();
            hitSound.play();
            impact = true;
        }
        if (isatEdge())
        {
            impact = true;
        }
        if (impact)
        {
            getWorld().removeObject(this);
        }
    }

    /**
     * Check if projectile has hit edge of world. Method borrowed from Mr. Cohen.
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
