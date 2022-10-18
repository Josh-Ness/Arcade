package Game;

import Snake.SnakePanel;
import SpaceGame.SpacePanel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;


/**
 *
 * @author Josh Ness -NDSU
 */
public class GameFrame extends JFrame {
    
    

    /*CardLayout*/
    JPanel center = new JPanel();

    CardLayout cL = new CardLayout();
    JButton snakeBtn = new JButton("Play Snake");
    JButton spaceBtn = new JButton("Play Space Invaders");
    JButton snakeScoresBtn = new JButton("View top Snake scores");
    JButton spaceScoresBtn = new JButton("View top Space Invaders Scores");
    static JTextArea textArea = new JTextArea();
    JLabel testLabel = new JLabel();
    MenuPanel menuPanel = new MenuPanel();
    Timer timer;
    
/*DATABASE */
    
            private static String jdbcURL;
    private static String username;
    private static String password;
    private static Connection connection;

        private static void connectDB() {
        try {
            File f = new File("C:/Users/jsn20/Desktop/ArcadeDBcon.txt");
            Scanner scan = new Scanner(f);
            jdbcURL = scan.nextLine();
            username = scan.nextLine();
            password = scan.nextLine();

            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Problem with SQL " + e.getMessage());
        }

    }
        
        /*Method used to get the lowest score on the high score list
        *Used to determine if a score is a high score
        */
        
      public static int getTopScore(String game) {
        int res = -1;
        try {
            String scoreCutoff = "SELECT MIN(score) FROM " + game;
            PreparedStatement pstmt = connection.prepareStatement(scoreCutoff);
            ResultSet rs = pstmt.executeQuery(); 
            while(rs.next()){
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("There was an error");
        }
        textArea.setText("Test");
        return res;
    }
      
      /* Methd to insert a high score */
      public static void insertScore(String game, String username, int score){
          try{
              String countRecords = "SELECT COUNT(*) as count_records FROM " + game;
              String deleteLast = "DELETE FROM " + game + " WHERE score = (SELECT MIN(score) FROM " + game + ")";
              String insertScore = "INSERT INTO " + game + " (username, score) VALUES (?,?)";
              PreparedStatement countStmt = connection.prepareStatement(countRecords);
              PreparedStatement delStmt = connection.prepareStatement(deleteLast);
              PreparedStatement insStmt = connection.prepareStatement(insertScore);
              insStmt.setString(1, username);
              insStmt.setInt(2, score);
              ResultSet rs = countStmt.executeQuery();
              int count=-1;
              while(rs.next()){
                  count = rs.getInt(1);
              }
              if(count>9)
                delStmt.executeUpdate();
              insStmt.executeUpdate();
          }catch (SQLException e){
              System.out.println("There was an error");
          }
      }

        /*Method to view high scores of a game */
        public static String viewScores(String game){  
    String res = "";
try{

    String showScores = "SELECT username, score FROM " + game + " ORDER BY score DESC";
    PreparedStatement pstmt = connection.prepareStatement(showScores);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()){
        res = res + "Name:  "+ rs.getString(1) + " |  Score - " + rs.getInt(2) + "\n";
    }    
}
catch (SQLException e){
            res = "There was an error.";
}
return res;
    }
    
        /*END DATABASE */
        
    GameFrame() {
        menuPanel.setSize(PanelAdjustments.getWidth(), PanelAdjustments.getHeight());
        center.setLayout(cL);
        menuPanel.add(snakeBtn);
        menuPanel.add(spaceBtn);
        menuPanel.add(snakeScoresBtn);
        menuPanel.add(spaceScoresBtn);
        menuPanel.add(textArea);
        center.add(menuPanel, "Menu");
        this.add(center);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();        //Frame will adjust to fit with all components added
        this.setVisible(true);
        this.setLocationRelativeTo(null); //Makes the frame lie in the middle of the screen

        connectDB();
       // getTopScore("snake");
                snakeScoresBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               JOptionPane.showMessageDialog(null, viewScores("snake"));
            }
        });
        
                spaceScoresBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               JOptionPane.showMessageDialog(null, viewScores("invaders"));
                
            }
        });
                
                
        
        snakeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SnakePanel snakePanel = new SnakePanel();
                center.add(snakePanel, "Snake");
                cL.show(center, "Snake");
                snakePanel.requestFocusInWindow();  //ABSOLUTELY NECESSARY METHOD, MyKeyAdapter not in focus and wont work without
                snakePanel.startGame();
                
                snakePanel.menuBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        center.remove(snakePanel);
                        cL.show(center, "Menu");
                    }
                });
            }
        });

        spaceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SpacePanel spacePanel = new SpacePanel();
                center.add(spacePanel, "Space");
                cL.show(center, "Space");

                spacePanel.requestFocusInWindow();  //ABSOLUTELY NECESSARY METHOD, MyKeyAdapter not in focus and wont work without
                spacePanel.start();
                spacePanel.menuBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {

                        center.remove(spacePanel);
                        cL.show(center, "Menu");
                    }
                });
            }
        });
        
  

    }

}
