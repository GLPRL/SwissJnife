package GUI;

import GUI.EncDec.decryptGUI;
import GUI.EncDec.encryptGUI;
import GUI.sniffer.netSnifferGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        createMenuBar();
        panel = new JPanel();                               //Main panel settings
        BoxLayout box = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(box);
        panel.setBackground(Color.WHITE);
        frame.add(panel);
    }

    /**
     * Creating menu bar at the top of the window.
     * Allows to see credits, open the project on GitHub, and close the program.
     */
    public void createMenuBar() {
        JMenuBar menu = new JMenuBar();
        menu.setBackground(new Color(246, 246, 246, 255));
        JMenu file = new JMenu(("File"));
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1, false);
        menu.setBorder(border);
        file.setBorder(border);
        JMenuItem credits = new JMenuItem("Credits");
        credits.setBorder(border);
        file.add(credits);
        JMenuItem uri = new JMenuItem("Project on Github");
        uri.addActionListener(e -> {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.github.com/GLPRL/SwissJnife"));
                } catch (IOException | URISyntaxException ex) {
                    //create error popup that we failed opening a browser
                    sharedUtils.errorPopup("Could not open the project on browser", frame);
                    throw new RuntimeException(ex);
                }
            }
        });
        uri.setBorder(border);
        file.add(uri);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setBorder(border);
        file.add(exit);
        menu.add(file);
        frame.setJMenuBar(menu);
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
        rigidArea.setBackground(Color.WHITE);
        this.panel.add(rigidArea);

        //Create encrypt file button
        JButton encBtn = new JButton("Encrypt File");
        sharedUtils.setGeneralButton(encBtn);
        panel1.add(encBtn);
        panel1.add(rigidArea);

        //Create decrypt file button
        JButton decBtn = new JButton("Decrypt File");
        sharedUtils.setGeneralButton(decBtn);
        panel1.add(decBtn);
        panel1.setBackground(Color.WHITE);

        //Create second column
        JPanel panel2 = new JPanel();
        BoxLayout box2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(box2);
        panel2.setAlignmentY(Component.TOP_ALIGNMENT);
        panel2.removeAll();

        JButton vulScan = new JButton("Self Scanner");
        sharedUtils.setGeneralButton(vulScan);
        panel2.add(vulScan);

        JPanel panel3 = new JPanel();
        BoxLayout box3 = new BoxLayout(panel3, BoxLayout.Y_AXIS);
        panel3.setLayout(box3);
        panel3.setAlignmentY(Component.TOP_ALIGNMENT);
        panel3.removeAll();

        JButton sniff = new JButton("Network Sniffer");
        sharedUtils.setGeneralButton(sniff);
        panel3.add(sniff);

        //Add listeners
        setClickListeners(encBtn, decBtn, vulScan, sniff);

        Component component = Box.createRigidArea(new Dimension(10, 10));
        component.setBackground(Color.WHITE);
        this.panel.add(component);
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

    /**
     * Setting click listeners to start a wanted program
     * @param encBtn to begin file encryption
     * @param decBtn to begin file decryption
     * @param vulScan to begin system vulnerability scanning
     * @param sniff to begin network sniffer
     */
    public void setClickListeners(JButton encBtn, JButton decBtn, JButton vulScan, JButton sniff) {
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
        sniff.addActionListener(e -> {
            sharedUtils.clearScreen(panel);
            netSnifferGUI gui = new netSnifferGUI();
            gui.presentGui(frame, mainGUI.this);
        });

    }

}