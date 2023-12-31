package GUI.EncDec;

import GUI.mainGUI;
import GUI.Utils;
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
                        Utils.errorPopup("Invalid parameters");
                    } else {
                        FLAG = 0;
                    }
                }
            } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                     NoSuchAlgorithmException | IOException | BadPaddingException | InvalidKeyException e) {
                Utils.errorPopup("Error decrypting the file");
                throw new RuntimeException(e);
            }
        } else if (res == JFileChooser.CANCEL_OPTION) {
            gui.presentGUI();
        }
        frame.setVisible(true);
    }

    /**
     * Popup to request key + IV from user
     */
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
        keyLabel.setFont(Utils.TAHOMA_PLAIN_12);
        keyPanel.add(keyLabel);
        JTextField keyField = newTextField(fieldSize);
        keyPanel.add(keyField);
        keyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(keyPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel ivPanel = new JPanel();
        ivPanel.setLayout(new BoxLayout(ivPanel, BoxLayout.X_AXIS));
        JLabel ivLabel = new JLabel("IV: ");
        ivLabel.setFont(Utils.TAHOMA_PLAIN_12);
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

    /**
     * create a new cancel button for the popup.
     * @param popup
     * @return cancel button
     */
    private JButton newCancelButton(JDialog popup) {
        JButton cancelBtn = new JButton("Cancel");
        Utils.noFocusBorder(cancelBtn);
        cancelBtn.setHorizontalAlignment(SwingConstants.CENTER);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        Utils.setRetButton(cancelBtn, popup);
        cancelBtn.addActionListener(e -> FLAG = 1);
        return cancelBtn;
    }

    /**
     * create the finish button to start decrypting
     * @param popup
     * @param keyField key
     * @param ivField iv
     * @return finish button
     */
    private JButton newFinishButton(JDialog popup, JTextField keyField, JTextField ivField) {
        JButton finishBtn = new JButton("Finish");
        Utils.noFocusBorder(finishBtn);
        Utils.setRetButton(finishBtn, popup);
        finishBtn.setHorizontalAlignment(SwingConstants.CENTER);
        finishBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishBtn.addActionListener(e -> data = Utils.getKeyIv(keyField.getText(), ivField.getText()));
        return finishBtn;
    }

    /**
     * create the popup for requesting key + IV
     * @return popup
     */
    private JDialog insertionPopup() {
        JDialog popup = new JDialog();
        popup.setTitle("SwissJnife - Key+IV data");
        popup.setSize(350, 250);
        popup.setLocation(Utils.centerPopup(popup));
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

    /**
     * create JTextField for key / IV
     * @param fieldSize size of the text field
     * @return text field
     */
    private JTextField newTextField(Dimension fieldSize) {
        JTextField field = new JTextField();
        field.setBackground(Color.WHITE);
        field.setMinimumSize(fieldSize);
        field.setMaximumSize(fieldSize);
        return field;
    }
}

