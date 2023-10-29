import javax.swing.*;
import java.awt.*;

public class encryptGUI {
    public encryptGUI() {
    }
    public void presentGui(JFrame frame, mainGUI gui) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        JButton backButton = new JButton("Return");
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBackground(new Color(248, 72, 72, 216));
        panel.add(backButton);
        backButton.addActionListener(e -> {
            sharedUtils.clearScreen(panel);
            gui.presentGUI();
        });
        frame.setVisible(true);
    }
}
