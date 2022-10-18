
package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Josh Ness
 */
public class MenuPanel extends JPanel{
    private static final int MENU_WIDTH = PanelAdjustments.getWidth();
    private static final int MENU_HEIGHT = PanelAdjustments.getHeight();
    
    JRadioButton startButton = new JRadioButton("Start");
    
    
        MenuPanel(){
        this.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
    }
        
}
