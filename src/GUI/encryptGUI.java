package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import Logics.Global;
import Logics.encrypt;
import org.jetbrains.annotations.NotNull;

public class encryptGUI {
    JPanel panel;
    JPanel buttonsPanel;
    JDialog popup;
    Color normal = new Color(150, 245, 222);
    Color onHover = new Color(118, 192, 173);
    public encryptGUI() {
        panel = new JPanel();
        buttonsPanel = new JPanel();
        popup = new JDialog();
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

    /**
     * Create the popup window
     * @param frame programs' frame
     */
    public void dataPopup(JFrame frame) {
        panel.setLayout(new BorderLayout());
        popup.setTitle("SwissJnife - Key+IV data");
        popup.setSize(460, 130);
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        createElements();
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(buttonsPanel, BorderLayout.NORTH);
        popup.add(panel);
        popup.setVisible(true);
    }

    /**
     * Creating elements of the popup
     */
    public void createElements() {
        String key = Arrays.toString(Global.getInstance().aesData.getKey().getEncoded());
        String iv = Arrays.toString(Global.getInstance().aesData.getIv().getIV());

        Dimension d = new Dimension(5, 0);

        buttonsPanel.add(Box.createRigidArea(d));
        JLabel infoLabel = new JLabel("");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setFont(infoLabel.getFont().deriveFont(17.0f));

        JButton ivBtn = new JButton("Copy IV to Clipboard");
        ivBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ivBtn.setAlignmentY(Component.TOP_ALIGNMENT);
        ivBtn.setBackground(new Color(150, 245, 222));
        ivBtn.addActionListener(e -> {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(iv);
            cb.setContents(selection, null);
            infoLabel.setText("IV copied to clipboard");
        });
        ivBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ivBtn.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ivBtn.setBackground(normal);
            }
        });

        buttonsPanel.add(ivBtn);
        buttonsPanel.add(Box.createRigidArea(d));
        panel.add(infoLabel, BorderLayout.CENTER);

        JButton keyBtn = new JButton("Copy Key to Clipboard");
        keyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        keyBtn.setAlignmentY(Component.TOP_ALIGNMENT);
        keyBtn.setBackground(new Color(150, 245, 222));
        keyBtn.addActionListener(e -> {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(key);
            cb.setContents(selection, null);
            infoLabel.setText("Key copied to clipboard");
        });
        keyBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                keyBtn.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                keyBtn.setBackground(normal);
            }
        });
        buttonsPanel.add(keyBtn);

        JButton closeBtn = new JButton("Return to Menu");
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> popup.dispose());
        closeBtn.setAlignmentY(Component.TOP_ALIGNMENT);
        buttonsPanel.add(Box.createRigidArea(d));
        buttonsPanel.add(closeBtn);
        buttonsPanel.add(Box.createRigidArea(d));
    }
}
