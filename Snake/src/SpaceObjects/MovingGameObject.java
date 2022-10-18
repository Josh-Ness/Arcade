
package SpaceObjects;

import SpaceInterfaces.Moveable;
import java.awt.Color;

/**
 *
 * @author Josh Ness
 */
public abstract class MovingGameObject extends GameObject implements Moveable {

    protected int xVelocity;
    protected int yVelocity;

    public MovingGameObject(int xPosition, int yPosition, int xVelocity, int yVelocity, Color color) {
        super(xPosition, yPosition, color);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    @Override
    public void move() {
        xPosition += xVelocity;
        yPosition += yVelocity;
    }
}
