package GUI.sniffer;

import GUI.mainGUI;
import GUI.sharedUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
    JToggleButton FTPrad = new JToggleButton("FTP (20,21)");
    JToggleButton SSHrad = new JToggleButton("SSH (22)");
    JToggleButton TELNETrad = new JToggleButton("Telnet (23)");
    JToggleButton SMTPrad = new JToggleButton("SMTP (25)");
    JToggleButton DNSrad = new JToggleButton("DNS (53)");
    JToggleButton HTTPrad = new JToggleButton("HTTP (80)");
    JToggleButton POP3rad = new JToggleButton("POP3 (110)");
    JToggleButton IMAPrad = new JToggleButton("IMAP (143)");
    JToggleButton SNMPrad = new JToggleButton("SNMP (161)");
    JToggleButton LDAPrad = new JToggleButton("LDAP (389)");
    JToggleButton HTTPSrad = new JToggleButton("HTTPS (443)");
    JToggleButton RDPrad = new JToggleButton("RDP (3389)");

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
        TitledBorder tb = new TitledBorder(new LineBorder(Color.BLACK), "Port Filter");
        tb.setTitleColor(Color.BLACK);
        tb.setTitleJustification(TitledBorder.CENTER);
        tb.setTitleColor(Color.BLACK);

        portsPanel.setBackground(Color.WHITE);
        portsPanel.setBorder(tb);

        portsPanel.setMaximumSize(new Dimension(350, 220));
        portsPanel.setMinimumSize(new Dimension(350, 220));

        colSetup(FTPrad, SSHrad, TELNETrad, SMTPrad);
        colSetup(DNSrad, HTTPrad, POP3rad, IMAPrad);
        colSetup(SNMPrad, LDAPrad, HTTPSrad, RDPrad);

        portsPanel.add(rigidArea);
        portsPanel.add(normalPorts);

        JPanel misc = customPortPanel();

        portsPanel.add(rigidArea);
        portsPanel.add(misc);
        portsPanel.setMaximumSize(new Dimension(300, 190));
        portsPanel.setMinimumSize(new Dimension(300, 190));
        normalPorts.setBackground(Color.WHITE);
        normalPorts.setLayout(new BoxLayout(normalPorts, BoxLayout.X_AXIS));
    }

    /**
     * Setting each column of JRadioButtons
     * @param btnOne first button
     * @param btnTwo second button
     * @param btnThree third button
     * @param btnFour fourth button
     */
    private void colSetup(JToggleButton btnOne, JToggleButton btnTwo, JToggleButton btnThree, JToggleButton btnFour) {
        JPanel col = getColPanel();
        portSetup(btnOne);
        portSetup(btnTwo);
        portSetup(btnThree);
        portSetup(btnFour);
        col.add(btnOne);
        col.add(btnTwo);
        col.add(btnThree);
        col.add(btnFour);
        normalPorts.add(col);
    }

    /**
     * Sets up a column panel for adding radio buttons
     * @return new panel
     */
    @NotNull
    private static JPanel getColPanel() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(Color.WHITE);
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
        misc.setBackground(Color.WHITE);

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
        addPortBtn.setMaximumSize(new Dimension(69, 21));
        addPortBtn.setMaximumSize(new Dimension(69, 21));
        addRemovePort(addPortBtn, misc);

        JButton removePortBtn = new JButton("Delete");
        removePortBtn.setBackground(Color.RED);
        removePortBtn.setMaximumSize(new Dimension(74, 21));
        removePortBtn.setMaximumSize(new Dimension(74, 21));
        addRemovePort(removePortBtn, misc);

        return misc;
    }
    public static void addRemovePort(JButton btn, JPanel panel) {
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Tahoma", Font.BOLD, 11));
        sharedUtils.noFocusBorder(btn);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(btn);
    }

    /**
     * Creates general controls panel for the analyzer.
     */
    public void setButtonsPanel() {
        buttonsPanel.setBackground(Color.WHITE);

        TitledBorder tb = new TitledBorder(new LineBorder(Color.BLACK), "Controls");
        tb.setTitleColor(Color.BLACK);
        tb.setTitleJustification(TitledBorder.CENTER);
        tb.setTitle("Controls");
        buttonsPanel.setBorder(tb);
        buttonsPanel.setMaximumSize(new Dimension(400, 220));
        buttonsPanel.setMinimumSize(new Dimension(400, 220));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        JPanel interfacePanel = new JPanel();
        interfacePanel.setBorder(new EtchedBorder());
        interfacePanel.setLayout(new BoxLayout(interfacePanel, BoxLayout.Y_AXIS));
        interfacePanel.setBackground(Color.WHITE);
        interfacePanel.setMinimumSize(new Dimension(110, 90));
        interfacePanel.setMaximumSize(new Dimension(110, 110));

        JLabel interfaceLabel = new JLabel("Interface #");
        interfaceLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        interfaceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceLabel);

        JTextField interfaceText = new JTextField();
        interfaceText.setPreferredSize(new Dimension(30, 25));
        interfaceText.setMaximumSize(new Dimension(30, 25));
        interfaceText.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceText.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceText);

        JButton interfaceSet = new JButton("Set");
        sharedUtils.setGeneralButton(interfaceSet);
        interfaceSet.setFont(new Font("Tahoma", Font.BOLD, 11));
        interfaceSet.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceSet.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceSet.setMaximumSize(new Dimension(53, 25));
        interfaceSet.setMinimumSize(new Dimension(53, 25));
        sharedUtils.noFocusBorder(interfaceSet);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceSet);

        JButton interfaceStart = new JButton("Start");
        interfaceStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sharedUtils.noFocusBorder(interfaceStart);
        interfaceStart.setBackground(Color.GREEN);
        interfaceStart.setFont(new Font("Tahoma", Font.BOLD, 11));
        interfaceStart.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceStart.setMaximumSize(new Dimension(70, 25));
        interfaceStart.setMinimumSize(new Dimension(70, 25));
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceStart);

        interfacePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel.add(interfacePanel);

    }

    /**
     * Setting visual attributes to a button
     * @param btn button to set
     */
    public void portSetup(JToggleButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btn.setBackground(Color.WHITE);
        btn.setMaximumSize(new Dimension(110, 25));
        btn.setMinimumSize(new Dimension(110, 25));
    }
}
