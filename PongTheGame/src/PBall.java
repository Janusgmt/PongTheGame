import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class PBall extends JPanel implements Runnable {
    /*
    Steuerung:
    Spieler 1: W ; S
    Spieler 2: Pfeil Hoch ; Pfeil Runter
    Einstellungen:
    Farbe:
    Ball            Z.33
    Spieler1        Z.34
    Spieler2        Z.35
    Siegespunktzahl Z.157
    */
    private Thread thread;
    private int ballPx = 20, ballPy = 20;
    private int player1X = 20, player1Y = 200, player2X = 460, player2Y = 200;
    private int rechts = 10, links = -10;
    private int rechtsB = 10, linksB = -10;
    private int oben = 10, unten = -10;
    private int obenB = 10, untenB = -10;
    private int width, height;
    private int countP1 = -1, countP2 = -1;
    private boolean p1moveUp, p1moveDown, p2moveUp, p2moveDown;
    private boolean spielStart, ende;
    private String lastbutton;
    private int timer;
    private Color ballc = Color.black; //Ball Farbe
    private Color p1c = Color.red; //Spieler 1 Farbe
    private Color p2c = Color.blue; //Spieler 2 Farbe

    public PBall() {
        spielStart = true;
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics gc) {

        setOpaque(false);
        super.paintComponent(gc);
        gc.setColor(ballc);
        gc.fillOval(ballPx, ballPy, 16, 16);

        gc.setColor(p1c);
        gc.fillRect(player1X, player1Y, 10, 50);
        gc.drawString("Player 1:  " + countP1, 50, 20);

        gc.setColor(p2c);
        gc.fillRect(player2X, player2Y, 10, 50);
        gc.drawString("Player 2:  " + countP2, width - 110, 20);

        if (ende) {
            if (countP1 >= countP2) {
                gc.drawString("Player1 won the Game", 180, 200);
            } else if (countP2 >= countP1) {
                gc.drawString("Player2 won the Game", 180, 200);
            }
        }
    }

    public void drawBall(int nx, int ny) {
        ballPx = nx;
        ballPy = ny;
        this.width = this.getWidth();
        this.height = this.getHeight();
        repaint();
    }

    public void drawP1(int x, int y) {
        this.player1X = x;
        this.player1Y = y;
        repaint();
    }

    public void drawP2(int x, int y) {
        this.player2X = x;
        this.player2Y = y;
        repaint();
    }

    public void moveP1() {
        if (p1moveUp == true && player1Y >= 0) {
            player1Y += unten;
        }
        if (p1moveDown == true && player1Y <= (this.getHeight() - 50)) {
            player1Y += oben;
        }
        drawP1(player1X, player1Y);
    }

    public void moveP2() {
        if (p2moveUp == true && player2Y >= 0) {
            player2Y += unten;
        }
        if (p2moveDown == true && player2Y <= (this.getHeight() - 50)) {
            player2Y += oben;
        }
        drawP2(player2X, player2Y);
    }

    public void run() {

        boolean richtungX = false;
        boolean richtungY = false;

        while (true) {
            if (spielStart) {
                if (richtungX) {
                    ballPx += rechtsB;
                    if (ballPx >= (width - 16)) {
                        richtungX = false;
                    }
                } else {
                    ballPx += linksB;
                    if (ballPx <= 0) {
                        richtungX = true;
                    }
                }

                if (richtungY) {
                    ballPy += obenB;
                    if (ballPy >= (height - 16)) {
                        richtungY = false;
                    }
                } else {
                    ballPy += untenB;
                    if (ballPy <= 0) {
                        richtungY = true;
                    }
                }

                drawBall(ballPx, ballPy);

                try {
                    Thread.sleep(30 - timer); 
                } catch (InterruptedException ex) {
                }

                moveP1();
                moveP2();

                if (ballPx >= (width - 16)) {
                    timer = 0;
                    countP1++;
                }
                if (ballPx == 0) {
                    timer = 0;
                    countP2++;
                }
                
                if (countP1 == 10 || countP2 == 10) { //Anzahl Tore um zu gewinnen
                    spielStart = false;
                    ende = true;
                }
                
                if (ballPx == player1X + 10 && ballPy >= player1Y && ballPy <= (player1Y + 60)) {

                    if (lastbutton == "o" && p1moveDown) {
                        richtungY = true;
                    } else if (lastbutton == "u" && p1moveUp) {
                        richtungY = false;
                    }
                    try {
                        thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PBall.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    richtungX = true;
                    timer++;
                    /*
                     rechtsB += 1;
                     linksB += 1;
                     obenB += 1;
                     untenB -= 1;
                     */
                }

                if (ballPx == (player2X - 10) && ballPy >= player2Y && ballPy <= (player2Y + 60)) {

                    if (lastbutton == "o" && p2moveDown) {
                        richtungY = true;
                    } else if (lastbutton == "u" && p2moveUp) {
                        richtungY = false;
                    }
                    try {
                        thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PBall.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    richtungX = false;
                    timer++;
                    /*
                     rechtsB += 1;
                     linksB -= 1;
                     obenB += 1;
                     untenB -= 1;
                     */
                }
            }
        }
    }

    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_W:
                lastbutton = "u";
                p1moveUp = true;
                break;
            case KeyEvent.VK_S:
                lastbutton = "o";
                p1moveDown = true;
                break;
            case KeyEvent.VK_UP:
                lastbutton = "u";
                p2moveUp = true;
                break;
            case KeyEvent.VK_DOWN:
                lastbutton = "o";
                p2moveDown = true;
                break;
        }
    }

    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_W:
                p1moveUp = false;
                break;
            case KeyEvent.VK_S:
                p1moveDown = false;
                break;
            case KeyEvent.VK_UP:
                p2moveUp = false;
                break;
            case KeyEvent.VK_DOWN:
                p2moveDown = false;
                break;
        }
    }

}
