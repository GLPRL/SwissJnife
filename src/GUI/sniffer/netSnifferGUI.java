package GUI.sniffer;

import GUI.mainGUI;
import GUI.sharedUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * GUI for network analyzer.
 */
public class netSnifferGUI {
    JFrame frame;
    JPanel mainPanel;
    JPanel controlPanel;
    JPanel portsPanel;
    JPanel normalPorts;
    JPanel buttonsPanel;
    JRadioButton FTPrad = new JRadioButton("FTP (20,21)");
    JRadioButton SSHrad = new JRadioButton("SSH (22)");
    JRadioButton TELNETrad = new JRadioButton("Telnet (23)");
    JRadioButton SMTPrad = new JRadioButton("SMTP (25)");
    JRadioButton DNSrad = new JRadioButton("DNS (53)");
    JRadioButton HTTPrad = new JRadioButton("HTTP (80)");
    JRadioButton POP3rad = new JRadioButton("POP3 (110)");
    JRadioButton IMAPrad = new JRadioButton("IMAP (143)");
    JRadioButton SNMPrad = new JRadioButton("SNMP (161)");
    JRadioButton LDAPrad = new JRadioButton("LDAP (389)");
    JRadioButton HTTPSrad = new JRadioButton("HTTPS (443)");
    JRadioButton RDPrad = new JRadioButton("RDP (3389)");
    ButtonGroup ports = new ButtonGroup();

    /**
     * Constructor
     * @param frame programs' window.
     */
    public netSnifferGUI(@NotNull JFrame frame) {
        this.mainPanel = new JPanel();
        this.controlPanel = new JPanel();
        this.portsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.normalPorts = new JPanel();
        this.frame = frame;

        frame.setTitle("SwissJnife - Network Traffic");
        this.frame.setSize(860, 650);
        this.frame.setLocation(sharedUtils.centerFrame(this.frame));

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        portsPanel.setLayout(new BoxLayout(portsPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        controlPanel.setBackground(Color.WHITE);
        this.frame.add(mainPanel);

    }

    /**
     * Present the sniffers' gui
     * @param gui mainGUI to return
     */
    public void presentGui(mainGUI gui) {
        createElements();

        frame.setVisible(true);
    }
    public void createElements() {
        JTextArea log = createLogArea();
        JScrollPane sp = setLogPane(log);
        mainPanel.add(sp);
        addPanels();
        Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
        rigidArea.setBackground(Color.WHITE);
        mainPanel.add(rigidArea);
        mainPanel.add(controlPanel);
    }

    /**
     * Sets the text pane for scrolling
     * @param log log to attach the pane
     * @return whole panning area
     */
    @NotNull
    private static JScrollPane setLogPane(JTextArea log) {
        JScrollPane sp = new JScrollPane();
        sp.setWheelScrollingEnabled(true);
        sp.setBackground(Color.gray);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setViewportView(log);
        return sp;
    }

    /**
     * Create the textual log
     * @return log window
     */
    @NotNull
    private static JTextArea createLogArea() {
        JTextArea log = new JTextArea();
        Border logBorder = BorderFactory.createEtchedBorder();
        log.setEditable(false);
        log.setFont(new Font("Monospaced", Font.PLAIN, 12));
        log.setForeground(new Color(0, 0, 112));
        log.setPreferredSize(new Dimension(800, 360));
        log.setBorder(logBorder);
        log.setLineWrap(true);
        log.setBackground(Color.WHITE);
        log.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        return log;
    }

    /**
     * Adding the controlling panels
     */
    public void addPanels() {
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        setPortsPanel();
        setButtonsPanel();

        controlPanel.add(portsPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(5,0)));
        controlPanel.add(buttonsPanel);
    }

    /**
     * Creates the port filtering options control panel
     */
    public void setPortsPanel() {
        Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
        rigidArea.setBackground(new Color(189,189,189));

        JLabel title = new JLabel("Port Filter");
        title.setFont(new Font("Tahoma", Font.PLAIN, 14));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        portsPanel.add(title);

        portsPanel.setBackground(new Color(189, 189, 189));
        portsPanel.setBorder(BorderFactory.createEtchedBorder());
        portsPanel.setMaximumSize(new Dimension(250, 165));
        portsPanel.setMinimumSize(new Dimension(250, 165));

        colSetup(FTPrad, SSHrad, TELNETrad, SMTPrad);
        colSetup(DNSrad, HTTPrad, POP3rad, IMAPrad);
        colSetup(SNMPrad, LDAPrad, HTTPSrad, RDPrad);

        portsPanel.add(rigidArea);
        portsPanel.add(normalPorts);

        JPanel misc = customPortPanel();

        portsPanel.add(rigidArea);
        portsPanel.add(misc);
    }

    /**
     * Setting each column of JRadioButtons
     * @param btnOne first button
     * @param btnTwo second button
     * @param btnThree third button
     * @param btnFour fourth button
     */
    private void colSetup(JRadioButton btnOne, JRadioButton btnTwo, JRadioButton btnThree, JRadioButton btnFour) {
        JPanel portCol1 = getColPanel();
        portSetup(btnOne);
        portSetup(btnTwo);
        portSetup(btnThree);
        portSetup(btnFour);
        portCol1.add(btnOne);
        portCol1.add(btnTwo);
        portCol1.add(btnThree);
        portCol1.add(btnFour);
        normalPorts.add(portCol1);
    }

    @NotNull
    private static JPanel getColPanel() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(new Color(189, 189, 189));
        return col;
    }

    /**
     * Sets the custom ports panel filtering
     * @return panel for filtering custom ports
     */
    @NotNull
    private static JPanel customPortPanel() {
        JPanel misc = new JPanel();
        misc.setLayout(new BoxLayout(misc, BoxLayout.X_AXIS));
        misc.setBackground(new Color(189, 189, 189));

        JLabel customPortLabel = new JLabel("Custom Port: ");
        customPortLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        misc.add(customPortLabel);

        JTextField customPort = new JTextField();
        customPort.setBackground(Color.WHITE);
        customPort.setBorder(new EtchedBorder());
        customPort.setMaximumSize(new Dimension(60, 22));
        customPort.setMinimumSize(new Dimension(60, 22));
        customPort.setHorizontalAlignment(JTextField.CENTER);
        misc.add(customPort);

        JButton addPortBtn = new JButton("Listen");
        addPortBtn.setBackground(Color.GREEN);
        addPortBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        addPortBtn.setMaximumSize(new Dimension(69, 21));
        addPortBtn.setMaximumSize(new Dimension(69, 21));
        sharedUtils.noFocusBorder(addPortBtn);
        addPortBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        misc.add(addPortBtn);
        return misc;
    }

    /**
     * Creates general controls panel for the analyzer.
     */
    public void setButtonsPanel() {
        buttonsPanel.setBackground(new Color(189, 189, 189));
        buttonsPanel.setBorder(BorderFactory.createEtchedBorder());
        buttonsPanel.setPreferredSize(new Dimension(300, 165));
        normalPorts.setBackground(new Color(189, 189, 189));
        normalPorts.setLayout(new BoxLayout(normalPorts, BoxLayout.X_AXIS));
    }

    /**
     * Setting visual attributes to a button
     * @param btn button to set
     */
    public void portSetup(JRadioButton btn) {
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btn.setBackground(new Color(189, 189, 189));
        btn.setSize(90, 25);
        sharedUtils.noFocusBorder(btn);
        ports.add(btn);
    }
}
