package Main;
import Component.GamePanel;

import Component.GamePanel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main extends JFrame{
    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }

    public Main() {
        init();
    }
    private void init() {
        setTitle("Rocket Shooter");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                gamePanel.start();;
            }
        });
    }
}
