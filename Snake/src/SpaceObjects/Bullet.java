
package SpaceObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Josh Ness
 */
public class Bullet extends MovingGameObject {

    private int diameter;

    public Bullet(int xPosition, int yPosition, int diameter, Color color) {
        super(xPosition, yPosition, 0, -20, color);
        this.diameter = diameter;
    }

    public int getDiameter() {
        return diameter;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(xPosition, yPosition, diameter, diameter);
        
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition, yPosition, diameter, diameter);
        return bounds;
    }
}
