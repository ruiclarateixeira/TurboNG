package com.ruiteixeira.turbong.networking;

import com.ruiteixeira.turbong.settings.Settings;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 *
 */
public class TurboNGServerSocketFactory {
    private static final Logger LOGGER = Logger.getLogger("TurboNGServerSocketFactory");
    /**
     * Algorithm used when secure socket is required. Hardcoded as SSL.
     */
    private final static String SSL_ALGORITHM = "SSL";

    /**
     * Creates a SSLServerSocket
     * NOTE: Password is not hidden when input. This needs work.
     * @param sslPassword Password for the ssl keystore. Null if no SSL connection required.
     * @return - Returns the created socket or null if a problem is found
     */
    public static ServerSocket createNGServerSocket(Settings settings, char[] sslPassword) throws IOException {
        SSLServerSocketFactory factory;
        try {
            if (settings.isSSL()) {
                if(settings.getSslKeysPath() == null || settings.getSslKeysPath().equals("")) {
                    LOGGER.log(Level.SEVERE, "Provide a path to the SSL keystore!");
                    return null;
                }
                else if(sslPassword == null) {
                    LOGGER.log(Level.SEVERE, "Provide keystore password!");
                    return null;
                }

                SSLContext context = SSLContext.getInstance(SSL_ALGORITHM);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");

                try {
                    ks.load(new FileInputStream(settings.getSslKeysPath()), sslPassword);
                } catch (FileNotFoundException e) {
                    LOGGER.log(Level.SEVERE, "Wrong file path! {}", settings.getSslKeysPath());
                }
                kmf.init(ks, sslPassword);
                context.init(kmf.getKeyManagers(), null, null);

                factory = context.getServerSocketFactory();
                return factory.createServerSocket(settings.getListeningPort());
            }
            else {
                return new ServerSocket(settings.getListeningPort());
            }
        } catch (IOException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException | KeyStoreException | CertificateException e) {
            LOGGER.log(Level.SEVERE, "Could not create SSL Socket");
        }

        return null;
    }
}
