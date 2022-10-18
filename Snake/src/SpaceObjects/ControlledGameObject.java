
package SpaceObjects;


import SpaceGame.SpaceController;
import SpaceInterfaces.Moveable;
import java.awt.Color;

/**
 *
 * @author Josh Ness
 */
public abstract class ControlledGameObject extends GameObject implements Moveable {

    protected SpaceController control;

    public ControlledGameObject(int xPosition, int yPosition, Color color, SpaceController control) {
        super(xPosition, yPosition, color);
        this.control = control;
    }
}
