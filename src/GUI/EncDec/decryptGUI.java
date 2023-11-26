package GUI.EncDec;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

import GUI.mainGUI;
import GUI.sharedUtils;
import Logics.EncDec.AESData;
import Logics.EncDec.decrypt;

import java.awt.*;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class decryptGUI {
    JFrame frame;
    AESData data = null;
    public decryptGUI(JFrame frame) {
        this.frame = frame;
    }

    public void presentGui(mainGUI gui) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("Select");

        int res = fileChooser.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try {
                requestData();
                gui.presentGUI();
                if (data != null) {
                    decrypt.decryptFile(selectedFile.getAbsolutePath(), data);

                } else {
                    sharedUtils.errorPopup("Invalid parameters", frame);
                }
            } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                     NoSuchAlgorithmException | IOException | BadPaddingException | InvalidKeyException e) {
                sharedUtils.errorPopup("Error decrypting the file", frame);
                throw new RuntimeException(e);
            }
        } else if (res == JFileChooser.CANCEL_OPTION) {
            gui.presentGUI();
        }
        frame.setVisible(true);
    }
    public void requestData() {
        JDialog popup = new JDialog();
        popup.setTitle("SwissJnife - Key+IV data");
        popup.setSize(350, 250);
        popup.setLocation(sharedUtils.centerFrame(frame));
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        popup.add(mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.X_AXIS));
        JLabel keyLabel = new JLabel("Key: ");
        keyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        keyPanel.add(keyLabel);
        JTextField keyField = new JTextField();
        keyField.setBackground(Color.WHITE);
        keyField.setMinimumSize(new Dimension(150, 25));
        keyField.setMaximumSize(new Dimension(150, 25));
        keyPanel.add(keyField);
        keyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(keyPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel ivPanel = new JPanel();
        ivPanel.setLayout(new BoxLayout(ivPanel, BoxLayout.X_AXIS));
        JLabel ivLabel = new JLabel("IV: ");
        ivLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        ivPanel.add(ivLabel);
        JTextField ivField = new JTextField();
        ivField.setBackground(Color.WHITE);
        ivField.setMinimumSize(new Dimension(150, 25));
        ivField.setMaximumSize(new Dimension(150, 25));
        ivPanel.add(ivField);
        ivPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(ivPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton finishBtn = new JButton("Finish");
        sharedUtils.noFocusBorder(finishBtn);
        sharedUtils.setRetButton(finishBtn, popup);
        finishBtn.setHorizontalAlignment(SwingConstants.CENTER);
        finishBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishBtn.addActionListener(e -> data = sharedUtils.getKeyIv(keyField.getText(), ivField.getText()));
        mainPanel.add(finishBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton cancelBtn = new JButton("Cancel");
        sharedUtils.noFocusBorder(cancelBtn);
        cancelBtn.setHorizontalAlignment(SwingConstants.CENTER);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sharedUtils.setRetButton(cancelBtn, popup);
        mainPanel.add(cancelBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        popup.setVisible(true);
    }
}

