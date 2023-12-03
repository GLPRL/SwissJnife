package GUI.sniffer;

import GUI.mainGUI;
import GUI.sharedUtils;
import Logics.sniffer.netSniffer;
import org.pcap4j.core.PcapAddress;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI for network analyzer.
 */
public class netSnifferGUI {
    static boolean ALL_PORTS = false;
    static boolean FILTER_PORTS = true;
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
    static JButton clearBtn;
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
    static List<Integer> ports = new ArrayList<>();

    /**
     * Constructor
     * @param frame programs' window.
     */
    public netSnifferGUI(JFrame frame) {
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
        setToggleListeners();
    }

    /**
     * Activated by setToggleListeners.
     * @param btn button to add listener
     * @param port port
     */
    private void setToggleListener(JToggleButton btn, int port) {
        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                ports.add(port);
            } else {
                ports.remove(Integer.valueOf(port));
            }
        });
    }

    /**
     * If button was selected, then add it's port value to ports list.
     * Doing it for all buttons
     */
    public void setToggleListeners() {
        setToggleListener(FTPrad, 20);
        setToggleListener(FTPrad, 21);
        setToggleListener(SSHrad, 22);
        setToggleListener(TELNETrad, 23);
        setToggleListener(SMTPrad, 25);
        setToggleListener(DNSrad, 53);
        setToggleListener(HTTPrad, 80);
        setToggleListener(POP3rad, 110);
        setToggleListener(IMAPrad, 143);
        setToggleListener(SNMPrad, 161);
        setToggleListener(LDAPrad, 389);
        setToggleListener(HTTPSrad, 443);
        setToggleListener(RDPrad, 3389);
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
     * Sets locking status for the buttons
     * @param b is locked
     */
    public static void setBtnLock(boolean b) {
        setMiscBtnLock(b);
        setPortLock(b);
    }

    /**
     * Setting lock status for misc buttons
     *
     * @param b true/false for locking status
     */
    public static void setMiscBtnLock(boolean b) {
        filterPorts.setEnabled(b);
        allPorts.setEnabled(b);
        customPort.setEnabled(b);
        addPortBtn.setEnabled(b);
        removePortBtn.setEnabled(b);
        startBtn.setEnabled(b);
    }

    /**
     * Setting lock status for port filtering buttons
     *
     * @param b true/false for locking status
     */
    public static void setPortLock(boolean b) {
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
    }

    /**
     * Creates the port filtering options control panel
     */
    public void setPortsPanel() {
        Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
        rigidArea.setBackground(sharedUtils.x3189);
        portsPanel.setBorder(sharedUtils.getTitledBorder("Port Filter"));
        portsPanel.setBackground(Color.WHITE);


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
     *
     * @param btnOne   first button
     * @param btnTwo   second button
     * @param btnThree third button
     * @param btnFour  fourth button
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
     *
     * @return new panel
     */
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
    private static JPanel customPortPanel() {
        JPanel misc = new JPanel();
        misc.setLayout(new BoxLayout(misc, BoxLayout.X_AXIS));
        misc.setBackground(Color.WHITE);

        JLabel customPortLabel = new JLabel("Custom Port: ");
        customPortLabel.setFont(sharedUtils.TAHOMA_BOLD_12);
        misc.add(customPortLabel);

        customPort = new JTextField();
        customPort.setFont(sharedUtils.TAHOMA_PLAIN_11);
        customPort.setEnabled(false);
        customPort.setBackground(Color.WHITE);
        customPort.setBorder(new EtchedBorder());
        customPort.setMaximumSize(new Dimension(80, 22));
        customPort.setMinimumSize(new Dimension(80, 22));
        customPort.setHorizontalAlignment(JTextField.CENTER);
        customPort.setDocument(sharedUtils.numericOnly2);
        misc.add(customPort);

        addPortBtn = new JButton();
        sharedUtils.setSnifferBtn(addPortBtn, "Listen", Color.GREEN);
        addPortBtn.addActionListener(e -> {
            if (customPort.getText() != "") {
                int temp = Integer.parseInt(customPort.getText());
                if (temp >= 0 && temp <= 65535) {
                    assignPort(Integer.parseInt(customPort.getText()), true);
                }
            }
            customPort.setText("");
        });
        misc.add(Box.createRigidArea(new Dimension(5, 0)));
        misc.add(addPortBtn);

        removePortBtn = new JButton();
        sharedUtils.setSnifferBtn(removePortBtn, "Delete", Color.RED);
        removePortBtn.addActionListener(e -> {
            if (customPort.getText() != "") {
                assignPort(Integer.parseInt(customPort.getText()), false);
            }
            customPort.setText("");
        });
        removePortBtn.setBackground(Color.RED);
        misc.add(Box.createRigidArea(new Dimension(5, 0)));
        misc.add(removePortBtn);

        return misc;
    }

    /**
     * Creates general controls panel for the analyzer.
     */
    public void setButtonsPanel() {
        buttonsPanel.setBackground(Color.WHITE);

        buttonsPanel.setBorder(sharedUtils.getTitledBorder("Controls"));
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

        startBtn = new JButton();
        sharedUtils.setSnifferBtn(startBtn, "Start", Color.GREEN);
        panel.add(startBtn);
        startBtn.addActionListener(e -> {
            startButtonListener();
        });

        clearBtn = new JButton();
        sharedUtils.setSnifferBtn(clearBtn, "Clear", sharedUtils.normal);
        clearBtn.addActionListener(e -> log.setText(""));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(clearBtn);

        exitBtn = new JButton();
        sharedUtils.setSnifferBtn(exitBtn, "Exit", Color.RED);
        exitBtn.addActionListener(e -> {
            frame.remove(scrollPane);
            if (snifferThread != null) {
                snifferThread.interrupt();
            }
            sharedUtils.clearScreen(mainPanel);
            log.setText("");
            interfaceText.setText("");
            customPort.setText("");
            gui.presentGUI();
        });
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(exitBtn);

        return panel;
    }

    /**
     * Create the listener for start button.
     */
    private static void startButtonListener() {
        if (startBtn.getText().equals("Start")) {
            if (ports.isEmpty()) {
                allPorts.setSelected(true);
                status.setText("Disabled (All Ports)");
            }
            setPortLock(false);
            addPortBtn.setEnabled(false);
            removePortBtn.setEnabled(false);
            customPort.setEnabled(false);
            interfaceText.setEnabled(false);
            filterPorts.setEnabled(false);
            allPorts.setEnabled(false);
            interfaceList.setEnabled(false);
            interfaceSet.setEnabled(false);
            log.setText("");
            if (!filterPorts.isSelected() && !allPorts.isSelected()) {
                if (ports.isEmpty()) {
                    allPorts.setSelected(true);
                    filterPorts.setSelected(false);
                    status.setText("Disabled (All Ports)");
                    log.append("Listening on all ports\n");
                    snifferThread = new Thread(() -> ns.listen(log, ports, ALL_PORTS));

                } else {
                    allPorts.setSelected(false);
                    filterPorts.setSelected(true);
                    status.setText("Enabled (Using Filter)");
                    log.append("Listening on:\n");
                    logPorts(log, ports);
                    snifferThread = new Thread(() -> ns.listen(log, ports, FILTER_PORTS));
                }
            } else if (allPorts.isSelected()) {
                log.append("Listening on all ports\n");
                snifferThread = new Thread(() -> ns.listen(log, ports, ALL_PORTS));
            } else if (filterPorts.isSelected()) {
                if (ports.isEmpty()) {
                    log.append("No ports selected for filtering");
                } else {
                    status.setText("Enabled (Using Filter)");
                    log.append("Listening on:\n");
                    logPorts(log, ports);
                    snifferThread = new Thread(() -> ns.listen(log, ports, FILTER_PORTS));
                }
            }
            snifferThread.start();
            startBtn.setBackground(Color.YELLOW);
            startBtn.setText("Stop");

        } else {
            interfaceText.setEnabled(true);
            setBtnLock(true);
            setPortLock(true);
            addPortBtn.setEnabled(true);
            removePortBtn.setEnabled(true);
            customPort.setEnabled(true);
            interfaceList.setEnabled(true);
            interfaceSet.setEnabled(true);
            startBtn.setEnabled(false);
            status.setText("Status");
            startBtn.setBackground(Color.GREEN);
            interfaceInfo.setText("");
            startBtn.setText("Start");
            snifferThread.interrupt();
        }
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
        radioPanel.setBorder(new EtchedBorder());
        ButtonGroup bg = new ButtonGroup();

        allPorts = createFilteringBtn("Disable", "Disabled (All Ports)");
        filterPorts = createFilteringBtn("Enable", "Enabled (Using Filter)");

        bg.add(allPorts);
        bg.add(filterPorts);
        radioPanel.add(allPorts);
        radioPanel.add(filterPorts);

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
    private static JRadioButton createFilteringBtn(String name, String desc) {
        JRadioButton btn = new JRadioButton(name);
        btn.setBackground(Color.WHITE);
        sharedUtils.noFocusBorder(btn);
        btn.addActionListener(e -> status.setText(desc));
        return btn;
    }
    /**
     * Interface selection, settings and listing
     *
     * @return panel contains all of the above
     */
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
        interfaceText.setFont(sharedUtils.TAHOMA_PLAIN_11);
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
            printInterfaceList();
            ns.getInterfaces();
            interfaceSet.setEnabled(true);
            interfaceText.setEnabled(true);
        });
        sharedUtils.setGeneralButton(interfaceList);
        interfaceList.setFont(sharedUtils.TAHOMA_BOLD_11);
        interfaceList.setHorizontalAlignment(SwingConstants.CENTER);
        interfaceList.setAlignmentX(Component.CENTER_ALIGNMENT);
        interfaceList.setMaximumSize(new Dimension(70, 25));
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
                if (interfaceID >= ns.getInterfaces().size() || interfaceID < 0) {
                    interfaceInfo.setText("Wrong interface ID");
                } else {
                    ns.setNetworkInterface(interfaceID);
                    interfaceInfo.setText("");
                    interfaceInfo.setText(ns.getNetworkInterfaceInfo().replace("/", ""));
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
     * Setting visual attributes to a toggle button
     *
     * @param btn toggle button to set
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
     */
    public static void printInterfaceList() {
        log.setText("");
        if (ns.getInterfaces() == null) {
            log.append("Could not load interface list\n");
            return;
        }
        for (int i = 0; i < ns.getInterfaces().size(); i++) {
            log.append("(" + i + ")\n");
            log.append(ns.getInterfaces().get(i).getName() + "\n");
            log.append(ns.getInterfaces().get(i).getDescription() + "\n");
            List<PcapAddress> addresses = ns.getInterfaces().get(i).getAddresses();
            for (PcapAddress addr: addresses) {
                if (addr.getAddress() instanceof Inet4Address) {
                    log.append("IPv4: " + addr.getAddress().toString().replace("/", "") + "\n");
                    log.append("Subnet Mask: " + addr.getNetmask() + "\n");
                }
                if (addr.getAddress() instanceof Inet6Address) {
                    log.append("IPv6: " + addr.getAddress().toString().replace("/", "") + "\n");
                    log.append("Subnet Mask: " + addr.getNetmask() + "\n");
                }
            }

            log.append("\n ________________________________________________________________________\n");
        }
    }

    /**
     * If clicked remove/listen from custom ports text field, then activate.
     * @param b true -> add port to list. false -> remove port from list
     * @param portNum port number
     * @param btn button associated to port
     */
    public static void controlPorts(boolean b, int portNum, JToggleButton btn) {
        btn.setSelected(b);
        if (b && !ports.contains(portNum)) {
            ports.add(portNum);
        }
        if (!b && ports.contains(portNum)) {
            ports.remove(Integer.valueOf(portNum));
        }
    }
    /**
     * Assigns port to listen
     * @param portNum port
     */
    public static void assignPort(int portNum, boolean b) {
        switch (portNum) {
            case 20, 21: {
                controlPorts(b, 20, FTPrad);
                controlPorts(b, 21, FTPrad);
                break;
            }
            case 22: {
                controlPorts(b, 22, SSHrad);
                break;
            }
            case 23: {
                controlPorts(b, 23, TELNETrad);
                break;
            }
            case 25: {
                controlPorts(b, 25, SMTPrad);
                break;
            }
            case 53: {
                controlPorts(b, 53, DNSrad);
                break;
            }
            case 80: {
                controlPorts(b, 80, HTTPrad);
                break;
            }
            case 110: {
                controlPorts(b, 110, POP3rad);
                break;
            }
            case 143: {
                controlPorts(b, 143, IMAPrad);
                break;
            }
            case 161: {
                controlPorts(b, 161, SNMPrad);
                break;
            }
            case 389: {
                controlPorts(b, 389, LDAPrad);
                break;
            }
            case 443: {
                controlPorts(b, 443, HTTPSrad);
                break;
            }
            case 3389: {
                controlPorts(b, 3389, RDPrad);
                break;
            }
            default: {
                if (b && !ports.contains(portNum)) {
                    ports.add(portNum);
                } else if(!b && ports.contains(portNum)) {
                    ports.remove(Integer.valueOf(portNum));
                }
            }
        }
    }

    /**
     * Write ports to log
     * @param log destination
     * @param ports list
     */
    private static void logPorts(JTextArea log, List<Integer> ports) {
        for (int i = 0; i < ports.size(); i++) {
            if (i < ports.size() - 1) {
                log.append(ports.get(i) + ", ");
            } else {
                log.append(ports.get(i) + "\n");
            }
        }
        log.append("=======================================================================\n");
    }
}