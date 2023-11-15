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
                String keyIv = encrypt.encryptFile(selectedFile.getAbsolutePath());
                dataPopup(frame, keyIv);
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

    public void dataPopup(JFrame frame, String keyIv) {

        int div = keyIv.indexOf("_");
        String key = keyIv.substring(0, div);
        String iv = keyIv.substring(div + 1);

        JDialog popup = new JDialog();
        popup.setTitle("SwissJnife - Key+IV data");
        popup.setSize(990, 110);
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JLabel inst = new JLabel("Click the key and the initialization vector to copy to clipboard,\n" +
                "make sure to keep the text in a safe location");
        inst.setFont(inst.getFont().deriveFont(16.0f));
        inst.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel keyLabel = new JLabel(key);
        JLabel ivLabel = new JLabel(iv);
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        setText(keyLabel, border);
        setText(ivLabel, border);
        setOnLabelClick(keyLabel);
        setOnLabelClick(ivLabel);

        JButton closeBtn = new JButton("Return to Menu");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> popup.dispose());

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.PAGE_AXIS));
        popupPanel.add(inst);
        popupPanel.add(keyLabel);
        popupPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        popupPanel.add(ivLabel);

        popupPanel.add(closeBtn);
        popup.add(popupPanel);
        popup.setVisible(true);
    }
    public void setText(JLabel label, Border b) {
        label.setBorder(b);
        label.setFont(label.getFont().deriveFont(16.0f));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public void setOnLabelClick(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String copiedKey = label.getText();
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(copiedKey);
                cb.setContents(selection, null);
            }
        });
    }
}
