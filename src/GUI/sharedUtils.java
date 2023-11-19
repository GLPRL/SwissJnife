package GUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        sharedUtils.setRetButton(retBtn, popup);
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
}
