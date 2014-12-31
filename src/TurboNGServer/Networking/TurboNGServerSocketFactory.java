package TurboNGServer.Networking;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 *
 */
public class TurboNGServerSocketFactory {
    /**
     * Algorithm used when secure socket is required. Hardcoded as SSL.
     */
    private final static String algorithm = "SSL";

    /**
     * Path to the ssl keys.
     */
    private final static String sslKeysPath = "/Users/ruijorgeclarateixeira/Development/TurboNGServer/sslkeys/turbong.keys";

    /**
     * Creates a SSLServerSocket
     * NOTE: This contains hardcoded data. Needs work.
     * @param port - Port in which the socket should listen
     * @param authenticationRequired - If true the socket will only allow cipher suites with authentication
     * @return - Returns the created socket or null if a problem is found
     */
    public static ServerSocket createNGServerSocket(int port, boolean authenticationRequired) throws IOException {
        SSLServerSocketFactory factory;
        try {
            if (authenticationRequired) {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                char[] password;
                System.out.print("SSL Keys Password:");
                password = System.console().readPassword();
                System.out.println(password);
                SSLContext context = SSLContext.getInstance(algorithm);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream(sslKeysPath), password);
                kmf.init(ks, password);
                context.init(kmf.getKeyManagers(), null, null);

                Arrays.fill(password, '0'); // Wipe the password

                factory = context.getServerSocketFactory();
                return factory.createServerSocket(port);
            }
            else {
                return new ServerSocket(port);
            }
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException | KeyStoreException | CertificateException e) {
            e.printStackTrace();
        }

        return null;
    }
}
