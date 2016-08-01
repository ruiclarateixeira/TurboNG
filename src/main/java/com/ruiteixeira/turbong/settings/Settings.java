package com.ruiteixeira.turbong.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {
    @JsonProperty
    private boolean sslConnection;
    @JsonProperty
    private int numberOfThreads;
    @JsonProperty
    private int listeningPort;
    @JsonProperty
    private String sslKeysPath;

    public boolean isSslConnection() {
        return sslConnection;
    }

    public void setSslConnection(boolean sslConnection) {
        this.sslConnection = sslConnection;
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
