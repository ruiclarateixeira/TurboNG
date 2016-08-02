package com.ruiteixeira.turbong;

import com.ruiteixeira.turbong.networking.TurboNGServerSocketFactory;
import com.ruiteixeira.turbong.settings.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.ServerSocket;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
public class TurboNGServerTest {
    @Test
    @PrepareForTest({TurboNGServerSocketFactory.class})
    public void InstantiatingServer() throws Exception {
        ServerSocket mockSocket = mock(ServerSocket.class);
        mockStatic(TurboNGServerSocketFactory.class);
        given(TurboNGServerSocketFactory.createNGServerSocket(anyObject(), isNull(char[].class)))
                .willReturn(mockSocket);
        TurboNGServer turboNGServer = new TurboNGServer(new Settings(), null);
        assertThat(turboNGServer, is(not(nullValue())));
    }
}