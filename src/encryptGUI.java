import javax.swing.*;
import java.awt.*;
public class encryptGUI {
    public encryptGUI() {
    }
    public void presentGui(JFrame frame, mainGUI gui) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        frame.add(panel);
        Component rigidArea = Box.createRigidArea(new Dimension(5, 10));
        panel.add(rigidArea);
        JButton backButton = new JButton("Return");
        panel.add(backButton);
        backButton.addActionListener(e -> {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            gui.presentGUI();
        });
        frame.setVisible(true);
    }
}
