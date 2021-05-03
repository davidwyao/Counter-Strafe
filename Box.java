import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Box that blocks bullets, players, and robots.
 * Collision with box is coded within individual
 * Projectile, Player, and Robot classes
 * 
 * @DAVID YAO
 * @15 June 2017
 */
public class Box extends Actor
{
    // Declare class object
    private GreenfootImage sprite;
    
    /**
     * Constructor for box
     */
    public Box()
    {
        sprite = new GreenfootImage("box.png");
        this.setImage(sprite);
    }
}
