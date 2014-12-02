package TurboNGServerTests.Interface;

import TurboNGServer.Player.PlayerLobby;
import TurboNGServerTests.DependencyInjection.LobbyInterfaceDITest;
import TurboNGServerTests.DependencyInjection.TestPlayer;
import dagger.ObjectGraph;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PlayerLobbyTest extends TestCase {
    PlayerLobby playerLobby;
    BufferedReader reader;
    BufferedWriter writer;

    public void setUp() throws Exception {
        super.setUp();
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        ObjectGraph objectGraph;
        objectGraph = ObjectGraph.create(new LobbyInterfaceDITest());
        playerLobby = objectGraph.get(PlayerLobby.class);
    }

    public void testPlayerInjection_Correct() throws Exception {
        assertTrue(playerLobby.getPlayer() instanceof TestPlayer);
    }

    public void testSetReader_Correct() throws Exception {
        assertEquals(playerLobby.bufReader, reader);
    }

    public void testSetReader_Null() throws Exception {
    }

    public void testSetWriter_Correct() throws Exception {
        assertEquals(playerLobby.bufWriter, writer);
    }

    public void testSetWriter_Null() throws Exception {
    }

    public void testCall() throws Exception {
        playerLobby.call();
    }

    public void testSendToClient() throws Exception {

    }
}