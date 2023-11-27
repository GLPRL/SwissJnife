package Logics.sniffer;

import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import javax.swing.*;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;


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
        InetAddress ipv4 = null,  ipv6 = null;
        for (PcapAddress addr: networkInterface.getAddresses()) {
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
    public void listen(JTextArea log) {

    }
}
