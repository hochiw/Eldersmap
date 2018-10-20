package com.example.kallyruan.eldermap.P2PPkg;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JSONObject.class, MsgCoder.class})
public class MsgCoderTest {


    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(MsgCoder.class);
    }

    @Test
    public void encode() throws Exception{
        String expected = "HelloAreYouGood";
        JSONObject object = PowerMock.createMock(JSONObject.class);
        PowerMockito.whenNew(JSONObject.class).withAnyArguments().
                thenReturn(object);
        MsgItem msgItem = PowerMockito.mock(MsgItem.class);
        MsgCoder.encode(msgItem);
        PowerMockito.when(MsgCoder.encode(msgItem)).thenReturn(expected);
        assertEquals(expected, MsgCoder.encode(msgItem));
    }

    @Test
    public void decode() throws Exception{
        String message = "HelloWorld";
        MsgItem item = PowerMockito.mock(MsgItem.class);
        JSONObject object = PowerMockito.mock(JSONObject.class);
        PowerMockito.whenNew(JSONObject.class).withAnyArguments().
                thenReturn(object);

        PowerMockito.when(object.has("type")).thenReturn(true);
        PowerMockito.when(object.has("content")).thenReturn(true);

        PowerMockito.when(object.get("type")).thenReturn("2");
        PowerMockito.when(object.get("type").toString()).thenReturn("2");
        PowerMockito.when(Integer.parseInt(object.get("type").toString())).thenReturn(2);
        Mockito.doReturn("Hello").when(object).get("content");

        PowerMockito.whenNew(MsgItem.class).withAnyArguments().thenReturn(item);

        PowerMockito.when(MsgCoder.decode(message)).thenReturn(item);
        assertEquals(item, MsgCoder.decode(message));
    }
}