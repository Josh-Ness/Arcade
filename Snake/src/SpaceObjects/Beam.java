
package SpaceObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Josh Ness
 */
public class Beam extends MovingGameObject {

    private int width;
    private int height;

    public Beam(int xPosition, int yPosition, int xVelocity, int yVelocity, int width, int height, Color color) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(xPosition, yPosition, width, height);
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition, yPosition, width, height);
        return bounds;
    }

}
