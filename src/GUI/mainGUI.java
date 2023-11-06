package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GUI class to present content of main screen
 */
public class mainGUI {
    JFrame frame;
    JPanel panel;
    Color normal = new Color(150, 245, 222);
    Color onHover = new Color(118, 192, 173);

    /**
     * Constructor.
     * Build the window and the panel/layout.
     */
    public mainGUI() {
        frame = new JFrame("SwissJnife");              //Window settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

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

        JButton vulScan = new JButton("Self Scanner");
        vulScan.setBackground(new Color(150, 245, 222));
        vulScan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel2.add(vulScan);

        JPanel panel3 = new JPanel();
        BoxLayout box3 = new BoxLayout(panel3, BoxLayout.Y_AXIS);
        panel3.setLayout(box3);
        panel3.setAlignmentY(Component.TOP_ALIGNMENT);
        panel3.removeAll();

        JButton fw = new JButton("Firewall Config");
        fw.setBackground(new Color(150, 245, 222));
        fw.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel3.add(fw);

        //Add listeners
        setListeners(encBtn, decBtn, vulScan, fw);



        this.panel.add(Box.createRigidArea(new Dimension(10, 10)));
        this.panel.add(panel1);
        this.panel.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panel.add(panel2);
        this.panel.add(Box.createRigidArea(new Dimension(10, 0)));
        this.panel.add(panel3);
    }

    /**
     * Present the GUI and call to create the content
     */
    public void presentGUI() {
        createContent();
        // Show
        frame.setVisible(true);
    }
    public void setListeners(JButton encBtn, JButton decBtn, JButton vulScan, JButton fw) {
        encBtn.addActionListener(e -> {
            sharedUtils.clearScreen(panel);
            encryptGUI gui = new encryptGUI();
            gui.presentGui(frame, mainGUI.this);
        });
        decBtn.addActionListener(e -> {
            sharedUtils.clearScreen(panel);
            decryptGUI gui = new decryptGUI();
            gui.presentGui(frame, mainGUI.this);
        });
        vulScan.addActionListener(e -> {
            //TODO
        });
        fw.addActionListener(e -> {
            //TODO
        });

        encBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                encBtn.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                encBtn.setBackground(normal);
            }
        });
        decBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                decBtn.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                decBtn.setBackground(normal);
            }
        });
        vulScan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                vulScan.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                vulScan.setBackground(normal);
            }
        });
        fw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                fw.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                fw.setBackground(normal);
            }
        });
    }
}