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
import java.util.List;

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
     *
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
    }

    /**
     * Present the sniffers' gui
     *
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
     *
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
     *
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
            assignPort(Integer.parseInt(customPort.getText()), true);
            customPort.setText("");
        });
        misc.add(Box.createRigidArea(new Dimension(5, 0)));
        misc.add(addPortBtn);

        removePortBtn = new JButton();
        sharedUtils.setSnifferBtn(removePortBtn, "Delete", Color.RED);
        removePortBtn.addActionListener(e -> {
            assignPort(Integer.parseInt(customPort.getText()), false);
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
            if (startBtn.getText().equals("Start")) {
                if (!allPorts.isSelected() || !filterPorts.isSelected()) {
                    allPorts.setSelected(true);
                    status.setText("Enabled (Using Filter)");
                }

                log.setText("");
                snifferThread = new Thread(() -> {
                    ns.listen(log);
                });
                snifferThread.start();
                startBtn.setBackground(Color.YELLOW);
                startBtn.setText("Stop");
                interfaceText.setEnabled(false);
                filterPorts.setEnabled(true);
                allPorts.setEnabled(true);
                setPortLock(true);
            } else {
                interfaceText.setEnabled(true);
                setBtnLock(false);
                status.setText("Status");
                startBtn.setBackground(Color.GREEN);
                interfaceInfo.setText("");
                startBtn.setText("Start");
                snifferThread.interrupt();
                log.append("Stopped Listening");
            }

        });

        exitBtn = new JButton();
        sharedUtils.setSnifferBtn(exitBtn, "Exit", Color.RED);
        exitBtn.addActionListener(e -> {
            frame.remove(scrollPane);
            sharedUtils.clearScreen(mainPanel);
            gui.presentGUI();
        });
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
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
            //TODO: List all the interfaces and print to log
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
                //TODO: check for list of interfaces, that if the user entered incorrect ID then give a message.
                if (interfaceID >= ns.getInterfaces().size() || interfaceID < 0) {
                    interfaceInfo.setText("Wrong interface ID");
                } else {
                    //TODO: set the target interface to the ID selected
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
     * Assigns port to listen
     *
     * @param portNum port
     */
    public static void assignPort(int portNum, boolean b) {
        switch (portNum) {
            case 20, 21: {
                FTPrad.setSelected(b);
                break;
            }
            case 22: {
                SSHrad.setSelected(b);
                break;
            }
            case 23: {
                TELNETrad.setSelected(b);
                break;
            }
            case 25: {
                SMTPrad.setSelected(b);
                break;
            }
            case 53: {
                DNSrad.setSelected(b);
                break;
            }
            case 80: {
                HTTPrad.setSelected(b);
                break;
            }
            case 110: {
                POP3rad.setSelected(b);
                break;
            }
            case 143: {
                IMAPrad.setSelected(b);
                break;
            }
            case 161: {
                SNMPrad.setSelected(b);
                break;
            }
            case 389: {
                LDAPrad.setSelected(b);
                break;
            }
            case 443: {
                HTTPSrad.setSelected(b);
                break;
            }
            case 3389: {
                RDPrad.setSelected(b);
                break;
            }
            default: {
                //do nothing
            }
        }
    }
}