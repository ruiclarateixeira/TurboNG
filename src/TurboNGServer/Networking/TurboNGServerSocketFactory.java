package TurboNGServer.Networking;

import TurboNGServer.ServerSettings.ServerVariables;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 *
 */
public class TurboNGServerSocketFactory {
    private final static String algorithm = "SSL";
    /**
     * Creates a SSLServerSocket
     * @param port - Port in which the socket should listen
     * @param authenticationRequired - If true the socket will only allow cipher suites with authentication
     * @return - Returns the created socket or null if a problem is found
     */
    public static SSLServerSocket createNGServerSocket(int port, boolean authenticationRequired) {
        SSLServerSocket newSocket = null;
        SSLServerSocketFactory factory;
        try {
            if (authenticationRequired) {
                SSLContext context = SSLContext.getInstance(algorithm);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");
                char[] password = "13ncldm1p".toCharArray(); // TESTING ONLY
                ks.load(new FileInputStream(ServerVariables.PUBLIC_KEYS_PATH), password);
                kmf.init(ks, password);
                context.init(kmf.getKeyManagers(), null, null);

                Arrays.fill(password, '0'); // Wipe the password

                factory = context.getServerSocketFactory();
                newSocket =  (SSLServerSocket) factory.createServerSocket(port);
            }
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException | KeyStoreException | CertificateException e) {
            e.printStackTrace();
        } finally {
            return newSocket;
        }
    }


}
