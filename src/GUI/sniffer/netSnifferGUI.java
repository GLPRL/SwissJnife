package GUI.sniffer;

import GUI.mainGUI;
import GUI.sharedUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
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
    JRadioButton SNMPrad = new JRadioButton("SNMP (161");
    JRadioButton LDAPrad = new JRadioButton("LDAP (389");
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
        normalPorts = new JPanel();
        this.frame = frame;

        frame.setTitle("SwissJnife - Network Traffic");

        this.frame.setSize(860, 650);
        this.frame.setLocation(sharedUtils.centerFrame(this.frame));
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
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
    public void addPanels() {

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

        setPortsPanel();
        controlPanel.add(portsPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(5,0)));
        setButtonsPanel();
        controlPanel.add(buttonsPanel);
    }
    public void setPortsPanel() {
        portsPanel.setBackground(new Color(189, 189, 189));
        portsPanel.setBorder(BorderFactory.createEtchedBorder());
        JPanel portCol1 = new JPanel();
        portCol1.setLayout(new BoxLayout(portCol1, BoxLayout.Y_AXIS));
        portCol1.setBackground(new Color(189, 189, 189));
        portSetup(FTPrad);
        portSetup(SSHrad);
        portSetup(TELNETrad);
        portSetup(SMTPrad);
        portCol1.add(FTPrad);
        portCol1.add(SSHrad);
        portCol1.add(TELNETrad);
        portCol1.add(SMTPrad);
        normalPorts.add(portCol1);

        JPanel portCol2 = new JPanel();
        portCol2.setLayout(new BoxLayout(portCol2, BoxLayout.Y_AXIS));
        portCol2.setBackground(new Color(189, 189, 189));
        portSetup(DNSrad);
        portSetup(HTTPrad);
        portSetup(POP3rad);
        portSetup(IMAPrad);
        portCol2.add(DNSrad);
        portCol2.add(HTTPrad);
        portCol2.add(POP3rad);
        portCol2.add(IMAPrad);
        normalPorts.add(portCol2);

        JPanel portCol3 = new JPanel();
        portCol3.setLayout(new BoxLayout(portCol3, BoxLayout.Y_AXIS));
        portCol3.setBackground(new Color(189, 189, 189));
        portSetup(SNMPrad);
        portSetup(LDAPrad);
        portSetup(HTTPSrad);
        portSetup(RDPrad);
        portCol3.add(SNMPrad);
        portCol3.add(LDAPrad);
        portCol3.add(HTTPSrad);
        portCol3.add(RDPrad);
        normalPorts.add(portCol3);

        portsPanel.add(normalPorts);
    }
    public void setButtonsPanel() {
        buttonsPanel.setBackground(new Color(189, 189, 189));
        buttonsPanel.setBorder(BorderFactory.createEtchedBorder());
        buttonsPanel.setPreferredSize(new Dimension(300, 165));
        normalPorts = new JPanel();
        normalPorts.setBackground(new Color(189, 189, 189));
        normalPorts.setLayout(new BoxLayout(normalPorts, BoxLayout.X_AXIS));
    }
    public void portSetup(JRadioButton btn) {
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btn.setBackground(new Color(189, 189, 189));
        btn.setSize(90, 25);
        sharedUtils.noFocusBorder(btn);
        ports.add(btn);
    }
}
