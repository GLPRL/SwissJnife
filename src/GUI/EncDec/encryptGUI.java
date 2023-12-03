package GUI.EncDec;

import GUI.mainGUI;
import GUI.sharedUtils;
import Logics.EncDec.AESData;
import Logics.EncDec.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

/**
 * GUI for encrypting files.
 */
public class encryptGUI {
    JPanel panel;
    JPanel buttonsPanel;
    JDialog popup;
    JFrame frame;
    /**
     * Constructor
     */
    public encryptGUI(JFrame frame) {
        panel = new JPanel();
        buttonsPanel = new JPanel();
        popup = new JDialog();
        this.frame = frame;
    }

    /**
     * Show the GUI for the user - file selection popup
     * @param gui main GUI to return when finished
     */
    public void presentGui(mainGUI gui) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("Select");

        int res = fileChooser.showOpenDialog(this.frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try {
                AESData data = encrypt.encryptFile(selectedFile.getAbsolutePath(), frame);
                dataPopup(frame, data);
                Arrays.fill(data.getKey().getEncoded(), (byte) 0);
                Arrays.fill(data.getIv().getIV(), (byte) 0);
                gui.presentGUI();
            } catch (Exception e) {
                sharedUtils.errorPopup("Error: failed encrypting the file", frame);
                e.fillInStackTrace();
                gui.presentGUI();
            }
        } else if (res == JFileChooser.CANCEL_OPTION) {
            gui.presentGUI();
        }
        frame.setVisible(true);
    }

    /**
     * Create the popup window
     * @param frame programs' frame
     */
    public void dataPopup(JFrame frame, AESData data) {
        panel.setLayout(new BorderLayout());
        popup.setTitle("SwissJnife - Key+IV data");
        popup.setSize( 500, 130);
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        createElements(data);
        buttonsPanel.add(sharedUtils.W0_H10);
        panel.add(buttonsPanel, BorderLayout.NORTH);
        popup.add(panel);
        popup.setVisible(true);
    }

    /**
     * Creating elements of the popup for copying the Key and IV to clipboard.
     */
    public void createElements(AESData data) {
        String key = Arrays.toString(data.getKey().getEncoded());
        String iv = Arrays.toString(data.getIv().getIV());
        Dimension d = new Dimension(5, 0);

        JLabel infoLabel = new JLabel("");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setFont(infoLabel.getFont().deriveFont(17.0f));

        JButton ivBtn = createCBButton("Copy IV to Clipboard", iv, infoLabel, "IV");

        JButton keyBtn = createCBButton("Copy Key to Clipboard", key, infoLabel, "Key");

        JButton closeBtn = new JButton("Return to Menu");
        sharedUtils.noFocusBorder(closeBtn);
        sharedUtils.setRetButton(closeBtn, popup);
        closeBtn.setAlignmentY(Component.TOP_ALIGNMENT);

        buttonsPanel.add(Box.createRigidArea(d));
        buttonsPanel.add(ivBtn);
        buttonsPanel.add(Box.createRigidArea(d));
        panel.add(infoLabel, BorderLayout.CENTER);
        buttonsPanel.add(keyBtn);
        buttonsPanel.add(Box.createRigidArea(d));
        buttonsPanel.add(closeBtn);
        buttonsPanel.add(Box.createRigidArea(d));
    }

    /**
     * Initializes clipboard when clicking to copy a given string.
     * @param btn button clicked to copy to clipboard
     * @param data data to copy to clipboard
     * @param infoLabel announcing that the data was copied
     * @param desc desc of the data that was copied
     */
    public void initClipboard(JButton btn, String data, JLabel infoLabel, String desc) {
        btn.addActionListener(e -> {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(data);
            cb.setContents(selection, null);
            infoLabel.setText(desc + " copied to clipboard");
        });
    }
    public JButton createCBButton(String action, String key, JLabel infoLabel, String descKey) {
        JButton btn = new JButton(action);
        sharedUtils.noFocusBorder(btn);
        sharedUtils.setGeneralButton(btn);
        btn.setAlignmentY(Component.TOP_ALIGNMENT);
        initClipboard(btn, key, infoLabel, descKey);
        return btn;
    }
}
