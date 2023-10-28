import javax.swing.*;
import java.awt.*;

/**
 * GUI class to present content of main screen
 */
public class mainGUI {
    JFrame frame;
    JPanel panel;

    /**
     * Constructor.
     * Build the window and the panel/layout.
     */
    public mainGUI() {
        frame = new JFrame("SwissJnife");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);

        panel = new JPanel();
        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(box);
        panel.setBackground(Color.WHITE);
        frame.add(panel);
    }

    /**
     * Clear the screen.
     * @param panel main panel of the screen
     */
    private void clearScreen(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    /**
     * General method for creating buttons
     * @param panel panel to add the button to
     * @param desc the text shown in the button
     */
    private void createButton(JPanel panel, String desc) {
        JButton btn = new JButton(desc);
        Component rigidArea = Box.createRigidArea(new Dimension(5, 10));
        panel.add(rigidArea);
        panel.add(btn);
        //Assign listener for each button.
        btn.addActionListener(e -> {
            clearScreen(panel);
            if (desc.equals("Encrypt File")) {
                encryptGUI gui = new encryptGUI();
                gui.presentGui(frame, mainGUI.this);
            } else if (desc.equals("Decrypt File")) {
                btn.setLocation(10, 40);
            }
        });
    }

    /**
     * General method, creating the content of the main GUI
     */
    public void createContent() {
        createButton(panel, "Encrypt File");
        createButton(panel, "Decrypt File");
    }

    /**
     * Present the GUI and call to create the content
     */
    public void presentGUI() {
        createContent();
        // Show
        frame.setVisible(true);
    }
}