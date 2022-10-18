
package Snake;

import Game.GameFrame;
import Game.PanelAdjustments;
import GameEntities.Apple;
import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Random;


/**
 *
 * @author Josh Ness
 */
public class SnakePanel extends JPanel implements ActionListener{
    
    private static final int GAME_WIDTH = PanelAdjustments.getWidth();
    private static final int GAME_HEIGHT = PanelAdjustments.getHeight();
    private static final int UNIT_SIZE = PanelAdjustments.getUnitSize();
    private static final int GAME_UNITS = (GAME_WIDTH * GAME_HEIGHT) / UNIT_SIZE; //Number of units that can fit on the screen
    private static final int DELAY = 75;
    
    private final int xCords[] = new int[GAME_UNITS]; //x Coordinates of snake units
    private final int yCords[] = new int[GAME_UNITS]; //y Coordinates of snake units
    private int snakeLength = 6; //Starting length of the snake
    private int applesAte;  //Number of apples that have been consumed
    private int appleX; //x coordinate of apple
    private int appleY; //y coordinate of apple
    private char direction = 'R'; //direction of traveling snake. R= RIGHT, L = LEFT, U = UP, D = DOWN
    private boolean isRunning = false;
    Timer timer;
    private Random random;
    public JButton menuBtn = new JButton("Return to Menu");          
                

    
    public SnakePanel(){    //Must call startGame after creating instance of panel
        random = new Random();
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());    // Detect key press
        
        menuBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            }
        });
    }
    public void startGame(){
        yCords[0]=(((GAME_HEIGHT/UNIT_SIZE)/2)*UNIT_SIZE)-UNIT_SIZE;
        createApple();
        timer = new Timer(DELAY, this);
        timer.start();
        isRunning=true;
    }
    
    public int getScore(){
        return applesAte;
    }
    
    public void paint(Graphics g){
        super.paint(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(isRunning){
            for(int i=0; i<GAME_HEIGHT / UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, GAME_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, GAME_WIDTH, i*UNIT_SIZE);
            }
            
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            
            for(int i=0; i<snakeLength; i++){
                if(i==0){ //Head of snake
                    g.setColor(Color.GREEN);
                    g.fillRect(xCords[i], yCords[i], UNIT_SIZE, UNIT_SIZE);
                }else{
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(xCords[i], yCords[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesAte, (GAME_WIDTH - metrics.stringWidth("Score: " + applesAte))/2, g.getFont().getSize());
        }else
            endGame(g);
    }
    /*
    Spawn an apple randomly after the previous one is consumed covering one unit
    */
    public void createApple(){
        appleX = random.nextInt((int)(GAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(GAME_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }
    
    //Mehod to move snake
    public void move(){
        for(int i = snakeLength; i>0; i--){ //i == 0 is the head, but we want to start at tail
            xCords[i] = xCords[i-1];
            yCords[i] = yCords[i-1];
        }
        switch(direction){
            case 'U':
                yCords[0] = yCords[0] - UNIT_SIZE;
                break;
            case 'D':
                yCords[0] = yCords[0] + UNIT_SIZE;
                break;
            case 'L':
                xCords[0] = xCords[0] - UNIT_SIZE;
                break;
            case 'R':
                xCords[0] = xCords[0] + UNIT_SIZE;
                break;
        }
    }
    
    public void checkApple(){
        if((xCords[0] == appleX) && yCords[0] == appleY){
            snakeLength++;
            applesAte++;
            createApple();
        }
    }
    /*
    collisions method will be used to know when the snake hits an apple or boundry
    */
    public void checkCollisions(){
        for(int i = snakeLength; i>0; i--){
            if((xCords[0]==xCords[i]) && yCords[0] == yCords[i])    //Snake runs into itself
                isRunning=false;
        }
        //If snake hits screen boundaries
        if(xCords[0] < 0) //Left boundary
            isRunning = false;
        if(xCords[0] > GAME_WIDTH-UNIT_SIZE) //Right boundary
            isRunning = false;
        if(yCords[0]< 0) //Top boundary
            isRunning = false;
        if(yCords[0] > GAME_HEIGHT-UNIT_SIZE) //Bottom boundary
            isRunning = false;
        if(!isRunning)
            timer.stop();
        
    }
    public boolean isRunning(){
        return isRunning;
    }
    
    //Ends the game when player fails
    public void endGame(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over"))/2, GAME_HEIGHT/2);
        
        //Score
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesAte, (GAME_WIDTH - metrics2.stringWidth("Score: " + applesAte))/2, g.getFont().getSize()+(GAME_HEIGHT/4));  
            
        if(applesAte > GameFrame.getTopScore("snake")){
           String result;
            result = JOptionPane.showInputDialog(null, "Enter your name to associate with your high score");
            JOptionPane.showMessageDialog(null, result + ", Your high score of " + applesAte + " has been recorded");

                GameFrame.insertScore("snake", result, applesAte);
            
        }
            
        menuBtn.setBackground(Color.WHITE);
        super.add(menuBtn);
        super.revalidate();
    }
    
    public boolean gameOver(){
        return !isRunning;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isRunning){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    
    /*
    Nested Keyboard listener class detects when the directional keys are pressed and moves snake accordingly
    */
    private class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:  //Left arrow key
                    if(direction != 'R')    //Dont do a 180
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT: //Right arrow key
                    if(direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:    //Up arrow key
                    if(direction != 'D') 
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:  //Down arrow key
                    if(direction != 'U')
                        direction = 'D';
                    break;
                    
            }
        }
    }
    
}
