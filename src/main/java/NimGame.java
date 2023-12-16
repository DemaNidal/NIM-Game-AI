
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author hp
 */
public class NimGame extends javax.swing.JFrame {
private JButton[] Buttons;
String delete = "ready";
Scanner scanner = new Scanner(System.in);
int currentPlayer;
JLabel show;
int[] balls;
 int numBalls;
 int difficulty ;
 ArrayList<JButton> allButtons;
 int ballIndex, numToRemove;
 int[] move;
private static boolean inputReady = false;
    /**
     * Creates new form NewJFrame
     */
    public NimGame() {
        this.allButtons = new ArrayList<>();
         initComponents();
        
        
    }
public void createPileButtons(int[] buttonsInEachRow, int diff,int sta) {
    numBalls=buttonsInEachRow.length;
    balls= new int[numBalls];
    difficulty = diff;
    currentPlayer =sta;
   
    JPanel jPanel1 = new JPanel(); // Assuming jPanel1 is your main panel
    jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical arrangement

    for (int row = 0; row < buttonsInEachRow.length; row++) {
        balls[row]=buttonsInEachRow[row];
          
        JPanel rowPanel = new JPanel();
        for (int i = 0; i < buttonsInEachRow[row]; i++) {
            JButton button = new JButton("Button" + (i + 1));
            allButtons.add( button);
            button.setToolTipText(Integer.toString(row));
            button.addActionListener(new ActionListener() {
               
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    if(delete.compareToIgnoreCase("ready")==0){
                        button.setVisible(false);
                        delete=button.getToolTipText();
                        ballIndex=Integer.valueOf(delete);
                        numToRemove++;
                        allButtons.remove(button);
                        for (JButton button : allButtons) {
                            System.out.println(button.getToolTipText());
                   
                        }
                        
                    }
                    else if(button.getToolTipText().compareToIgnoreCase(delete)==0){
                        button.setVisible(false);
                        numToRemove++;
                       allButtons.remove(button);
                        for (JButton button : allButtons) {
                            System.out.println(button.getToolTipText());
                   
                        }
                       
                    }
                    
                      
                }
            });
            rowPanel.add(button); // Add the button to the rowPanel
        }

        // Add the rowPanel to the main panel
        jPanel1.add(rowPanel);

        // Add an empty spacer to create a new line
        jPanel1.add(Box.createRigidArea(new Dimension(0,5)));
       // jPanel1.add(Box.createRigidArea(new Dimension(0, 10));
    }

    JButton startButton = new JButton("PC Turn");
    startButton.setBackground(Color.blue);
    startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {              
                      
                    delete="ready";
                    
                    start();
                }
            });
    jPanel1.add(startButton);
      show = new JLabel(" Lets Play !!");
       Font labelFont = new Font("Arial", Font.BOLD, 14); // You can adjust the font family, style, and size
            show.setFont(labelFont);

            // Set the text color for JLabel
            show.setForeground(Color.blue); 
    
    jPanel1.add(show);


    // Add jPanel1 to your main frame or container
    // For example, if you're using a JFrame:
    JFrame frame = new JFrame("NIM Game");
    frame.add(jPanel1);
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);
    
    System.out.println("****************************************************");
    System.out.println("Game MODE" + difficulty);
    System.out.println("Starting with"+currentPlayer);
    System.out.println("Welcome to the Nim Game!");
    
    if(currentPlayer==2){
        start();
    }
   
//start();
}

    void start(){
        
        
              printBalls(balls);


            if (currentPlayer == 1) {
                
                System.out.println("Player 1's turn:");
                printBalls(balls);
                
            } 
              
              else {
                   printBalls(balls);
                System.out.println("Player 2's turn (AI):");
                
                if (difficulty == 1) {
                    move = findRandomMove(balls);
                } else if (difficulty == 2) {
                    move = findMedMove(balls);
                } else {
                    move = findHardMove(balls);
                }
                ballIndex = move[0];
                numToRemove = move[1];
                int temp=0;
                for (JButton button : allButtons) {
                   
                    if (button.getToolTipText().compareToIgnoreCase(String.valueOf(ballIndex))==0 && temp<numToRemove) {
                        if(button.getToolTipText().compareToIgnoreCase("removed")!=0){
                        button.setVisible(false);
                          temp++;
                        button.setToolTipText("removed");
                        }
                     }
                   
                }
                System.out.println("AI removes " + numToRemove + " from balls " + ballIndex);
                       show.setText("AI removes " + numToRemove + " from balls " + ballIndex);
                                   printBalls(balls);
            }
                             if (ballIndex >= 0 && ballIndex < balls.length && numToRemove > 0 && numToRemove <= balls[ballIndex]) {
                balls[ballIndex] -= numToRemove;
                currentPlayer = 3 - currentPlayer; // Switch players (1 -> 2 and 2 -> 1)
                 printBalls(balls);
            } else {
                System.out.println("Invalid move. Please try again.");
            }

            if (isGameOver(balls)) {
                printBalls(balls);
                System.out.println("Player " + (3 - currentPlayer) + " wins!");
                int t =3-currentPlayer;
                if(t==1){
                     show.setText("Congratulation you win");
                }
                else{
                    show.setText("GAME OVER");
                }
               
                return;
            }
           
           numToRemove=0;
           if(currentPlayer==2){ start();}
          
    }
    // Print the current state of the balls
    static void printBalls(int[] balls) {
        System.out.print("ball: ");
        for (int i = 0; i < balls.length; i++) {
            System.out.print("ball " + i + ": " + balls[i] + " ");
        }
        System.out.println();
    }

   
    static boolean isGameOver(int[] balls) {
        for (int ball : balls) {
            if (ball > 0) {
                return false;
            }
        }
        return true;
    }
        
    static int[] findRandomMove(int[] buttonsArray) {
        // value for number of buttons need to remove and their index
        int buttonIndex ;
        int numToRemove ;
        

      buttonIndex = (int) (Math.random() * buttonsArray.length);
      numToRemove = (int) (Math.random() * buttonsArray[buttonIndex]) + 1;

        int[] move = {buttonIndex, numToRemove};
        return move;
    }

   static int[] findMedMove(int[] balls) {
        int[] bestMove = {0, 0};
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int i = 0; i < balls.length; i++) {//outer row
            for (int j = 1; j <= balls[i]; j++) {// each button in the row
                int[] temp = balls.clone();
                temp[i] -= j;
                int v = minimax(temp, false, alpha, beta, 0);

                if (v > alpha) {
                    alpha = v;
                    bestMove[0] = i;
                    bestMove[1] = j;
                }
            }
        }

        return bestMove;
    }

    static int minimax(int[] balls, boolean isMaximizingPlayer, int alpha, int beta, int depth) {
        if (isTerminalNode(balls) || depth == 2) {
            return hurestic(balls, depth);
        }

        if (isMaximizingPlayer) {
            int V = Integer.MIN_VALUE;
            for (int i = 0; i < balls.length; i++) {
                for (int j = 1; j <= balls[i]; j++) {
                    int[] temp = balls.clone();
                    temp[i] -= j;
                    int result = minimax(temp, false, alpha, beta, depth + 1);
                    V = Math.max(V, result);
                    alpha = Math.max(alpha, result);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return V;
        } else {
            int V = Integer.MAX_VALUE;
            for (int i = 0; i < balls.length; i++) {
                for (int j = 1; j <= balls[i]; j++) {
                    int[] tempPiles = balls.clone();
                    tempPiles[i] -= j;
                    int eval = minimax(tempPiles, true, alpha, beta, depth + 1);
                    V = Math.min(V, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return V;
        }
    }

    static int hurestic(int[] balls, int depth) {
        int h = 0;
        for (int ball : balls) {
            h += ball;
        }
        return h - depth * 10; 
    }
   static boolean isTerminalNode(int[] balls) {
    int sum = 0;
    for (int ball : balls) {
        sum += ball;
    }
    if(sum==0) return true;
    return false;
    }


   
    static int[] findHardMove(int[] balls) {
        int[] bestMove = {0, 0};
        int best = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int i = 0; i < balls.length; i++) {
            for (int j = 1; j <= balls[i]; j++) {
                int[] temp = balls.clone();
                temp[i] -= j;
                int v = minimaxAlphaBeta(temp, 0, false, alpha, beta);

                if (v > best) {
                    best = v;
                    bestMove[0] = i;
                    bestMove[1] = j;
                }

                alpha = Math.max(alpha, best);
            }

            if (alpha >= beta) {
                break; // Prune the branch
            }
        }

        return bestMove;
    }

    // Minimax algorithm with alpha-beta pruning
    static int minimaxAlphaBeta(int[] balls, int depth, boolean isMaximizing, int alpha, int beta) {
        if (isGameOver(balls)) {
            return isMaximizing ? -1 : 1;
        }

        int score = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < balls.length; i++) {
            for (int j = 1; j <= balls[i]; j++) {
                int[] temp = balls.clone();
                temp[i] -= j;
                int currentScore = minimaxAlphaBeta(temp, depth + 1, !isMaximizing, alpha, beta);

                if (isMaximizing) {
                    score = Math.max(score, currentScore);
                    alpha = Math.max(alpha, score);
                } else {
                    score = Math.min(score, currentScore);
                    beta = Math.min(beta, score);
                }

                if (alpha >= beta) {
                    break; // Prune the branch
                }
            }
        }

        return score;
    }
    // simple heuristic that counts the total number of items in all balls

  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NimGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NimGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NimGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NimGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
