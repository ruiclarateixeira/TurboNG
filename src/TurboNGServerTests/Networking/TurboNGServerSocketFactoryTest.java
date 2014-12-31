package TurboNGServerTests.Networking;

import TurboNGServer.Networking.TurboNGServerSocketFactory;
import junit.framework.TestCase;

import javax.net.ssl.SSLServerSocket;
import java.net.ServerSocket;

public class TurboNGServerSocketFactoryTest extends TestCase {

    public void testCreateNGServerSocket_AuthenticationTrue() throws Exception {
        //ServerSocket socket =  TurboNGServerSocketFactory.createNGServerSocket(0, true);
        //assertTrue(socket instanceof SSLServerSocket);
    }

    public void testCreateNGServerSocket_AuthenticationFalse() throws Exception {
        //ServerSocket socket =  TurboNGServerSocketFactory.createNGServerSocket(0, false);
        //assertFalse(socket instanceof SSLServerSocket);
    }
}