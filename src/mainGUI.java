import javax.swing.*;
import java.awt.*;

public class mainGUI {
    public mainGUI() {
    }

    public void presentGUI() {
        JFrame frame = new JFrame("SwissJnife");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);
        frame.setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NORTH;
        constraints.insets = new Insets(20, 20, 20, 20);

        //Create title
        JLabel title = new JLabel("SwissJnife");
        constraints.gridx = 0;
        constraints.gridy = 0;
        title.setFont(title.getFont().deriveFont(20.0f));
        constraints.anchor = GridBagConstraints.NORTH;
        panel.add(title, constraints);

        JButton button = new JButton("Encrypt File");
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(button, constraints);

        frame.setVisible(true);
    }
}