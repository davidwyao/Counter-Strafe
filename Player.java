import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Playable player class.
 * 
 * @DAVID YAO
 * @15 June 2017
 */
public class Player extends Actor
{
    // Declare class variables
    private int score;
    private int maxFireRate, fireRate;
    private int maxCapacity, capacity;
    private int maxReloadDelay, reloadDelay;
    private int previousPlayerX, previousPlayerY; // For collision with boxes
    private int maxHealth, health;
    private int transparency;
    
    private boolean firing;
    private boolean reloading;
    private boolean dead;
    
    // Declare class objects
    private Box box;
    private MouseInfo m;
    private GreenfootImage sprite;
    private GreenfootSound emptyFireSound, reloadSound, dieSound;
    
    /**
     * Constructor for Player
     */
    public Player()
    {
        // Initialize player variables
        maxFireRate = 7;
        maxCapacity = 30;
        maxReloadDelay = 180;
        maxHealth = 25;
        
        // Allows quick resets
        fireRate = maxFireRate;
        capacity = maxCapacity;
        reloadDelay = maxReloadDelay;
        health = maxHealth;
        
        transparency = 255;
        
        firing = false;
        reloading = false;
        dead = false;
        
        sprite = new GreenfootImage("player1.png");
        setImage (sprite);
        
        emptyFireSound = new GreenfootSound("emptyfire.mp3");
        reloadSound = new GreenfootSound("reload.mp3");
        dieSound = new GreenfootSound("playerdeath.mp3");
        
        emptyFireSound.setVolume(40);
        reloadSound.setVolume(25);
        dieSound.setVolume(40);
    }

    public void act() 
    {
        if (health < 1)
            dead = true;
        if (!dead)
        {
            // Check mouse input (aiming, firing)
            checkMouse();
            // Check keyboard input (movement, reloading)
            checkKeys();
            // Checks if player is touching box or robot
            checkCollision();
            // Incrementing fireRate counter
            if (fireRate != maxFireRate)
                fireRate++;
            // Saves player's position at end of act to undo last movement if player collides with box in NEW act
            previousPlayerX = this.getX();
            previousPlayerY = this.getY();
        }
        else
        {
            dieSound.play();
            die();
        }
    }

    /**
     * Reads all mouse inputs (aiming, firing, picking up powerups)
     */
    private void checkMouse()
    {
        m = Greenfoot.getMouseInfo();
        if (m != null) // Only reads if mouse is in world
        {
            // Player 'aims' at mouse
            turnTowards(m.getX(), m.getY());
            if (m.getButton() == 1) // If left mouse button is pressed
            {
                if (!firing && Greenfoot.mousePressed(null)) // Fires on mouse press
                    firing = true;
                if (firing && Greenfoot.mouseClicked(null)) // Stops firing on mouse release
                    firing = false;
            }
            if (fireRate == maxFireRate && firing)
            {
                if (capacity > 0)
                {
                    // Adds new object of class Projectile to world
                    getWorld().addObject(new Projectile(getRotation()), getX(), getY());
                    // Reset fireRate counter, lower remaining shots by 1
                    fireRate = 0;
                    capacity--;
                }
                else {
                    fireRate = 0;
                    emptyFireSound.play();
                }
            }
        }
    }

    /**
     * Reads all keyboard inputs (movement, reloading)
     * Reloading is handled in this method.
     */
    private void checkKeys()
    {
        // Movement is independent of mouse position
        if (Greenfoot.isKeyDown("w"))
            this.setLocation(this.getX(), this.getY() - 3);
        if (Greenfoot.isKeyDown("s") && this.getY() < 550) // Prevents moving in to info bar
            this.setLocation(this.getX(), this.getY() + 3);
        if (Greenfoot.isKeyDown("a"))
            this.setLocation(this.getX() - 3, this.getY());
        if (Greenfoot.isKeyDown("d"))
            this.setLocation(this.getX() + 3, this.getY());
        if (Greenfoot.isKeyDown("r") && !reloading) // Cannot reload while already reloading
        {
            reloading = true;
            capacity = 0; // Prevents continuously reloading while firing (bottomless mag)
            reloadSound.play();
        }
        if (reloading)
        {
            reloadDelay--; // Counter decrements every act until it reaches 0
            if (reloadDelay == 0)
            {
                capacity = maxCapacity; // Refill capacity
                reloadDelay = maxReloadDelay; // Reset counter
                reloading = false;
            }
        }
    }

    /**
     * Check if player has touched a box. Undo movement made in act if player touches box.
     */
    private void checkCollision()
    {
        box = (Box)getOneIntersectingObject(Box.class);
        if (box != null) // If box is detected, player's position is reset to before it moved
            this.setLocation(previousPlayerX, previousPlayerY);
    }

    /**
     * Player slowly fades. gameOver() method in world Level is called when player fades completely.
     */
    private void die()
    {
        transparency -= 5;
        sprite.setTransparency(transparency);
        if (transparency == 0)
        {
            Level level = (Level)getWorld();
            level.gameOver();
            getWorld().removeObject(this);
        }
    }

    /**
     * Return shots remaining as int.
     */
    public int getShots()
    {
        return capacity;
    }

    /**
     * Return maximum ammo as int.
     */
    public int getMaxShots()
    {
        return maxCapacity;
    }

    /**
     * Return current score as int.
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Return current health as int.
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * Increase score by amount passed through parameter.
     */
    public void raiseScore(int amount)
    {
        score += amount;
    }

    /**
     * Decrease score by amount passed through parameter.
     */
    public void lowerScore(int amount)
    {
        if (score > 0 && health > 0) // Score cannot drop below 0
            score -= amount;
    }

    /**
     * Decrement player health.
     */
    public void removeHealth()
    {
        if (health > 0)
            health--;
    }

    /**
     * Increase player health by 5 from medkit, unless that would raise it above maximum.
     */
    public void restoreHealth()
    {
        if (health < maxHealth)
        {
            if (health + 5 <= maxHealth)
                health += 5;
            else
                health = maxHealth;
        }
    }
    
    /**
     * Restore remaining shots to maximum instantly.
     */
    public void restoreAmmo()
    {
        capacity = maxCapacity;
    }
}