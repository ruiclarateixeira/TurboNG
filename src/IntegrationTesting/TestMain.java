package IntegrationTesting;

import TurboNGServer.TurboNGServer;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 */
public class TestMain {
    public static void main(String[] args) {
        TurboNGServer gameServer = new TurboNGServer(8080, 50, true, "13ncldm1p".toCharArray(), "/Users/ruijorgeclarateixeira/Development/TurboNGServer/turbong.keys");
        gameServer.start(new TestPlayerFactory());
    }
}
