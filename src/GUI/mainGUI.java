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
    JButton exitBtn;

    /**
     * Constructor.
     * Build the window and the panel/layout.
     */
    public mainGUI() {
        frame = new JFrame("SwissJnife");              //Window settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 150);
        frame.setLocation(Utils.centerFrame(this.frame));

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        createMenuBar();
        panel = new JPanel();                               //Main panel settings
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
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
        JMenuItem uri = createGitHubURI(border);
        file.add(uri);
        menu.add(file);

        JMenu encDec = new JMenu("Encryptor");
        encDec.setBorder(border);
        JMenuItem encDecHelp = new JMenuItem("Help");
        encDecHelp.addActionListener(e -> Utils.helpPopup("Encryption/Decryption Help"));
        encDec.add(encDecHelp);
        menu.add(encDec);

        JMenu sniffer = new JMenu("Sniffer");
        sniffer.setBorder(border);
        JMenuItem snifferHelp = new JMenuItem("Help");
        snifferHelp.addActionListener(e -> Utils.helpPopup("Network Sniffer Help"));
        sniffer.add(snifferHelp);
        menu.add(sniffer);
        frame.setJMenuBar(menu);
    }

    /**
     * Get URI of GitHub project
     * @param border border of the JMenuItem
     * @return the item of the URI link
     */
    private JMenuItem createGitHubURI(Border border) {
        JMenuItem uri = new JMenuItem("Project on Github");
        uri.addActionListener(e -> {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.github.com/GLPRL/SwissJnife"));
                } catch (IOException | URISyntaxException ex) {
                    //create error popup that we failed opening a browser
                    Utils.errorPopup("Could not open the project on browser");
                    throw new RuntimeException(ex);
                }
            }
        });
        uri.setBorder(border);
        return uri;
    }
    /**
     * General method, creating the content of the main GUI
     */
    public void createContent() {
        panel.removeAll();
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setAlignmentY(Component.TOP_ALIGNMENT);
        panel1.removeAll();

        Utils.W10_H5.setBackground(Color.WHITE);
        this.panel.add(Utils.W10_H5);

        //Create encrypt file button
        JButton encBtn = new JButton("Encrypt File");
        Utils.noFocusBorder(encBtn);
        Utils.setGeneralButton(encBtn);
        panel1.add(encBtn);
        panel1.add(Utils.W10_H5);

        //Create decrypt file button
        JButton decBtn = new JButton("Decrypt File");
        Utils.noFocusBorder(decBtn);
        Utils.setGeneralButton(decBtn);
        panel1.add(decBtn);
        panel1.setBackground(Color.WHITE);

        //Create second column
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setAlignmentY(Component.TOP_ALIGNMENT);
        panel2.removeAll();

        JButton ftr_3 = new JButton("ftr_3");
        Utils.noFocusBorder(ftr_3);
        Utils.setGeneralButton(ftr_3);
        panel2.add(ftr_3);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel3.setAlignmentY(Component.TOP_ALIGNMENT);
        panel3.removeAll();

        JButton sniff = new JButton("Network Sniffer");
        Utils.noFocusBorder(sniff);
        Utils.setGeneralButton(sniff);
        panel3.add(sniff);

        //Add listeners
        setClickListeners(encBtn, decBtn, ftr_3, sniff);

        this.panel.add(Utils.W10_H0);
        this.panel.add(panel1);
        this.panel.add(Utils.W10_H0);
        this.panel.add(panel2);
        this.panel.add(Utils.W10_H0_2);
        this.panel.add(panel3);

        exitBtn = new JButton("Exit");
        exitBtn.setCursor(Utils.HAND_CURSOR);
        Utils.noFocusBorder(exitBtn);
        exitBtn.setBackground(Color.RED);
        exitBtn.setFont(Utils.TAHOMA_BOLD_12);
        exitBtn.addActionListener(e -> frame.dispose());
        frame.add(Utils.W10_H70);
        frame.add(exitBtn);
    }

    /**
     * Present the GUI and call to create the content
     */
    public void presentGUI() {
        createContent();
        frame.setSize(650, 150);
        frame.setLocation(Utils.centerFrame(this.frame));
        frame.setVisible(true);
    }

    /**
     * Setting click listeners to start a wanted program
     * @param encBtn to begin file encryption
     * @param decBtn to begin file decryption
     * @param ftr_3 ???
     * @param sniff to begin network sniffer
     */
    public void setClickListeners(JButton encBtn, JButton decBtn, JButton ftr_3, JButton sniff) {
        encBtn.addActionListener(e -> {
            Utils.clearScreen(panel);
            frame.remove(exitBtn);
            encryptGUI gui = new encryptGUI(frame);
            gui.presentGui(mainGUI.this);
        });
        decBtn.addActionListener(e -> {
            Utils.clearScreen(panel);
            frame.remove(exitBtn);
            decryptGUI gui = new decryptGUI(frame);
            gui.presentGui(mainGUI.this);
        });
        ftr_3.addActionListener(e -> Utils.errorPopup("Feature is not yet available"));
        sniff.addActionListener(e -> {
            Utils.clearScreen(panel);
            frame.remove(exitBtn);
            netSnifferGUI gui = new netSnifferGUI(frame);
            gui.presentGui(mainGUI.this);
        });

    }

}