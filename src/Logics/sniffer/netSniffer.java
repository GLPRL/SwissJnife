package Logics.sniffer;

import jpcap.*;

public class netSniffer {
    NetworkInterface[] interfaces;
    NetworkInterface networkInterface;
    public netSniffer() {

    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public NetworkInterface[] listInterfaces() {
        interfaces =  JpcapCaptor.getDeviceList();
        return interfaces;
    }

}
