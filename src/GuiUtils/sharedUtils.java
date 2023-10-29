package GuiUtils;
import javax.swing.*;

public class sharedUtils {
    public static void clearScreen(JPanel panel) {
        panel.removeAll();
        SwingUtilities.invokeLater(() -> {
            panel.revalidate();
            panel.repaint();
        });
    }
}
