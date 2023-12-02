package Logics.sniffer;

import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.IpNumber;

import javax.swing.*;
import java.io.EOFException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
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

    public PcapNetworkInterface getNetworkInterface() {
        return networkInterface;
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
        String portString = "N/A";
        IpV4Packet ipV4Packet;
        IpNumber protocol;
        String srcAddr, destAddr, ttl, MACsrc, MACdst;
        if (!filterPorts) {             //No filtering, listening to all ports
            log.append("Listening on all ports\n");
            try {

                PcapHandle handle = networkInterface.openLive(65536,
                        PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 100000);
                while(true) {
                    Packet packet = handle.getNextPacketEx();
                    ipV4Packet = packet.get(IpV4Packet.class);
                    if (ipV4Packet != null) {
                        srcAddr = getSrcAddr(ipV4Packet);
                        destAddr = getDstAdd(ipV4Packet);
                        protocol = getProtocol(ipV4Packet);
                        ttl = String.valueOf(ipV4Packet.getHeader().getTtl());
                        Packet payload = ipV4Packet.getPayload();
                        if (payload != null) {
                            if (payload instanceof TcpPacket) {
                                int srcPort = getSrcPort(payload, true);
                                int destPort = getDstPort(payload, true);
                                portString = "SrcPort: " + srcPort + ", DstPort: " + destPort;
                            } else if (payload instanceof UdpPacket) {
                                int srcPort = getSrcPort(payload ,false);
                                int destPort = getDstPort(payload, false);
                                portString = "\nSrcPort: " + srcPort + ", DstPort: " + destPort;
                            }
                            log.append("Source: " + srcAddr);
                            log.append(" -> Destination: " + destAddr);
                            log.append("\nProtocol " + protocol + portString + " | ");
                            log.append("TTL: " + ttl + "\n");
                            if (packet.contains(EthernetPacket.class)) {
                                MACsrc = getSrcMAC(packet);
                                MACdst = getDstMAC(packet);
                                log.append("Src MAC addr: " + MACsrc + " -> ");
                                log.append("Dst MAC addr: " + MACdst + "\n");
                            }
                            log.append("_______________________________________________________________________\n");
                        }

                    }
                }

            } catch (PcapNativeException | NotOpenException | TimeoutException | EOFException e) {
                e.printStackTrace();
                log.append("error");
            }


        } else {                        //Filtering is active, listening to selected ports
            logPorts(log, ports);
        }
    }
    private static void logPorts(JTextArea log, List<Integer> ports) {
        log.setText("Listening on: \n");
        for (int i = 0; i < ports.size(); i++) {
            if (i < ports.size() - 1) {
                log.append(ports.get(i) + ", ");
            } else {
                log.append(ports.get(i) + "\n");
            }
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
