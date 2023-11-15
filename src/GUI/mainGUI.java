package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        createMenuBar();
        panel = new JPanel();                               //Main panel settings
        BoxLayout box = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(box);
        panel.setBackground(Color.WHITE);
        frame.add(panel);
    }

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
                    errorPopup("Could not open the project on browser");
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
        encBtn.setBackground(new Color(150, 245, 222));
        encBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel1.add(encBtn);
        panel1.add(rigidArea);

        //Create decrypt file button
        JButton decBtn = new JButton("Decrypt File");
        decBtn.setBackground(new Color(150, 245, 222));
        decBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel1.add(decBtn);
        panel1.setBackground(Color.WHITE);

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

        JButton sniff = new JButton("Network Sniffer");
        sniff.setBackground(new Color(150, 245, 222));
        sniff.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel3.add(sniff);

        //Add listeners
        setListeners(encBtn, decBtn, vulScan, sniff);
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
    public void setListeners(JButton encBtn, JButton decBtn, JButton vulScan, JButton sniff) {
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
            try {
                gui.presentGui(frame, mainGUI.this);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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
        sniff.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                sniff.setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                sniff.setBackground(normal);
            }
        });
    }
    public void errorPopup(String message) {
        JDialog popup = new JDialog();
        popup.setSize(240, 110);
        popup.setLocationRelativeTo(frame);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setTitle("Error");

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton retBtn = new JButton("Back");
        retBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        retBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retBtn.addActionListener(e -> popup.dispose());
        retBtn.setBackground(new Color(255, 48, 62, 255));

        popupPanel.add(messageLabel);
        popupPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        popupPanel.add(retBtn);
        popup.add(popupPanel);
        popup.setVisible(true);
    }
}