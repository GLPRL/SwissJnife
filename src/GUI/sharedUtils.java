package GUI;
import Logics.EncDec.AESData;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class sharedUtils {
    static Color normal = new Color(150, 245, 222);
    static Color onHover = new Color(118, 192, 173);
    static Color ret = new Color(255, 48, 62, 255);

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
        btn.setBackground(normal);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        popupPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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
}
