package Logics.sniffer;

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


    }
}
