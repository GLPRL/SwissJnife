package GUI;
import Logics.EncDec.AESData;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class sharedUtils {
    public static Color normal = new Color(150, 245, 222);
    public static Color onHover = new Color(118, 192, 173);
    public static Color ret = new Color(255, 48, 62, 255);
    public static Font TAHOMA_PLAIN_13= new Font("Tahoma", Font.PLAIN, 13);
    public static Font TAHOMA_BOLD_12 = new Font("Tahoma", Font.BOLD, 12);
    public static Font TAHOMA_PLAIN_12 = new Font("Tahoma", Font.PLAIN, 12);
    public static Font MONO_PLAIN_12 = new Font("Monospaced", Font.PLAIN, 12);
    public static Font MONO_PLAIN_11 = new Font("Monospaced", Font.PLAIN, 1);
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
    public static Component W0_H10_2 = Box.createRigidArea(new Dimension(0, 10));
    public static Component W0_H10_3 = Box.createRigidArea(new Dimension(0, 10));
    public static Component W0_H10_4 = Box.createRigidArea(new Dimension(0, 10));
    public static Component W0_H15 = Box.createRigidArea(new Dimension(0, 15));
    public static Component W5_H0 = Box.createRigidArea(new Dimension(5,0));
    public static Component W10_H0 = Box.createRigidArea(new Dimension(10, 0));
    public static Component W10_H0_2 = Box.createRigidArea(new Dimension(10, 0));
    public static Component W10_H70 = Box.createRigidArea(new Dimension(10, 70));
    public static Component W10_H5 = Box.createRigidArea(new Dimension(10, 5));
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
    public static void clearScreen(@NotNull JPanel panel) {
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
    public static void setRetButton(JButton btn, JDialog popup) {
        btn.setCursor(HAND_CURSOR);
        btn.setBackground(ret);
        btn.addActionListener(e -> popup.dispose());
    }

    /**
     * Display an error message
     * @param message message to display
     * @param frame for location options
     */
    public static void errorPopup(String message, JFrame frame) {
        JDialog popup = new JDialog();
        popup.setAlwaysOnTop(true);
        popup.setSize(280, 110);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        popup.setLocation(x, y);
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
    public static void noFocusBorder(JButton btn) {
        btn.setUI(new BasicButtonUI() {
            @Override
            protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect,
                                      Rectangle iconRect) {
                //do nothing
            }
        });
    }
    public static void noFocusBorder(JRadioButton btn) {
        btn.setUI(new BasicRadioButtonUI() {
            @Override
            protected void paintFocus(Graphics g, Rectangle t, Dimension d) {
                //do nothing
            }
        });
    }
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
     * Formatting MAC Address string
     * @param mac MAC addr.
     * @return formatted string
     */
    public static String formatMac(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (byte b: mac) {
            sb.append(String.format("%02X:", b));
        }
        return sb.substring(0, sb.length() - 1).replace("/", "");
    }

    /**
     * Formatting IP Address string
     * @param addr IP addr.
     * @return formatted string
     */
    public static String formatIP(NetworkInterfaceAddress[] addr) {
        StringBuilder sb = new StringBuilder();
        for (NetworkInterfaceAddress address: addr) {
            sb.append(address.address).append(" ");
        }
        return sb.toString().replace("/", "");
    }

    /**
     * Formatting Subnet Mask string
     * @param dev device
     * @return formatted string
     */
    public static String getSubnetMask(NetworkInterface dev) {
        NetworkInterfaceAddress[] addresses = dev.addresses;
        if (addresses[0].subnet != null) {
            return addresses[0].subnet.toString();
        } else {
            return "N/A";
        }
    }
}
