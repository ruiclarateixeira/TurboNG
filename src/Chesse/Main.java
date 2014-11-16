package Chesse;

import TurboNGServer.TurboNGServer;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 */
public class Main {
    public static void main(String[] args) {
        TurboNGServer gameServer = TurboNGServer.createInstance(8080, 50, false);
        gameServer.start();
    }
}
