package Logics;

import Logics.EncDec.AESData;

public class Global {
    public AESData aesData = new AESData();


    private static final class InstanceHolder {
        private static final Global instance = new Global();
    }

    public static Global getInstance() {
        return InstanceHolder.instance;
    }
}
