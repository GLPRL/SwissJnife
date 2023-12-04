package GUI;

import Logics.EncDec.AESData;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;


public class Utils {
    public static Color normal = new Color(150, 245, 222);
    public static Color onHover = new Color(118, 192, 173);
    public static Color ret = new Color(255, 48, 62, 255);
    public static Font TAHOMA_PLAIN_13= new Font("Tahoma", Font.PLAIN, 13);
    public static Font TAHOMA_BOLD_13= new Font("Tahoma", Font.BOLD, 13);
    public static Font TAHOMA_BOLD_12 = new Font("Tahoma", Font.BOLD, 12);
    public static Font TAHOMA_PLAIN_12 = new Font("Tahoma", Font.PLAIN, 12);
    public static Font MONO_PLAIN_12 = new Font("Monospaced", Font.PLAIN, 12);
    public static Font MONO_PLAIN_11 = new Font("Monospaced", Font.PLAIN, 11);
    public static Font TAHOMA_BOLD_11 = new Font("Tahoma", Font.BOLD, 11);
    public static Font TAHOMA_PLAIN_11 = new Font("Tahoma", Font.PLAIN, 11);
    public static Color x3189 = new Color(189,189,189);
    public static Color c213241250= new Color(213, 241, 250);
    public static Color c0554 = new Color(0, 5, 54);
    public static Color x3231 = new Color(231, 231, 231);
    public static Color c00112 = new Color(0, 0, 112);
    public static Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    public static Cursor TEXT_CURSOR = new Cursor(Cursor.TEXT_CURSOR);
    public static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    public static Component W0_H10 = Box.createRigidArea(new Dimension(0, 10));
    public static Component W5_H0 = Box.createRigidArea(new Dimension(5,0));
    public static Component W10_H0 = Box.createRigidArea(new Dimension(10, 0));
    public static Component W10_H0_2 = Box.createRigidArea(new Dimension(10, 0));
    public static Component W10_H70 = Box.createRigidArea(new Dimension(10, 70));
    public static Component W10_H5 = Box.createRigidArea(new Dimension(10, 5));
    /**
     * Prevent non-numeric characters & empty strings
     */
    public static PlainDocument numericOnly = new PlainDocument() {
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }
            if (str.matches("\\d*") && !str.isEmpty()) {
                super.insertString(offset, str, attr);
            }
        }
    };
    public static PlainDocument numericOnly2 = new PlainDocument() {
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }
            if (str.matches("\\d*") && !str.isEmpty()) {
                super.insertString(offset, str, attr);
            }
        }
    };

    /**
     * Clear the screen of a given panel
     * @param panel to clear
     */
    public static void clearScreen(JPanel panel) {
        panel.removeAll();
        SwingUtilities.invokeLater(() -> {
            panel.revalidate();
            panel.repaint();
        });
    }

    /**
     * Setting attributes of a general purpose button: Color, hovering attributes
     * @param btn button to set
     */
    public static void setGeneralButton(JButton btn) {
        btn.setFont(TAHOMA_BOLD_12);
        btn.setBackground(normal);
        btn.setCursor(HAND_CURSOR);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btn.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btn.setBackground(normal);
            }
        });
    }

    /**
     * setting return button for popup
     * @param btn
     * @param popup
     */
    public static void setRetButton(JButton btn, JDialog popup) {
        btn.setCursor(HAND_CURSOR);
        btn.setBackground(ret);
        btn.addActionListener(e -> popup.dispose());
    }

    /**
     * Display an error message
     * @param message message to display
     */
    public static void errorPopup(String message) {
        JDialog popup = new JDialog();
        popup.setAlwaysOnTop(true);
        popup.setSize(280, 110);
        popup.setLocation(centerPopup(popup));
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setTitle("Error");

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retBtn = new JButton("Back");
        noFocusBorder(retBtn);
        setRetButton(retBtn, popup);
        retBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        popupPanel.add(messageLabel);
        popupPanel.add(W0_H10);
        popupPanel.add(retBtn);
        popup.add(popupPanel);
        popup.setVisible(true);
    }

    /**
     * Move the frame to the center of the screen
     * @param frame frame to move
     * @return center point of the screen
     */
    public static Point centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        return new Point(x, y);
    }
    /**
     * Move the frame to the center of the screen
     * @param dialog frame to move
     * @return center point of the screen
     */
    public static Point centerPopup(JDialog dialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        return new Point(x, y);
    }

    /**
     * disable border on focus for button
     * @param btn
     */
    public static void noFocusBorder(JButton btn) {
        btn.setUI(new BasicButtonUI() {
            @Override
            protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect,
                                      Rectangle iconRect) {
                //do nothing
            }
        });
    }

    /**
     * disable border on focus for radio buttons
     * @param btn
     */
    public static void noFocusBorder(JRadioButton btn) {
        btn.setUI(new BasicRadioButtonUI() {
            @Override
            protected void paintFocus(Graphics g, Rectangle t, Dimension d) {
                //do nothing
            }
        });
    }

    /**
     * get key, iv for decryption process
     * @param key of encryption
     * @param iv of encryption
     * @return
     */
    public static AESData getKeyIv(String key, String iv) {
        if (key.isEmpty() || iv.isEmpty()) {
            return null;
        }

        String[] keyNumberStrings = key.substring(1, key.length() - 1).split(", ");
        int[] keyNumbers = new int[keyNumberStrings.length];
        if (keyNumbers.length != 32) {
            return null;
        }
        for (int i = 0; i < keyNumberStrings.length; i++) {
            keyNumbers[i] = Integer.parseInt(keyNumberStrings[i]);
        }

        byte[] keyBytes = new byte[keyNumbers.length];
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) keyNumbers[i];
        }
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        String[] ivNumberStrings = iv.substring(1, iv.length() - 1).split(", ");
        int[] ivNumbers = new int[ivNumberStrings.length];
        if (ivNumbers.length != 16) {
            return null;
        }
        for (int i = 0; i < ivNumberStrings.length; i++) {
            ivNumbers[i] = Integer.parseInt(ivNumberStrings[i]);
        }

        byte[] ivBytes = new byte[ivNumbers.length];
        for (int i = 0; i < ivBytes.length; i++) {
            ivBytes[i] = (byte) ivNumbers[i];
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        AESData aesData = new AESData();
        aesData.setKey(secretKey);
        aesData.setIv(ivParameterSpec);
        return aesData;
    }

    /**
     * setting a general button for sniffer GUI
     * @param btn button to set
     * @param name of button
     * @param c color of button
     */
    public static void setSnifferBtn(JButton btn, String name, Color c) {
        btn.setText(name);
        btn.setBackground(c);
        btn.setCursor(HAND_CURSOR);
        btn.setFont(TAHOMA_BOLD_11);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        noFocusBorder(btn);
        btn.setPreferredSize(new Dimension(75, 20));
        btn.setMinimumSize(new Dimension(75, 20));
        btn.setMaximumSize(new Dimension(75, 20));
    }

    /**
     * create a general title border
     * @param title for the border
     * @return titled border
     */
    public static TitledBorder getTitledBorder(String title) {
        TitledBorder tb = new TitledBorder(new LineBorder(Color.BLACK), title);
        tb.setTitleColor(Color.BLACK);
        tb.setTitleJustification(TitledBorder.CENTER);
        return tb;
    }

    /**
     * create a general help popup for the menu
     * @param title of popup
     */
    public static void helpPopup(String title) {
        JDialog popup = new JDialog();
        popup.setAlwaysOnTop(true);
        popup.setTitle(title);
        popup.setBackground(Color.WHITE);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel helpTitle = new JLabel();
        helpTitle.setFont(TAHOMA_BOLD_13);
        helpTitle.setFocusable(false);
        helpTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpTitle.setCursor(DEFAULT_CURSOR);

        JTextArea help = new JTextArea();
        help.setFocusable(false);
        help.setCursor(DEFAULT_CURSOR);
        help.setBackground(Color.WHITE);
        help.setFont(TAHOMA_PLAIN_12);

        if (title.equals("Encryption/Decryption Help")) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            popup.setSize(500, 500);
            popup.setLocation(centerPopup(popup));
            helpTitle.setText("A encryption / decryption tool, using AES/CBC");
            help.setText(" Instructions:\n" +
                    " Encryption:\n (1) Select target file for encryption.\n" +
                    " (2) A popup will be displayed. Click the buttons to copy the data to clipboard\n" +
                    " and secure in a safe location. You are required to save both the key and the IV\n" +
                    " for future decryption.\n" +
                    " (3) The encrypted file will be in the same location of your target file\n" +
                    " with the original filetype + the text \"Enc\"\n\n\n" +
                    " Decryption:\n" +
                    " (1) Select the encrypted file with the suffix described in (3) in Encryption section.\n" +
                    " (2) In the popup, insert the exact same key and IV that were given in the encryption\n process." +
                    " The key and the IV will be checked for validity.\n" +
                    " (3) Your new decrypted file will be located in the same location as the encrypted file,\n" +
                    " with the name suffix \"Dec\"");
            panel.add(helpTitle);
            panel.add(Box.createRigidArea(new Dimension(10, 20)));
            panel.add(help);
            popup.add(panel);
        } else {
            popup.setSize(1020, 740);
            popup.setLocation(centerPopup(popup));
            helpTitle.setText("A network sniffing tool");
            URL imageRes = Utils.class.getResource("/GUI/img/ns_help.jpg");
            if (imageRes != null) {
                ImageIcon image = new ImageIcon(imageRes);
                JLabel imageLabel = new JLabel(image);
                popup.add(imageLabel);
            } else {
                errorPopup("Image not found");
            }
        }
        popup.setVisible(true);
    }
}
