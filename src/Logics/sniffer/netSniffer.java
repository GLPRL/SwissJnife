package Logics.sniffer;

import GUI.sharedUtils;
import jpcap.*;

import javax.swing.*;

public class netSniffer {
    NetworkInterface[] interfaces;
    NetworkInterface networkInterface;
    public netSniffer() {

    }

    public NetworkInterface[] getInterfaces() {
        return interfaces;
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(int index) {
        networkInterface = interfaces[index];
    }

    public NetworkInterface[] listInterfaces() {
        interfaces =  JpcapCaptor.getDeviceList();
        return interfaces;
    }
    public void listen(JTextArea log) {
        log.append("Started Listening...");
    }
    public String getNetworkInterfaceInfo() {
        return "Listening on: \n" + networkInterface.description + "\n" +
                "IP: " + sharedUtils.formatIP(networkInterface.addresses) + "\nMAC: " +
                sharedUtils.formatMac(networkInterface.mac_address);
    }
}
