package GUI;

import javax.swing.*;
import java.awt.*;
import Logics.encrypt;
import org.jetbrains.annotations.NotNull;

public class encryptGUI {
    public encryptGUI() {
    }
    public void presentGui(@NotNull JFrame frame, mainGUI gui) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = fileChooser.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try {
                encrypt.encryptFile(selectedFile.getAbsolutePath());
                sharedUtils.clearScreen(panel);
                gui.presentGUI();
            } catch (Exception e) {
                e.fillInStackTrace();
                sharedUtils.clearScreen(panel);
                gui.presentGUI();
            }
        } else if (res == JFileChooser.CANCEL_OPTION) {
            sharedUtils.clearScreen(panel);
            gui.presentGUI();
        }
        //Key data
        JTextPane key = new JTextPane();
        key.setFont(key.getFont().deriveFont(16.0f));
        key.setEditable(false);
        panel.add(key);


        frame.add(panel);
        frame.setVisible(true);
    }
}
