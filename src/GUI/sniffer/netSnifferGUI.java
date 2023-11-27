package GUI.sniffer;

import GUI.mainGUI;
import GUI.sharedUtils;
import Logics.sniffer.netSniffer;
import jpcap.NetworkInterface;
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
    static netSniffer ns;
    static JTextArea log;
    static JScrollPane scrollPane;
    static JPanel mainPanel;
    JPanel controlPanel;
    JPanel portsPanel;
    JPanel normalPorts;
    JPanel buttonsPanel;
    static Thread snifferThread;
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
    static JTextField customPort;
    static JTextField interfaceText;
    static JLabel status;
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
        ns = new netSniffer();
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
        mainPanel.add(controlPanel);

    }

    /**
     * Create the log for displaying the output
     */
    private static void createLog() {
        // Create a JTextArea
        log = new JTextArea(22, 100);
        log.setLineWrap(true);
        log.setEditable(false);
        log.setWrapStyleWord(true);
        log.setFont(sharedUtils.MONO_PLAIN_12);
        log.setForeground(sharedUtils.c00112);
        log.setBorder(BorderFactory.createEtchedBorder());
        log.setCursor(sharedUtils.TEXT_CURSOR);

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
        controlPanel.add(sharedUtils.W5_H0);
        controlPanel.add(buttonsPanel);
        setBtnLock(false);
    }

    /**
     * Sets locking status for listening capabilities
     * @param b is locked
     */
    public static void setBtnLock(Boolean b) {
        customPort.setEnabled(b);
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
        rigidArea.setBackground(sharedUtils.x3189);
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
        customPortLabel.setFont(sharedUtils.TAHOMA_BOLD_12);
        misc.add(customPortLabel);

        customPort = new JTextField();
        customPort.setEnabled(false);
        customPort.setBackground(Color.WHITE);
        customPort.setBorder(new EtchedBorder());
        customPort.setMaximumSize(new Dimension(60, 22));
        customPort.setMinimumSize(new Dimension(60, 22));
        customPort.setHorizontalAlignment(JTextField.CENTER);
        customPort.setDocument(sharedUtils.numericOnly2);
        misc.add(customPort);

        addPortBtn = new JButton("Listen");
        addPortBtn.addActionListener(e -> {
            customPort.setText("");
        });
        addPortBtn.setBackground(Color.GREEN);
        addRemovePort(addPortBtn, misc);

        removePortBtn = new JButton("Delete");
        removePortBtn.addActionListener(e -> {
            customPort.setText("");
        });
        removePortBtn.setBackground(Color.RED);
        addRemovePort(removePortBtn, misc);

        return misc;
    }
    public static void addRemovePort(JButton btn, JPanel panel) {
        btn.setMaximumSize(new Dimension(75, 20));
        btn.setMaximumSize(new Dimension(75, 20));
        btn.setCursor(sharedUtils.HAND_CURSOR);
        btn.setFont(sharedUtils.TAHOMA_BOLD_11);
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
        startBtn.setCursor(sharedUtils.HAND_CURSOR);
        startBtn.setFont(sharedUtils.TAHOMA_BOLD_11);
        startBtn.setBackground(Color.GREEN);
        startBtn.setHorizontalAlignment(SwingConstants.CENTER);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sharedUtils.noFocusBorder(startBtn);
        startBtn.setPreferredSize(new Dimension(75, 20));
        startBtn.setMinimumSize(new Dimension(75, 20));
        startBtn.setMaximumSize(new Dimension(75, 20));
        panel.add(startBtn);
        startBtn.addActionListener(e -> {
            if (startBtn.getText().equals("Start")) {
                if (!allPorts.isSelected() || !filterPorts.isSelected()) {
                    allPorts.setSelected(true);
                    status.setText("Enabled (Using Filter)");
                }
                interfaceText.setEnabled(false);
                log.setText("");
                snifferThread = new Thread(() -> ns.listen(log));
                snifferThread.start();
                startBtn.setBackground(Color.YELLOW);
                startBtn.setText("Stop");
                filterPorts.setEnabled(true);
                allPorts.setEnabled(true);
                FTPrad.setEnabled(true);
                SSHrad.setEnabled(true);
                TELNETrad.setEnabled(true);
                SMTPrad.setEnabled(true);
                DNSrad.setEnabled(true);
                HTTPrad.setEnabled(true);
                POP3rad.setEnabled(true);
                IMAPrad.setEnabled(true);
                SNMPrad.setEnabled(true);
                LDAPrad.setEnabled(true);
                HTTPSrad.setEnabled(true);
                RDPrad.setEnabled(true);
            } else {
                interfaceText.setEnabled(true);
                customPort.setEnabled(false);
                addPortBtn.setEnabled(false);
                removePortBtn.setEnabled(false);
                FTPrad.setEnabled(false);
                SSHrad.setEnabled(false);
                TELNETrad.setEnabled(false);
                SMTPrad.setEnabled(false);
                DNSrad.setEnabled(false);
                HTTPrad.setEnabled(false);
                POP3rad.setEnabled(false);
                IMAPrad.setEnabled(false);
                SNMPrad.setEnabled(false);
                LDAPrad.setEnabled(false);
                HTTPSrad.setEnabled(false);
                RDPrad.setEnabled(false);
                filterPorts.setEnabled(false);
                allPorts.setEnabled(false);
                status.setText("Status");
                startBtn.setBackground(Color.GREEN);
                interfaceInfo.setText("");
                startBtn.setText("Start");
                snifferThread.interrupt();
            }

        });

        exitBtn = new JButton("Exit");
        exitBtn.setCursor(sharedUtils.HAND_CURSOR);
        exitBtn.setFont(sharedUtils.TAHOMA_BOLD_11);
        exitBtn.setSize(startBtn.getSize());
        exitBtn.setBackground(Color.RED);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setMinimumSize(new Dimension(75, 20));
        exitBtn.setMaximumSize(new Dimension(75, 20));
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

        status = new JLabel("Status");
        status.setFont(sharedUtils.TAHOMA_PLAIN_13);
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
        filterPorts.addActionListener(e -> {
            status.setText("Enabled (Using Filter)");
            //TODO
        });
        allPorts.addActionListener(e -> {
            status.setText("Disabled (All Ports)");
            //TODO
        });
        panel.add(radioPanel);

        interfaceInfo = new JTextArea();
        interfaceInfo.setFont(sharedUtils.MONO_PLAIN_11);
        interfaceInfo.setLineWrap(true);
        interfaceInfo.setWrapStyleWord(true);
        interfaceInfo.setCursor(sharedUtils.DEFAULT_CURSOR);
        interfaceInfo.setEditable(false);
        interfaceInfo.setForeground(sharedUtils.c0554);
        interfaceInfo.setBackground(sharedUtils.x3231);
        interfaceInfo.setFont(sharedUtils.MONO_PLAIN_11);
        JScrollPane pane = new JScrollPane(interfaceInfo);
        pane.setPreferredSize(new Dimension(150, 80));
        panel.add(pane);
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
        interfaceLabel.setFont(sharedUtils.TAHOMA_PLAIN_11);
        interfaceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceLabel);

        interfaceText = new JTextField();

        interfaceText.setEnabled(false);
        interfaceText.setDocument(sharedUtils.numericOnly);
        interfaceText.setPreferredSize(new Dimension(40, 25));
        interfaceText.setMaximumSize(new Dimension(40, 25));
        interfaceText.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceText.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceText);

        interfaceList = new JButton("List");
        interfaceList.addActionListener(e -> {
            NetworkInterface[] networkInterfaces = ns.listInterfaces();
            printInterfaceList(networkInterfaces);
            interfaceSet.setEnabled(true);
            interfaceText.setEnabled(true);
        });
        sharedUtils.setGeneralButton(interfaceList);
        interfaceList.setFont(sharedUtils.TAHOMA_BOLD_11);
        interfaceList.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceList.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceList.setMaximumSize(new Dimension(70,25));
        interfaceList.setMinimumSize(new Dimension(70, 25));
        sharedUtils.noFocusBorder(interfaceList);
        interfacePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        interfacePanel.add(interfaceList);

        interfaceSet = new JButton("Set");
        interfaceSet.setEnabled(false);
        interfaceSet.setCursor(sharedUtils.HAND_CURSOR);
        sharedUtils.noFocusBorder(interfaceSet);
        interfaceSet.setBackground(Color.GREEN);
        interfaceSet.setFont(sharedUtils.TAHOMA_BOLD_11);
        interfaceSet.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceSet.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceSet.setMaximumSize(new Dimension(70, 25));
        interfaceSet.setMinimumSize(new Dimension(70, 25));
        interfaceSet.addActionListener(e -> {
            if (!interfaceText.getText().isEmpty()) {
                int interfaceID = Integer.parseInt(interfaceText.getText());
                if (interfaceID >= ns.getInterfaces().length || interfaceID < 0) {
                    interfaceInfo.setText("Wrong interface ID");
                } else {
                    ns.setNetworkInterface(interfaceID);
                    interfaceInfo.setText("");
                    interfaceInfo.setText(ns.getNetworkInterfaceInfo());
                    setBtnLock(true);
                    interfaceText.setEnabled(false);
                }
            } else {
                interfaceInfo.setText("No interface selected");
            }
        });
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
        btn.setCursor(sharedUtils.HAND_CURSOR);
        btn.setFont(sharedUtils.TAHOMA_PLAIN_11);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBackground(sharedUtils.c213241250);
        btn.setMaximumSize(new Dimension(110, 25));
        btn.setMinimumSize(new Dimension(110, 25));
    }

    /**
     * lists all the available interfaces.
     * @param devices list of interfaces
     */
    public static void printInterfaceList(NetworkInterface[] devices) {
        log.setText("");
        for (int i = 0; i < devices.length; i++) {
            log.append("(" + i + ")\nName: " + devices[i].name);
            log.append("\nDescription: " + devices[i].description);
            log.append("\nDatalink name: " + devices[i].datalink_name);
            log.append("\nDatalink desc.: " + devices[i].datalink_description);
            log.append("\nMAC: " + sharedUtils.formatMac(devices[i].mac_address));
            log.append("\nIP: " + sharedUtils.formatIP(devices[i].addresses));
            log.append("\nSubnet Mask: " + sharedUtils.getSubnetMask(devices[i]));
            log.append("\n ________________________________________________________________________\n");
        }
    }

}
