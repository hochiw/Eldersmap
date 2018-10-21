package com.example.eldermap.P2PPkg;

// TODO: Then the only remaining package is P2PPkg,
// TODO: SocketClient, One more on P2P Tests.
// TODO: One more DBQuery.(Static)
// TODO: In total 5 more tests to go...
import com.example.eldermap.P2PPkg.ChatActivity;
import com.example.eldermap.P2PPkg.FileEncoder;
import com.example.eldermap.P2PPkg.MsgCoder;
import com.example.eldermap.P2PPkg.MsgItem;
import com.example.eldermap.P2PPkg.SocketClient;

import org.java_websocket.client.WebSocketClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.net.URI;

import static org.junit.Assert.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({URI.class,FileEncoder.class, MsgCoder.class})
public class SocketClientTest {
    private URI address;
    @Mock
    private WebSocketClient ws;
    @Mock
    private ChatActivity ca;

    @InjectMocks
    private SocketClient socketClient;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        address = PowerMockito.mock(URI.class);
        ws = PowerMockito.mock(WebSocketClient.class);
        socketClient = new SocketClient(address, ca);
    }

    @Test
    public void sendFile() throws Exception{
        String path = "Hello";
        int type= 0;
        File file = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
        PowerMockito.mockStatic(FileEncoder.class);
        byte[] data = new byte[] {123};
        PowerMockito.when(FileEncoder.convertFileToByte(file)).
                thenReturn(data);
        socketClient.sendFile(path,type);
    }

    @Test
    public void run() throws Exception{
        String message = "HelloWorld";
        PowerMockito.mockStatic(MsgCoder.class);
        PowerMockito.whenNew(WebSocketClient.class).withAnyArguments().
                thenReturn(ws);
        MsgItem item = PowerMockito.mock(MsgItem.class);
        PowerMockito.when(MsgCoder.decode(message)).thenReturn(item);
        PowerMock.suppress(method(ChatActivity.class, "newMessage",
                MsgItem.class));
        socketClient.run();
    }

    @Test
    public void getInstance() {
        assertEquals(null, socketClient.getInstance());
    }

    @Test
    public void getStatus() {
        boolean expected = false;
        assertEquals(expected, socketClient.getStatus());
    }
}