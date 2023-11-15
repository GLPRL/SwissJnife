package Logics;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESData {

    IvParameterSpec iv;
    SecretKey key;

    public IvParameterSpec getIv() {
        return iv;
    }

    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }
}
