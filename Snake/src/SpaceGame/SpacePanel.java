package SpaceGame;

import Game.GameFrame;
import Game.PanelAdjustments;
import SpaceObjects.Beam;
import SpaceObjects.Bullet;
import SpaceObjects.Enemy;
import SpaceObjects.Shield;
import SpaceObjects.Ship;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Josh Ness - NDSU
 */
public class SpacePanel extends JPanel {
    private Timer gameTimer;
    private SpaceController controller;

    private Ship s1;
    private ArrayList<Enemy> enemies = new ArrayList();
    private ArrayList<Shield> shields = new ArrayList();
    private ArrayList<Bullet> bullets = new ArrayList();
    private ArrayList<Beam> beams = new ArrayList();
    private ArrayList<Enemy> nextLevel = new ArrayList();
    private int beamIterator;
    private int bulletIterator; //Determines forced time inbetween shots
    private int userLives;
    private int levelCount;
    private int score;
    
    // Controls size of game window and framerate

    private final int gameWidth = PanelAdjustments.getWidth();
    private final int gameHeight = PanelAdjustments.getHeight();
    private final int framesPerSecond = 60;
    public JButton menuBtn = new JButton("Return to Menu");
    private boolean isRunning = false;

    //Sets up game upon run
    public final void setupGame() {
        controller.resetController();
        beamIterator = 0;
        bulletIterator = 25;
        userLives = 3;
        levelCount = 1;
        score = 0;


        s1 = new Ship(gameWidth / 2, gameHeight - 80, Color.GRAY, controller);
        enemies.add(new Enemy(400, 200, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(300, 200, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(200, 200, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(100, 200, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(400, 120, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(300, 120, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(200, 120, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(100, 120, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(400, 280, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(300, 280, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(200, 280, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(100, 280, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(400, 360, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(300, 360, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(200, 360, 2, 0, Color.GREEN, 2));
        enemies.add(new Enemy(100, 360, 2, 0, Color.GREEN, 2));

        shields.add(new Shield(100, 770, 80, 10, Color.BLUE));
        shields.add(new Shield(520, 770, 80, 10, Color.BLUE));
    }

    /**
     * This method is automatically called by the game timer every frame. 
     *
     * @param g The Graphics object used for drawing the GameObject instances.
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        draw(g);
    }
    
    public void draw(Graphics g){
         if (isRunning) {
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
            s1.draw(g);
            for (Shield shield : shields) {
                shield.draw(g);
            }
            for (Bullet bullet : bullets) {
                bullet.draw(g);
            }
            for (Beam beam : beams) {
                beam.draw(g);
            }
            g.drawString("Score: " + score, 15, 15);
            g.drawString("Lives: " + userLives, gameWidth - 80, 15);
        } else {
            endGame(g);
        }
    }

    /**
  
     *
     * @param frameNumber The number of the current frame.
     */
    public void updateGameState(int frameNumber) {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        s1.move();
        changeDirection();
        checkShipLocation();
        if (controller.getSpaceKeyStatus() == true) {
            if (levelCount == 1) {
                if (bulletIterator >= 25) {
                    bullets.add(new Bullet(s1.getXPosistion() - 4, s1.getYPosistion() - 15, 8, Color.BLUE));
                    bulletIterator = 0;
                }
            } else if (levelCount > 1) {
                if (bulletIterator >= 20) {
                    bullets.add(new Bullet(s1.getXPosistion() - 4, s1.getYPosistion() - 15, 8, Color.BLUE));
                    bulletIterator = 0;
                }
            }
        }
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        for (Beam beam : beams) {
            beam.move();
        }
        bulletHitsEnemy();
        shootBeams();
        beamHitsShip();
        shotHitsShip();

        //Determin if the game should still be running
        if (userLives < 1) {
            isRunning = false;
        }

        bulletIterator++;
//Creates a second level if the first one is passed
        if (enemies.isEmpty()) {
            levelCount++;
            if (levelCount >= 2) {
                enemies.add(new Enemy(400, 200, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(300, 200, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(200, 200, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(100, 200, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(400, 120, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(300, 120, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(200, 120, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(100, 120, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(400, 280, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(300, 280, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(200, 280, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(100, 280, 2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(400, 360, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(300, 360, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(200, 360, -2, 0, Color.GREEN, 2));
                enemies.add(new Enemy(100, 360, -2, 0, Color.GREEN, 2));

            }

        }
    }

    /**
     * Constructor method for GamePanel class. It is not necessary for you to
     * modify this code at all
     */
    public SpacePanel() {
        // Set the size of the Panel
        this.setSize(gameWidth, gameHeight);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));

        this.setBackground(Color.BLACK);

        // Register KeyboardController as KeyListener
        controller = new SpaceController();
        this.addKeyListener(controller);

        // Call setupGame to initialize fields
        this.setupGame();

        this.setFocusable(true);
        this.requestFocusInWindow();

    }

    //Method to change the direction of the aliens when one hits the edge
    public void changeDirection() {
        for (Enemy enemy : enemies) {
            if (enemy.getXPosistion() >= gameWidth - 54) {
                for (Enemy eachEnemy : enemies) {
                    eachEnemy.setXVelocity(-eachEnemy.getXVelocity());
                    eachEnemy.setYPosistion(eachEnemy.getYPosistion() + 30);
                }
                break;
            }
            if (enemy.getXPosistion() <= 10) {
                for (Enemy eachEnemy : enemies) {
                    eachEnemy.setXVelocity(-eachEnemy.getXVelocity());
                    eachEnemy.setYPosistion(eachEnemy.getYPosistion() + 30);
                }
                break;
            }
        }
    }

    //Method that allows enemies to shoot a beam
    public void shootBeams() {
        beamIterator++;
        if (enemies.size() > 0) {
            if (beamIterator == 30) {
                Random rand = new Random();
                int i = rand.nextInt(enemies.size());
                beams.add(new Beam(enemies.get(i).getXPosistion() + 25, enemies.get(i).getYPosistion() + 30, 0, 5, 3, 40, Color.RED));
                beamIterator = 0;
            }
        }
        ArrayList<Beam> removeBeams = new ArrayList();
        for (Beam beam : beams) {
            if (beam.getYPosistion() >= 1000) {

                removeBeams.add(beam);
            }
        }
        beams.removeAll(removeBeams);
    }

    //Method that prevents the ship from going out of bounds
    public void checkShipLocation() {
        if (s1.getXPosistion() >= gameWidth) {
            s1.setXPosistion(gameWidth);
        }
        if (s1.getXPosistion() <= 0) {
            s1.setXPosistion(0);
        }
    }

    //Method to detect if a bullet hits an alien, or if an alien makes it off of the screen
    public void bulletHitsEnemy() {
        ArrayList<Enemy> enemiesHit = new ArrayList();
        ArrayList<Bullet> bulletsHit = new ArrayList();
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    enemy.adjustHealth();
                    if (enemy.getHealth() == 1) {
                        enemy.setColor(Color.YELLOW);
                        score += 1;
                    } else if (enemy.getHealth() <= 0) {
                        enemiesHit.add(enemy);
                        score += 3;
                    }
                    bulletsHit.add(bullet);
                    if (bullet.getYPosistion() < -400) {
                        bulletsHit.add(bullet);
                    }
                }
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.getYPosistion() > gameHeight) {
                enemiesHit.add(enemy);
                userLives--;
            }
        }
        enemies.removeAll(enemiesHit);
        bullets.removeAll(bulletsHit);
    }

    //Method to detect if a beam hits the ship
    public void beamHitsShip() {
        for (Beam beam : beams) {
            if (beam.isColliding(s1)) {
                beams.remove(beam);
                userLives--;
                break;
            }
        }
    }

    //Method that determines if a bullet or beam hits a shield
    public void shotHitsShip() {
        ArrayList<Bullet> bulletRemovals = new ArrayList();
        ArrayList<Beam> beamRemovals = new ArrayList();
        for (Bullet bullet : bullets) {
            for (Shield shield : shields) {
                if (bullet.isColliding(shield)) {
                    bulletRemovals.add(bullet);
                }
            }
        }
        for (Beam beam : beams) {
            for (Shield shield : shields) {
                if (beam.isColliding(shield)) {
                    beamRemovals.add(beam);
                }
            }
        }
        bullets.removeAll(bulletRemovals);
        beams.removeAll(beamRemovals);
    }

    /**
     * Method to start the Timer that drives the animation for the game. It is
     * not necessary for you to modify this code unless you need to in order to
     * add some functionality.
     */
    public void start() {
        // Set up a new Timer to repeat based on the set framesPerSecond
        gameTimer = new Timer(1000 / framesPerSecond, new ActionListener() {

            // Tracks the number of frames that have been produced.
            // May be useful for limiting action rates
            private int frameNumber = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the game's state and repaint the screen
                updateGameState(frameNumber++);
                repaint();
            }
        });

        gameTimer.setRepeats(true);
        gameTimer.start();
        isRunning = true;
    }
    //Ends the game when player fails

    public void endGame(Graphics g) {

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (gameWidth - metrics.stringWidth("Game Over")) / 2, gameHeight / 2);

        //Score
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (gameWidth - metrics2.stringWidth("Score: " + score)) / 2, g.getFont().getSize() + (gameHeight / 4));
        
        if(score > GameFrame.getTopScore("invaders")){
            String result;
            result = JOptionPane.showInputDialog(null, "Enter your name to assocaite with your high score");
            JOptionPane.showMessageDialog(null, result + ", Your high score of " + score + " has been recorded");
            GameFrame.insertScore("invaders", result, score);
        }

        menuBtn.setBackground(Color.WHITE);
        super.add(menuBtn);
        super.revalidate();
    }
}
