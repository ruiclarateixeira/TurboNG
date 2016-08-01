package com.ruiteixeira.turbong.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private boolean valid = false;
    private boolean SSL;
    private int numberOfThreads, listeningPort;
    private String sslKeysPath;

    public Settings(String propertiesPath) {
        try {
            if(propertiesPath == null)
                throw new FileNotFoundException();

            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(propertiesPath);
            properties.load(in);
            listeningPort = Integer.parseInt((String) properties.get("PORT"));
            numberOfThreads = Integer.parseInt((String) properties.get("THREADS"));

            SSL = Boolean.parseBoolean((String)properties.get("SSL"));

            if(SSL)
                sslKeysPath = (String)properties.get("KEYSTORE_PATH");
            in.close();
            valid = true;
        } catch (FileNotFoundException e) {
            System.err.println("File " + propertiesPath + " not found.");
            valid = false;
        } catch (IOException e) {
            e.printStackTrace();
            valid = false;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isSSL() {
        return SSL;
    }

    public void setSSL(boolean SSL) {
        this.SSL = SSL;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort) {
        this.listeningPort = listeningPort;
    }

    public String getSslKeysPath() {
        return sslKeysPath;
    }

    public void setSslKeysPath(String sslKeysPath) {
        this.sslKeysPath = sslKeysPath;
    }
}
