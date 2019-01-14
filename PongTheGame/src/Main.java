import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

    private JPanel jContentPane = null;
    private PBall panel = null;

    private PBall getPanel() {
        if (panel == null) {
            panel = new PBall();
        }
        return panel;
    }

    public Main() {

        build();
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                KeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                KeyReleased(evt);
            }
        });
    }

    private void KeyPressed(KeyEvent evt) {
        panel.keyPressed(evt);
    }

    private void KeyReleased(KeyEvent evt) {
        panel.keyReleased(evt);
    }

    private void build() {
        this.setResizable(false);
        this.setBounds(new Rectangle(800, 300, 500, 500));
        this.setMinimumSize(new Dimension(500, 500));
        this.setMaximumSize(new Dimension(500, 500));
        this.setContentPane(getJContentPane());
        this.setTitle("PongTheGame");
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);

    }
}
