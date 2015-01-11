package TurboNGServer.Networking;

import TurboNGServer.ServerSettings.Settings;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.security.*;
import java.security.cert.CertificateException;

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
     * @param sslPassword Password for the ssl keystore. Null if no SSL connection required.
     * @return - Returns the created socket or null if a problem is found
     */
    public static ServerSocket createNGServerSocket(char[] sslPassword) throws IOException {
        SSLServerSocketFactory factory;
        try {
            if (Settings.SSL) {
                if(Settings.SSLKeysPath == null) {
                    System.err.println("Provide a path to the SSL keystore!");
                    return null;
                }
                else if(sslPassword == null) {
                    System.err.println("Provide keystore password!");
                    return null;
                }

                SSLContext context = SSLContext.getInstance(algorithm);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream(Settings.SSLKeysPath), sslPassword);
                kmf.init(ks, sslPassword);
                context.init(kmf.getKeyManagers(), null, null);

                factory = context.getServerSocketFactory();
                return factory.createServerSocket(Settings.ListeningPort);
            }
            else {
                return new ServerSocket(Settings.ListeningPort);
            }
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException | KeyStoreException | CertificateException e) {
            e.printStackTrace();
        }

        return null;
    }
}
