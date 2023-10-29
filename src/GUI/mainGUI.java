package GUI;
import GuiUtils.sharedUtils;

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
        frame = new JFrame("SwissJnife");              //Window settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel = new JPanel();                               //Main panel settings
        BoxLayout box = new BoxLayout(panel, BoxLayout.X_AXIS);

        panel.setLayout(box);
        frame.add(panel);
    }

    /**
     * General method, creating the content of the main GUI
     */
    public void createContent() {
        panel.removeAll();
        JPanel panel1 = new JPanel();                               //First column setup
        BoxLayout box1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);

        //Create first column
        panel1.setLayout(box1);
        panel1.setAlignmentY(Component.TOP_ALIGNMENT);
        panel1.removeAll();

        Component rigidArea = Box.createRigidArea(new Dimension(10, 5));
        this.panel.add(rigidArea);

        //Create encrypt file button
        JButton encBtn = new JButton("Encrypt File");
        encBtn.setBackground(new Color(150, 245, 222));
        encBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel1.add(encBtn);
        panel1.add(rigidArea);

        //Create decrypt file button
        JButton decBtn = new JButton("Decrypt File");
        decBtn.setBackground(new Color(150, 245, 222));
        decBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel1.add(decBtn);

        //Create second column
        JPanel panel2 = new JPanel();
        BoxLayout box2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(box2);
        panel2.setAlignmentY(Component.TOP_ALIGNMENT);
        panel2.removeAll();

        Component rigidArea2 = Box.createRigidArea(new Dimension(5,5));
        this.panel.add(rigidArea2);
        JButton someButton = new JButton("Some_button");
        someButton.setBackground(new Color(150, 245, 222));
        panel2.add(someButton);


        //Add listeners
        encBtn.addActionListener(e -> {
            sharedUtils.clearScreen(panel);
            encryptGUI gui = new encryptGUI();
            gui.presentGui(frame, mainGUI.this);
        });
        decBtn.addActionListener(e -> {
            //TODO: sharedUtils.clearScreen(panel);
            //TODO: decryptGUI gui = new encryptGUI();
            //TODO: gui.presentGui(frame, mainGUI.this);
        });

        this.panel.add(Box.createRigidArea(new Dimension(10, 10)));
        this.panel.add(panel1);
        this.panel.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panel.add(panel2);

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