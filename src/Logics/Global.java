package Logics;

public class Global {
    private static Global instance;
    public AESData aesData = new AESData();

    public static Global getInstance() {
        if (instance == null) {
            synchronized (Global.class) {
                if (instance == null) {
                    instance = new Global();
                }
            }
        }
        return instance;
    }
}
