import javax.swing.*;
import java.awt.*;

public class mainGUI {
    public mainGUI() {
    }
    private void clearScreen(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    private void createButton(JPanel panel, String desc) {
        JButton btn = new JButton(desc);
        panel.add(btn);
        btn.addActionListener(e -> clearScreen(panel));
    }
    public void presentGUI() {
        JFrame frame = new JFrame("SwissJnife");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        // Create title
        JLabel title = new JLabel("SwissJnife");
        title.setFont(title.getFont().deriveFont(20.0f));
        panel.add(title);

        createButton(panel, "Encrypt File");
        createButton(panel, "decrypt File");

        // Show
        frame.setVisible(true);
    }
}