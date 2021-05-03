import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Robots that attack the player. Pathfinding included!
 * 
 * KNOWN BUGS: Like players, robots can get clipped into boxes.
 * 
 * @DAVID YAO
 * @15 June 2017
 */
public class Robot extends Actor
{
    // Declaring class variables
    private int health;
    private int maxFireRate, fireRate;
    private int maxBurst, burstLength;
    private int maxReloadDelay, reloadDelay;
    private int transparency;
    private int previousBotX, previousBotY, direction;

    private boolean firing, canFire;
    private boolean heavyRobot; // Heavy robots cannot move and can fire faster, with more bullets per burst
    private boolean reloading;
    private boolean dead;

    // Declaring class objects
    private MouseInfo m;
    private Player player;
    private Box box;
    private Robot robot;
    private GreenfootImage sprite;
    private GreenfootSound fireSound, dieSound;

    /**
     * Constructor for class robot
     */
    public Robot (Player p, boolean heavy)
    {
        player = p;
        health = 3;
        if (!heavy)
        {
            maxFireRate = 9;
            maxBurst = 6;
            maxReloadDelay = 120;
            sprite = new GreenfootImage ("robot.png");
            fireSound = new GreenfootSound("robotfire.mp3");
            heavyRobot = false;
        }
        else
        {
            maxFireRate = 5;
            maxBurst = 15;
            maxReloadDelay = 150;
            sprite = new GreenfootImage ("heavyrobot.png");
            fireSound = new GreenfootSound ("heavybotfire.mp3");
            heavyRobot = true;
        }
        // Allow quick resets
        fireRate = maxFireRate;
        burstLength = maxBurst;
        reloadDelay = maxReloadDelay;

        transparency = 255;
        direction = 1; // Negated (-1) if robot touches other robot or box, so they move in opposite direction

        firing = false;
        reloading = false;
        dead = false;

        fireSound.setVolume(30);
        dieSound = new GreenfootSound ("robotdie.mp3");
        setImage (sprite);
    }

    public void act() 
    {
        if (!dead)
        {
            getWorld().addObject(new SightCheck(getRotation(), this), getX(), getY()); // Checks line of sight to player, robot fires only if line of sight exists
            fightPlayer(); // Attempts to fire at and move to get line of sight on player
            checkCollisions(); // Check if robot has touched box or other robot

            // Saves position in act so if in next act robot touches object, position is reset
            previousBotX = this.getX();
            previousBotY = this.getY();
        }
        else
            die();
    }

    /**
     * Attempts to fire at and move to get line of sight on player
     */
    private void fightPlayer()
    {
        if (player != null && player.getWorld() != null)
        {
            turnTowards(player.getX(), player.getY()); // Robot will always aim at player
            if (canFire && fireRate == maxFireRate) // canFire is determined by line of sight, method to change located in SightCheck class
            {
                if (burstLength > 0)
                {
                    getWorld().addObject(new Projectile(getRotation()), getX(), getY());
                    fireRate = 0;
                    burstLength--;
                    // Greenfoot cannot play a sound while it is already playing
                    fireSound.stop();
                    fireSound.play();
                }
            }
            if (!heavyRobot) // Can move only if robot is not heavy
            {
                if (!canFire && this.getY() < 550) // Move only when it needs to (no line of sight in current position), also prevents going in to info bar
                {
                    // Direction variable negated if robot touches box/other robot
                    turn(direction * 90);
                    move(1);
                    turn(-direction * 90); // Robot turns to the side, moves to the side, and turns back in one act (appears to be strafing)
                }
                else // Moves toward player if line of sight established
                    move(1);
            }
        }
        if (fireRate != maxFireRate)
            fireRate++;
        if (burstLength == 0) // Prevents overwhelming suppressive fire
        {
            reloadDelay--;
            if (reloadDelay == 0)
            {
                burstLength = maxBurst;
                reloadDelay = maxReloadDelay;
            }
        }
    }

    /**
     * Check if robot has touched box or other robot.
     * Direction is switched to opposite if true.
     */
    private void checkCollisions()
    {
        box = (Box)getOneIntersectingObject(Box.class);
        if (box != null) // If box is detected, robot's position is reset to before it moved
        {
            this.setLocation(previousBotX, previousBotY);
            direction = -1;
        }
        robot = (Robot)getOneIntersectingObject(Robot.class);
        if (robot != null) // If robot is detected, robot's position is reset to before it moved
        {
            this.setLocation(previousBotX, previousBotY);
            direction = -1;
        }
    }

    /**
     * Robot slowly fades from world until it disappears. Decrements robot
     * counter in Level (level is switched when this counter reaches 0).
     */
    private void die()
    {
        transparency -= 5;
        sprite.setTransparency(transparency);
        if (transparency == 0)
        {
            Level level = (Level)getWorld();
            level.loseRobot();
            if (heavyRobot) // Heavy robots will drop health kits
                getWorld().addObject(new HealthKit(), this.getX(), this.getY());
            getWorld().removeObject(this);
        }
    }

    /**
     * Called by Projectile. Removes health from robot.
     */
    public void removeHealth()
    {
        health--;
        if (health == 0)
        {
            dead = true;
            dieSound.play();
            player.raiseScore(100);
        }
    }

    /**
     * Called by SightCheck class. If SightCheck has touched Player,
     * robot can fire.
     */
    public void canFire()
    {
        canFire = true;
    }

    /**
     * Called by SightCheck class. If SightCheck has not touched Player,
     * robot cannot fire.
     */
    public void cantFire()
    {
        canFire = false;
    }
}
