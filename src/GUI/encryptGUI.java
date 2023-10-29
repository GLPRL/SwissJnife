package GUI;

import GuiUtils.sharedUtils;

import javax.swing.*;
import java.awt.*;

public class encryptGUI {
    public encryptGUI() {
    }
    public void presentGui(JFrame frame, mainGUI gui) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        JButton backButton = new JButton("Return");
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
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
