package GuiUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class sharedUtils {
    public static void clearScreen(@NotNull JPanel panel) {
        panel.removeAll();
        SwingUtilities.invokeLater(() -> {
            panel.revalidate();
            panel.repaint();
        });
    }
}
