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
        frame.add(panel);
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

            //JOptionPane.showMessageDialog(frame, "Selected file: " + selectedFile.getAbsolutePath());
        } else if (res == JFileChooser.CANCEL_OPTION) {
            sharedUtils.clearScreen(panel);
            gui.presentGUI();
        }
        //panel.add(fileChooser);
        frame.setVisible(true);
    }
}
