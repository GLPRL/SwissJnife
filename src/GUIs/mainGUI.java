package GUIs;

import javax.swing.*;

public class mainGUI {
    public mainGUI() {}
    public void presentGUI() {
        JFrame frame = new JFrame("SwissJnife");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);
        JButton button = new JButton("Encrypt File");
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}