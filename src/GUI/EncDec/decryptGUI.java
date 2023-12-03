package GUI.EncDec;

import GUI.mainGUI;
import GUI.sharedUtils;
import Logics.EncDec.AESData;
import Logics.EncDec.decrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class decryptGUI {
    JFrame frame;
    AESData data = null;
    private int FLAG = 0;
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
                    if (FLAG == 0) {
                        sharedUtils.errorPopup("Invalid parameters", frame);
                    } else {
                        FLAG = 0;
                    }
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
        Dimension d = new Dimension(0, 10);
        Dimension fieldSize = new Dimension(150, 25);

        JDialog popup = insertionPopup();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        popup.add(mainPanel);
        mainPanel.add(Box.createRigidArea(d));

        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.X_AXIS));
        JLabel keyLabel = new JLabel("Key: ");
        keyLabel.setFont(sharedUtils.TAHOMA_PLAIN_12);
        keyPanel.add(keyLabel);
        JTextField keyField = newTextField(fieldSize);
        keyPanel.add(keyField);
        keyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(keyPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel ivPanel = new JPanel();
        ivPanel.setLayout(new BoxLayout(ivPanel, BoxLayout.X_AXIS));
        JLabel ivLabel = new JLabel("IV: ");
        ivLabel.setFont(sharedUtils.TAHOMA_PLAIN_12);
        ivPanel.add(ivLabel);
        JTextField ivField = newTextField(fieldSize);
        ivPanel.add(ivField);
        ivPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(ivPanel);
        mainPanel.add(Box.createRigidArea(d));

        mainPanel.add(newFinishButton(popup, keyField, ivField));
        mainPanel.add(Box.createRigidArea(d));

        mainPanel.add(newCancelButton(popup));
        mainPanel.add(Box.createRigidArea(d));
        popup.setVisible(true);
    }
    private JButton newCancelButton(JDialog popup) {
        JButton cancelBtn = new JButton("Cancel");
        sharedUtils.noFocusBorder(cancelBtn);
        cancelBtn.setHorizontalAlignment(SwingConstants.CENTER);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sharedUtils.setRetButton(cancelBtn, popup);
        cancelBtn.addActionListener(e -> FLAG = 1);
        return cancelBtn;
    }
    private JButton newFinishButton(JDialog popup, JTextField keyField, JTextField ivField) {
        JButton finishBtn = new JButton("Finish");
        sharedUtils.noFocusBorder(finishBtn);
        sharedUtils.setRetButton(finishBtn, popup);
        finishBtn.setHorizontalAlignment(SwingConstants.CENTER);
        finishBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishBtn.addActionListener(e -> data = sharedUtils.getKeyIv(keyField.getText(), ivField.getText()));
        return finishBtn;
    }
    private JDialog insertionPopup() {
        JDialog popup = new JDialog();
        popup.setTitle("SwissJnife - Key+IV data");
        popup.setSize(350, 250);
        popup.setLocation(sharedUtils.centerFrame(frame));
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                FLAG = 1;
            }
        });
        popup.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        return popup;
    }
    private JTextField newTextField(Dimension fieldSize) {
        JTextField field = new JTextField();
        field.setBackground(Color.WHITE);
        field.setMinimumSize(fieldSize);
        field.setMaximumSize(fieldSize);
        return field;
    }
}

