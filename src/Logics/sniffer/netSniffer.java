package Logics.sniffer;

import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.IpNumber;

import javax.swing.*;
import java.io.EOFException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Sniffing class. Receives and prints packet information to log.
 */
public class netSniffer {
    List<PcapNetworkInterface> interfaces;
    PcapNetworkInterface networkInterface;

    public netSniffer() {
        //
    }

    /**
     * Getting all available network devices.
     * @return network device list.
     */
    public List<PcapNetworkInterface> getInterfaces() {
        try {
            if (interfaces == null) {
                interfaces = Pcaps.findAllDevs();
            }
            return interfaces;
        } catch (PcapNativeException e) {
            e.getStackTrace();
            return null;
        }
    }

    /**
     * Setting network interface to listen on.
     * @param index of interface device from interfaces list.
     */
    public void setNetworkInterface(int index) {
        networkInterface = interfaces.get(index);
    }

    /**
     * Prints network interface device to information on top of the screen, when set.
     * @return network interface information
     */
    public String getNetworkInterfaceInfo() {
        InetAddress ipv4 = null, ipv6 = null;
        for (PcapAddress addr : networkInterface.getAddresses()) {
            if (addr.getAddress() instanceof Inet4Address) {
                ipv4 = addr.getAddress();
            }
            if (addr.getAddress() instanceof Inet6Address) {
                ipv6 = addr.getAddress();
            }
        }
        return networkInterface.getName() + "\n" + networkInterface.getDescription() + "\n"
                + ipv4 + "\n" + ipv6;
    }

    /**
     * Runner in thread.
     * Receives and prints un/filtered ports to log.
     * @param log information display
     * @param ports if filtered, listen to list of ports.
     * @param filterPorts filter / all ports
     */
    public void listen(JTextArea log, List<Integer> ports, boolean filterPorts) {
        IpV4Packet ipV4Packet;
        if (!filterPorts) {             //No filtering, listening to all ports
            try {
                PcapHandle handle = networkInterface.openLive(65536,
                        PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 100000);
                while(true) {
                    Packet packet = handle.getNextPacketEx();

                    ipV4Packet = packet.get(IpV4Packet.class);
                    if (ipV4Packet != null) {
                        IpV4Packet finalIpV4Packet = ipV4Packet;
                        SwingUtilities.invokeLater(() -> {
                            try {
                                printData(finalIpV4Packet, log, packet);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                    if (Thread.interrupted()) {
                        break;
                    }
                }

            } catch (PcapNativeException | NotOpenException | TimeoutException | EOFException e) {
                log.append(Arrays.toString(e.getStackTrace()));
            }

        } else {                        //Filtering is active, listening to selected ports
            try {
                PcapHandle handle = networkInterface.openLive(65536,
                        PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 100000);
                while (true) {
                    Packet packet = handle.getNextPacketEx();
                    ipV4Packet = packet.get(IpV4Packet.class);
                    if (ipV4Packet != null) {
                        if (ipV4Packet.getPayload() != null) {
                            if (ipV4Packet.getPayload() instanceof TcpPacket) {             //is TCP packet
                                if (checkPort(ports, ipV4Packet.getPayload(), true)) {
                                    IpV4Packet finalIpV4Packet = ipV4Packet;
                                    SwingUtilities.invokeLater(() -> {
                                        try {
                                            printData(finalIpV4Packet, log, packet);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                }
                            } else if (ipV4Packet.getPayload() instanceof UdpPacket) {      //is UDP packet
                                if (checkPort(ports, ipV4Packet.getPayload(), false)) {
                                    IpV4Packet finalIpV4Packet = ipV4Packet;
                                    SwingUtilities.invokeLater(() -> {
                                        try {
                                            printData(finalIpV4Packet, log, packet);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                                }
                            }
                        }
                    }
                    if (Thread.interrupted()) {
                        break;
                    }
                }

            } catch (PcapNativeException | NotOpenException | TimeoutException | EOFException e) {
                log.append(Arrays.toString(e.getStackTrace()));
            }

        }
    }

    /**
     * If packet is UDP/TCP
     * @param ports ports list.
     * @param payload packet payload.
     * @param isTCP is TCP/UDP
     * @return if contains the src/dst port, for filtering.
     */
    private boolean checkPort(List<Integer> ports, Packet payload, boolean isTCP) {
        int srcPort;
        int dstPort;
        if (isTCP) {
            srcPort = ((TcpPacket) payload).getHeader().getSrcPort().valueAsInt();
            dstPort = ((TcpPacket) payload).getHeader().getDstPort().valueAsInt();
        } else {
            srcPort = ((UdpPacket) payload).getHeader().getSrcPort().valueAsInt();
            dstPort = ((UdpPacket) payload).getHeader().getDstPort().valueAsInt();
        }
        return ports.contains(srcPort) || ports.contains(dstPort);
    }

    /**
     * Prints the information about packet received.
     * @param ipV4Packet packet received
     * @param log print location
     * @param packet packet derived from ipv4packet
     */
    private void printData(IpV4Packet ipV4Packet, JTextArea log, Packet packet) throws InterruptedException {
        String portString = "N/A";
        String ttl;

        ttl = String.valueOf(ipV4Packet.getHeader().getTtl());
        Packet payload = ipV4Packet.getPayload();
        if (payload != null) {
            if (payload instanceof TcpPacket) {
                int srcPort = getSrcPort(payload, true);
                int destPort = getDstPort(payload, true);
                portString = "\nSrcPort: " + srcPort + ", DstPort: " + destPort;
            } else if (payload instanceof UdpPacket) {
                int srcPort = getSrcPort(payload, false);
                int destPort = getDstPort(payload, false);
                portString = "\nSrcPort: " + srcPort + ", DstPort: " + destPort;
            }
            log.append("Source: " + getSrcAddr(ipV4Packet) + " -> Destination: " + getDstAdd(ipV4Packet));
            log.append("\nProtocol " + getProtocol(ipV4Packet) + portString + " | TTL: " + ttl + "\n");
            if (packet.contains(EthernetPacket.class)) {
                log.append("Src MAC addr: " + getSrcMAC(packet) + " -> Dst MAC addr: " + getDstMAC(packet) + "\n");
            }
            log.append("_______________________________________________________________________\n");
        }
    }

    /**
     * Get SrcMAC and return information.
     * @param packet packet received
     * @return MAC addr
     */
    private String getSrcMAC(Packet packet) {
        return String.valueOf(packet.get(EthernetPacket.class).getHeader().getSrcAddr());
    }

    /**
     * Get DstMAC and return information.
     * @param packet packet received
     * @return MAC addr
     */
    private String getDstMAC(Packet packet) {
        return String.valueOf(packet.get(EthernetPacket.class).getHeader().getDstAddr());
    }

    /**
     * Get SrcPort and return information.
     * @param payload packet payload
     * @param isTCP is TCP/UDP packet
     * @return Port num
     */
    private int getSrcPort(Packet payload, boolean isTCP) {
        if (isTCP) {
            return ((TcpPacket) payload).getHeader().getSrcPort().valueAsInt();
        } else {
            return ((UdpPacket) payload).getHeader().getSrcPort().valueAsInt();
        }
    }

    /**
     * Get DstPort and return information.
     * @param payload packet payload
     * @param isTCP is TCP/UDP packet
     * @return Port num
     */
    private int getDstPort(Packet payload, boolean isTCP) {
        if (isTCP) {
            return ((TcpPacket) payload).getHeader().getDstPort().valueAsInt();
        } else {
            return ((UdpPacket) payload).getHeader().getDstPort().valueAsInt();
        }
    }

    /**
     * Get SrcIP address
     * @param ipV4Packet packet
     * @return information of IP address
     */
    private String getSrcAddr(IpV4Packet ipV4Packet) {
        return ipV4Packet.getHeader().getSrcAddr().toString().replace("/", "");
    }

    /**
     * Get DstIP address
     * @param ipV4Packet packet
     * @return information of IP address
     */
    private String getDstAdd(IpV4Packet ipV4Packet) {
        return ipV4Packet.getHeader().getDstAddr().toString().replace("/", "");
    }

    /**
     * Get packet's protocol
     * @param ipV4Packet packet
     * @return protocol information
     */
    private IpNumber getProtocol(IpV4Packet ipV4Packet) {
        return ipV4Packet.getHeader().getProtocol();
    }
}
