package TurboNGServerTests.Interface;

import DependencyInjection.LobbyDependencyInjector;
import TurboNGServer.Interface.LobbyInterface;
import dagger.ObjectGraph;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LobbyInterfaceTest extends TestCase {
    LobbyInterface lobbyInterface;
    BufferedReader reader;
    BufferedWriter writer;

    public void setUp() throws Exception {
        super.setUp();
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        ObjectGraph objectGraph;
        objectGraph = ObjectGraph.create(new LobbyDependencyInjector());
        lobbyInterface = objectGraph.get(LobbyInterface.class);
        lobbyInterface.setReaderAndWriter(reader, writer);
    }

    public void testSetReaderAndWriter() throws Exception {
        new Thread() {
            lobbyInterface.call();

        }.start();
    }

    public void testCall() throws Exception {

    }

    public void testSendToClient() throws Exception {

    }
}