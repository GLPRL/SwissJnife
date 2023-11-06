package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Logics.encrypt;
import org.jetbrains.annotations.NotNull;

public class encryptGUI {
    public encryptGUI() {
    }

    public void presentGui(@NotNull JFrame frame, mainGUI gui) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("Select");

        int res = fileChooser.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try {
                encrypt.encryptFile(selectedFile.getAbsolutePath());
                dataPopup(frame);
                gui.presentGUI();
            } catch (Exception e) {
                e.fillInStackTrace();
                gui.presentGUI();
            }
        } else if (res == JFileChooser.CANCEL_OPTION) {
            gui.presentGUI();
        }
        frame.setVisible(true);
    }

    public void dataPopup(JFrame frame) {
        JDialog popup = new JDialog();
        popup.setTitle("SwissJnife - key data");
        popup.setSize(500, 110);
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JLabel inst = new JLabel("Click the key data to copy to clipboard");
        inst.setFont(inst.getFont().deriveFont(16.0f));
        inst.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel key = new JLabel("YEET");
        Border keyBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        key.setBorder(keyBorder);
        key.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String copiedKey = key.getText();
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(copiedKey);
                cb.setContents(selection, null);
            }
        });
        key.setFont(key.getFont().deriveFont(16.0f));
        key.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton closeBtn = new JButton("Return to Menu");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> popup.dispose());

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.PAGE_AXIS));
        popupPanel.add(inst);
        popupPanel.add(key);

        popupPanel.add(closeBtn);
        popup.add(popupPanel);
        popup.setVisible(true);
    }
}
