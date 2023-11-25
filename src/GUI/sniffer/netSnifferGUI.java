package GUI.sniffer;

import GUI.mainGUI;
import GUI.sharedUtils;
import Logics.sniffer.netSniffer;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * GUI for network analyzer.
 */
public class netSnifferGUI {
    static JFrame frame;
    static JTextArea log;
    static JScrollPane scrollPane;
    static JPanel mainPanel;
    JPanel controlPanel;
    JPanel portsPanel;
    JPanel normalPorts;
    JPanel buttonsPanel;
    static mainGUI gui;
    static JRadioButton filterPorts;
    static JRadioButton allPorts;
    static JButton addPortBtn;
    static JButton removePortBtn;
    static JButton startBtn;
    static JButton exitBtn;
    static JTextArea interfaceInfo;
    static JButton interfaceList;
    static JButton interfaceSet;
    static JToggleButton FTPrad = new JToggleButton("FTP (20,21)");
    static JToggleButton SSHrad = new JToggleButton("SSH (22)");
    static JToggleButton TELNETrad = new JToggleButton("Telnet (23)");
    static JToggleButton SMTPrad = new JToggleButton("SMTP (25)");
    static JToggleButton DNSrad = new JToggleButton("DNS (53)");
    static JToggleButton HTTPrad = new JToggleButton("HTTP (80)");
    static JToggleButton POP3rad = new JToggleButton("POP3 (110)");
    static JToggleButton IMAPrad = new JToggleButton("IMAP (143)");
    static JToggleButton SNMPrad = new JToggleButton("SNMP (161)");
    static JToggleButton LDAPrad = new JToggleButton("LDAP (389)");
    static JToggleButton HTTPSrad = new JToggleButton("HTTPS (443)");
    static JToggleButton RDPrad = new JToggleButton("RDP (3389)");

    /**
     * Constructor
     * @param frame programs' window.
     */
    public netSnifferGUI(@NotNull JFrame frame) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBackground(Color.WHITE);

        this.controlPanel = new JPanel();
        controlPanel.setBackground(Color.WHITE);

        this.portsPanel = new JPanel();
        portsPanel.setLayout(new BoxLayout(portsPanel, BoxLayout.Y_AXIS));

        this.buttonsPanel = new JPanel();
        this.normalPorts = new JPanel();
        netSnifferGUI.frame = frame;

        frame.setTitle("SwissJnife - Network Traffic");
        netSnifferGUI.frame.setSize(860, 650);
        netSnifferGUI.frame.setLocation(sharedUtils.centerFrame(netSnifferGUI.frame));
        netSnifferGUI.frame.add(mainPanel);


    }

    /**
     * Present the sniffers' gui
     * @param gui mainGUI to return
     */
    public void presentGui(mainGUI gui) {
        createElements();
        netSnifferGUI.gui = gui;
        frame.setVisible(true);
    }
    public void createElements() {
        createLog();
        addPanels();
        Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
        rigidArea.setBackground(Color.WHITE);
        mainPanel.add(rigidArea);
        mainPanel.add(controlPanel);

    }

    private static void createLog() {
        // Create a JTextArea
        log = new JTextArea(22, 100);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setFont(new Font("Monospaced", Font.PLAIN, 12));
        log.setForeground(new Color(0, 0, 112));
        log.setBorder(BorderFactory.createEtchedBorder());
        log.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        scrollPane = new JScrollPane(log);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * Adding the control panels
     */
    public void addPanels() {
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        setPortsPanel();

        setButtonsPanel();

        controlPanel.add(portsPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(5,0)));
        controlPanel.add(buttonsPanel);
        setBtnLock(false);
    }

    /**
     * Sets locking status for listening capabilities
     * @param b is locked
     */
    public static void setBtnLock(Boolean b) {
        addPortBtn.setEnabled(b);
        removePortBtn.setEnabled(b);
        startBtn.setEnabled(b);
        FTPrad.setEnabled(b);
        SSHrad.setEnabled(b);
        TELNETrad.setEnabled(b);
        SMTPrad.setEnabled(b);
        DNSrad.setEnabled(b);
        HTTPrad.setEnabled(b);
        POP3rad.setEnabled(b);
        IMAPrad.setEnabled(b);
        SNMPrad.setEnabled(b);
        LDAPrad.setEnabled(b);
        HTTPSrad.setEnabled(b);
        RDPrad.setEnabled(b);
        filterPorts.setEnabled(b);
        allPorts.setEnabled(b);
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

        portsPanel.setMaximumSize(new Dimension(350, 150));
        portsPanel.setMinimumSize(new Dimension(350, 150));

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

        addPortBtn = new JButton("Listen");
        addPortBtn.setBackground(Color.GREEN);
        addPortBtn.setMaximumSize(new Dimension(75, 20));
        addPortBtn.setMaximumSize(new Dimension(75, 20));
        addRemovePort(addPortBtn, misc);

        removePortBtn = new JButton("Delete");
        removePortBtn.setBackground(Color.RED);
        removePortBtn.setMaximumSize(new Dimension(75, 20));
        removePortBtn.setMaximumSize(new Dimension(75, 20));
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

        JPanel interfacePanel = getInterfacesPanel();
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel.add(interfacePanel);

        JPanel filterStatusPanel = createFilterStatusPanel();
        buttonsPanel.add(filterStatusPanel);

        JPanel generalPanel = createGeneralPanel();
        buttonsPanel.add(generalPanel);

    }
    public static JPanel createGeneralPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(95, 110));
        panel.setMinimumSize(new Dimension(95, 110));

        startBtn = new JButton("Start");
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        startBtn.setBackground(Color.GREEN);
        startBtn.setHorizontalAlignment(SwingConstants.CENTER);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sharedUtils.noFocusBorder(startBtn);
        startBtn.setMinimumSize(new Dimension(75, 25));
        startBtn.setMaximumSize(new Dimension(75, 25));
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(startBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        exitBtn.setSize(startBtn.getSize());
        exitBtn.setBackground(Color.RED);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setMinimumSize(new Dimension(75, 25));
        exitBtn.setMaximumSize(new Dimension(75, 25));
        exitBtn.addActionListener(e -> {
            frame.remove(scrollPane);
            sharedUtils.clearScreen(mainPanel);
            gui.presentGUI();
        });
        sharedUtils.noFocusBorder(exitBtn);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(exitBtn);


        return panel;
    }
    public static JPanel createFilterStatusPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(200, 110));
        panel.setMinimumSize(new Dimension(200, 110));

        JLabel status = new JLabel("Status");
        status.setFont(new Font("Tahoma", Font.PLAIN, 13));
        status.setMaximumSize(new Dimension(150, 25));
        status.setMinimumSize(new Dimension(150, 25));
        status.setBorder(BorderFactory.createRaisedBevelBorder());
        status.setForeground(Color.RED);
        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(status);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setMaximumSize(new Dimension(150, 20));
        radioPanel.setMinimumSize(new Dimension(150, 20));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.setBackground(Color.WHITE);
        ButtonGroup bg = new ButtonGroup();
        allPorts = new JRadioButton("Disable");
        allPorts.setBackground(Color.WHITE);
        filterPorts = new JRadioButton("Enable");
        filterPorts.setBackground(Color.WHITE);
        sharedUtils.noFocusBorder(allPorts);
        sharedUtils.noFocusBorder(filterPorts);
        bg.add(allPorts);
        bg.add(filterPorts);
        radioPanel.add(allPorts);
        radioPanel.add(filterPorts);
        radioPanel.setBorder(new EtchedBorder());
        filterPorts.addActionListener(e -> status.setText("Enabled (Using Filter)"));
        allPorts.addActionListener(e -> status.setText("Disabled (All Ports)"));
        panel.add(radioPanel);

        interfaceInfo = new JTextArea();
        interfaceInfo.setLineWrap(true);
        interfaceInfo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        interfaceInfo.setEditable(false);
        interfaceInfo.setForeground(new Color(0, 5, 54));
        interfaceInfo.setMinimumSize(new Dimension(150, 80));
        interfaceInfo.setMinimumSize(new Dimension(150, 80));
        interfaceInfo.setBackground(new Color(231, 231, 231));
        panel.add(interfaceInfo);
        return panel;
    }

    @NotNull
    private static JPanel getInterfacesPanel() {
        JPanel interfacePanel = new JPanel();
        interfacePanel.setBorder(new EtchedBorder());
        interfacePanel.setLayout(new BoxLayout(interfacePanel, BoxLayout.Y_AXIS));
        interfacePanel.setBackground(Color.WHITE);
        interfacePanel.setMinimumSize(new Dimension(95, 110));
        interfacePanel.setMaximumSize(new Dimension(95, 110));

        JLabel interfaceLabel = new JLabel("Interface #");
        interfaceLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        interfaceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceLabel);

        JTextField interfaceText = new JTextField();
        interfaceText.setPreferredSize(new Dimension(40, 25));
        interfaceText.setMaximumSize(new Dimension(40, 25));
        interfaceText.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceText.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceText);

        interfaceList = new JButton("List");
        interfaceList.addActionListener(e -> {
            netSniffer ns = new netSniffer();
            NetworkInterface[] networkInterfaces = ns.listInterfaces();
            printInterfaceList(networkInterfaces);
        });
        sharedUtils.setGeneralButton(interfaceList);
        interfaceList.setFont(new Font("Tahoma", Font.BOLD, 11));
        interfaceList.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceList.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceList.setMaximumSize(new Dimension(70,25));
        interfaceList.setMinimumSize(new Dimension(70, 25));
        sharedUtils.noFocusBorder(interfaceList);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceList);

        interfaceSet = new JButton("Set");
        interfaceSet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sharedUtils.noFocusBorder(interfaceSet);
        interfaceSet.setBackground(Color.GREEN);
        interfaceSet.setFont(new Font("Tahoma", Font.BOLD, 11));
        interfaceSet.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceSet.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceSet.setMaximumSize(new Dimension(70, 25));
        interfaceSet.setMinimumSize(new Dimension(70, 25));
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceSet);
        return interfacePanel;
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
        btn.setFocusPainted(false);
        btn.setBackground(new Color(213, 241, 250));
        btn.setMaximumSize(new Dimension(110, 25));
        btn.setMinimumSize(new Dimension(110, 25));
    }

    /**
     * lists all the available interfaces.
     * @param devices list of interfaces
     */
    public static void printInterfaceList(NetworkInterface[] devices) {
        for (int i = 1; i < devices.length; i++) {

            log.append("(" + i + ")\nName: " + devices[i].name);
            log.append("\nDescription: " + devices[i].description);
            log.append("\nDatalink name: " + devices[i].datalink_name);
            log.append("\nDatalink desc.: " + devices[i].datalink_description);
            log.append("\nMAC: " + formatMac(devices[i].mac_address));
            log.append("\nIP: " + formatIP(devices[i].addresses));
            log.append("\nSubnet Mask: " + getSubnetMask(devices[i]));
            log.append("\n ________________________________________________________________________\n");
        }
    }
    public static String formatMac(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (byte b: mac) {
            sb.append(String.format("%02X:", b));
        }
        return sb.substring(0, sb.length() - 1).replace("/", "");
    }
    public static String formatIP(NetworkInterfaceAddress[] addr) {
        StringBuilder sb = new StringBuilder();
        for (NetworkInterfaceAddress address: addr) {
            sb.append(address.address).append(" ");
        }
        return sb.toString().replace("/", "");
    }
    public static String getSubnetMask(NetworkInterface dev) {
        NetworkInterfaceAddress[] addresses = dev.addresses;
        if (addresses[0].subnet != null) {
            return addresses[0].subnet.toString();
        } else {
            return "N/A"; // No addresses available
        }
    }
}
