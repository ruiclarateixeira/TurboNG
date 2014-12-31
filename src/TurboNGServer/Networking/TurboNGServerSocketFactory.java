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
     * Creates a SSLServerSocket
     * NOTE: Password is not hidden when input. This needs work.
     * @param port Port in which the socket should listen
     * @param authenticationRequired - If true the socket will only allow cipher suites with authentication
     * @param sslPassword Password to the SSL keys file. Null if no authentication required.
     * @param SSLKeysPath Path to the SSL keys file. Null if no authentication required
     * @return - Returns the created socket or null if a problem is found
     */
    public static ServerSocket createNGServerSocket(int port, boolean authenticationRequired, char[] sslPassword ,String SSLKeysPath) throws IOException {
        SSLServerSocketFactory factory;
        try {
            if (authenticationRequired) {
                if(SSLKeysPath == null) {
                    System.out.println("Provide a path to the SSL keystore!");
                    return null;
                }
                else if(sslPassword == null) {
                    System.out.println("Provide keystore password!");
                    return null;
                }

                SSLContext context = SSLContext.getInstance(algorithm);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream(SSLKeysPath), sslPassword);
                kmf.init(ks, sslPassword);
                context.init(kmf.getKeyManagers(), null, null);

                Arrays.fill(sslPassword, '0'); // Wipe the password

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
