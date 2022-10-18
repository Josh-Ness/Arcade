
package SpaceObjects;

import Game.GameFrame;
import Snake.SnakePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Josh Ness
 */
public class Enemy extends MovingGameObject {
private int moveIterator =0;
private int health;
    
    public Enemy(int xPosition, int yPosition, int xVelocity, int yVelocity, Color color, int health) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.health = health;
    }

    @Override
    public void draw(Graphics g) {
        //Body
        g.setColor(color);
        g.fillOval(xPosition, yPosition,35, 35);
        //Arms
        g.fillRect(xPosition+25, yPosition+15, 25, 5);
        g.fillRect(xPosition-15, yPosition+15, 25, 5);
        g.fillRect(xPosition+45, yPosition+15, 5, 30);
        g.fillRect(xPosition-15, yPosition+15, 5, 30);
        //Eyes
        g.setColor(Color.RED);
        g.fillOval(xPosition+7, yPosition+10, 8, 8);
        g.fillOval(xPosition+22, yPosition+10, 8, 8);
        //Mouth
        g.setColor(Color.WHITE);
        g.fillRect(xPosition+9, yPosition+28, 19, 3);
        g.setColor(Color.BLACK);
        g.drawLine(xPosition+10, yPosition+28, xPosition+13, yPosition+31);
        g.drawLine(xPosition+13, yPosition+31, xPosition+16, yPosition+28);
        g.drawLine(xPosition+16, yPosition+28, xPosition+19, yPosition+31);
        g.drawLine(xPosition+19, yPosition+31, xPosition+22, yPosition+28);
        g.drawLine(xPosition+22, yPosition+28, xPosition+25, yPosition+31);
    }
    
    public Beam shootBeam(){
        return new Beam(xPosition, yPosition, 0, 10, 3, 10, Color.ORANGE);
    }
    
    public void move(){
        xPosition += xVelocity;
        yPosition += yVelocity;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void adjustHealth(){
        health--;
    }
    
    @Override
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition-10, yPosition, 50, 30);
        return bounds;
    }
}
