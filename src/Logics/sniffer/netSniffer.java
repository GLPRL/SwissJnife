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


public class netSniffer {
    List<PcapNetworkInterface> interfaces;
    PcapNetworkInterface networkInterface;

    public netSniffer() {

    }

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

    public void setNetworkInterface(int index) {
        networkInterface = interfaces.get(index);
    }

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

    public void listen(JTextArea log, List<Integer> ports, boolean filterPorts) {
        IpV4Packet ipV4Packet;
        if (!filterPorts) {             //No filtering, listening to all ports
            try {
                PcapHandle handle = networkInterface.openLive(65536,
                        PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 100000);
                boolean contList = true;
                while (contList) {
                    Packet packet = handle.getNextPacketEx();

                    ipV4Packet = packet.get(IpV4Packet.class);
                    if (ipV4Packet != null) {
                        IpV4Packet finalIpV4Packet = ipV4Packet;
                        SwingUtilities.invokeLater(() -> printData(finalIpV4Packet, log, packet));
                    }
                    if (Thread.interrupted()) {
                        contList = false;
                    }
                }
                log.append("=======================================================================\n");
                log.append("Stopped listening on all ports");

            } catch (PcapNativeException | NotOpenException | TimeoutException | EOFException e) {
                log.append(Arrays.toString(e.getStackTrace()));
            }

        } else {                        //Filtering is active, listening to selected ports
            try {
                PcapHandle handle = networkInterface.openLive(65536,
                        PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 100000);
                boolean contList = true;
                while (contList) {
                    Packet packet = handle.getNextPacketEx();
                    ipV4Packet = packet.get(IpV4Packet.class);
                    if (ipV4Packet != null) {
                        if (ipV4Packet.getPayload() != null) {
                            if (ipV4Packet.getPayload() instanceof TcpPacket) {             //is TCP packet
                                if (checkPort(ports, ipV4Packet.getPayload(), true)) {
                                    IpV4Packet finalIpV4Packet = ipV4Packet;
                                    SwingUtilities.invokeLater(() -> printData(finalIpV4Packet, log, packet));
                                }
                            } else if (ipV4Packet.getPayload() instanceof UdpPacket) {      //is UDP packet
                                if (checkPort(ports, ipV4Packet.getPayload(), false)) {
                                    IpV4Packet finalIpV4Packet = ipV4Packet;
                                    SwingUtilities.invokeLater(() -> printData(finalIpV4Packet, log, packet));
                                }
                            }
                        }
                    }
                    if (Thread.interrupted()) {
                        contList = false;
                    }
                    log.append("=======================================================================\n");
                    log.append("Stopped listening on selected ports");
                }

            } catch (PcapNativeException | NotOpenException | TimeoutException | EOFException e) {
                log.append(Arrays.toString(e.getStackTrace()));
            }

        }
    }

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

    private void printData(IpV4Packet ipV4Packet, JTextArea log, Packet packet) {
        String portString = "N/A";
        IpNumber protocol;
        String srcAddr, destAddr, ttl, MACsrc, MACdst;

        srcAddr = getSrcAddr(ipV4Packet);
        destAddr = getDstAdd(ipV4Packet);
        protocol = getProtocol(ipV4Packet);
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
            log.append("Source: " + srcAddr + " -> Destination: " + destAddr);
            log.append("\nProtocol " + protocol + portString + " | TTL: " + ttl + "\n");
            if (packet.contains(EthernetPacket.class)) {
                MACsrc = getSrcMAC(packet);
                MACdst = getDstMAC(packet);
                log.append("Src MAC addr: " + MACsrc + " -> Dst MAC addr: " + MACdst + "\n");
            }
            log.append("_______________________________________________________________________\n");
        }
    }

    private String getSrcMAC(Packet packet) {
        return String.valueOf(packet.get(EthernetPacket.class).getHeader().getSrcAddr());
    }

    private String getDstMAC(Packet packet) {
        return String.valueOf(packet.get(EthernetPacket.class).getHeader().getDstAddr());
    }

    private int getSrcPort(Packet payload, boolean isTCP) {
        if (isTCP) {
            return ((TcpPacket) payload).getHeader().getSrcPort().valueAsInt();
        } else {
            return ((UdpPacket) payload).getHeader().getSrcPort().valueAsInt();
        }
    }

    private int getDstPort(Packet payload, boolean isTCP) {
        if (isTCP) {
            return ((TcpPacket) payload).getHeader().getDstPort().valueAsInt();
        } else {
            return ((UdpPacket) payload).getHeader().getDstPort().valueAsInt();
        }
    }

    private String getSrcAddr(IpV4Packet ipV4Packet) {
        return ipV4Packet.getHeader().getSrcAddr().toString().replace("/", "");
    }

    private String getDstAdd(IpV4Packet ipV4Packet) {
        return ipV4Packet.getHeader().getDstAddr().toString().replace("/", "");
    }

    private IpNumber getProtocol(IpV4Packet ipV4Packet) {
        return ipV4Packet.getHeader().getProtocol();
    }
}
