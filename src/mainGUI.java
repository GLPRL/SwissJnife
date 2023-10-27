import javax.swing.*;
import java.awt.*;

public class mainGUI {
    public mainGUI() {
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

        JButton encryptButton = new JButton("Encrypt File");
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt File");
        panel.add(decryptButton);

        // Set frame visibility at the end
        frame.setVisible(true);
    }
}