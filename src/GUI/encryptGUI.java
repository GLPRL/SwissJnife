package GUI;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import Logics.encrypt;

public class encryptGUI {
    public encryptGUI() {
    }
    public void presentGui(@NotNull JFrame frame, mainGUI gui) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        JButton backButton = new JButton("Return");
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBackground(new Color(248, 72, 72, 216));
        panel.add(backButton);
        backButton.addActionListener(e -> {
            sharedUtils.clearScreen(panel);
            gui.presentGUI();
        });
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = fileChooser.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            encrypt.encryptFile(selectedFile.getAbsolutePath());
            //JOptionPane.showMessageDialog(frame, "Selected file: " + selectedFile.getAbsolutePath());
        } else if (res == JFileChooser.CANCEL_OPTION) {
            sharedUtils.clearScreen(panel);
            gui.presentGUI();
        }
        //panel.add(fileChooser);
        frame.setVisible(true);
    }
}
