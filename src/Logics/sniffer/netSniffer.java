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
        Inet4Address srcAddr, destAddr;
        if (!filterPorts) {             //No filtering, listening to all ports
            int snapLen = 65536;
            PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
            int timeout = 100000;
            try {

                PcapHandle handle = networkInterface.openLive(snapLen, mode, timeout);
                while(true) {
                    Packet packet = handle.getNextPacketEx();
                    ipV4Packet = packet.get(IpV4Packet.class);
                    if (ipV4Packet != null) {
                        srcAddr = ipV4Packet.getHeader().getSrcAddr();

                        destAddr = ipV4Packet.getHeader().getDstAddr();
                        protocol = ipV4Packet.getHeader().getProtocol();
                        Packet payload = ipV4Packet.getPayload();
                        if (payload != null) {
                            if (payload instanceof TcpPacket) {
                                TcpPacket tcpPacket = (TcpPacket) payload;
                                int srcPort = tcpPacket.getHeader().getSrcPort().valueAsInt();
                                int destPort = tcpPacket.getHeader().getDstPort().valueAsInt();
                                portString = "SrcPort: " + srcPort + ", DstPort: " + destPort;
                            } else if (payload instanceof UdpPacket) {
                                UdpPacket udpPacket = (UdpPacket) payload;
                                int srcPort = udpPacket.getHeader().getSrcPort().valueAsInt();
                                int destPort = udpPacket.getHeader().getDstPort().valueAsInt();
                                portString = "SrcPort: " + srcPort + ", DstPort: " + destPort;
                            }
                            log.append("Source: " + srcAddr.toString().replace("/", ""));
                            log.append(" -> Destination: " + destAddr.toString().replace("/", "") + "\n");
                            log.append("Protocol " + protocol + " | " + portString + " | ");
                            log.append("TTL: " + ipV4Packet.getHeader().getTtl() + "\n");
                            if (packet.contains(EthernetPacket.class)) {
                                EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                                log.append("Src MAC addr: " + ethernetPacket.getHeader().getSrcAddr() + " -> ");
                                log.append("Dst MAC addr: " + ethernetPacket.getHeader().getDstAddr() + "\n");
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
            try {

            } catch () {

            }
        }
    }
}
