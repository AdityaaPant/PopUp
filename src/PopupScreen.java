import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PopupScreen {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PongGame().startGame());
    }
}

class PongGame {
    private JFrame frame;
    private JPanel gamePanel;
    private Timer gameTimer;

    private int paddleX = 200, paddleY = 350, paddleWidth = 100, paddleHeight = 10;
    private int ballX = 250, ballY = 200, ballDiameter = 20;
    private int ballDX = 3, ballDY = 3;
    private int score = 0;

    public void startGame() {
        frame = new JFrame("Pong Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw background
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Draw paddle
                g.setColor(Color.WHITE);
                g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);

                // Draw ball
                g.setColor(Color.RED);
                g.fillOval(ballX, ballY, ballDiameter, ballDiameter);

                // Draw score
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Score: " + score, 10, 20);
            }
        };

        gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                paddleX = e.getX() - paddleWidth / 2;
                gamePanel.repaint();
            }
        });

        frame.add(gamePanel);

        gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();

        frame.setVisible(true);
    }

    private void updateGame() {
        // Move ball
        ballX += ballDX;
        ballY += ballDY;

        // Check for wall collisions
        if (ballX <= 0 || ballX + ballDiameter >= gamePanel.getWidth()) {
            ballDX *= -1;
        }
        if (ballY <= 0) {
            ballDY *= -1;
        }

        // Check for paddle collision
        if (ballY + ballDiameter >= paddleY && ballX + ballDiameter >= paddleX && ballX <= paddleX + paddleWidth) {
            ballDY *= -1;
            score++;
        }

        // Check for game over
        if (ballY + ballDiameter >= gamePanel.getHeight()) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(frame, "Game Over! Your final score is: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        // Repaint game panel
        gamePanel.repaint();
    }
}