package com.example.kallyruan.eldermap.P2PPkg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
public class MsgItemTest {
    private String content = "Hello";
    private int type;
    private int msgType;

    private MsgItem item;

    @Before
    public void setup(){
        type = 0;
        msgType = 10;
        item = new MsgItem(content, type, msgType);
    }
    @Test
    public void getContent() {
        assertEquals(content, item.getContent());
    }

    @Test
    public void setContent() {
        String expected = "HelloWorld";
        item.setContent(expected);
        assertEquals(expected, item.getContent());
    }

    @Test
    public void getType() {
        assertEquals(type, item.getType());
    }

    @Test
    public void setType() {
        int expected = 100;
        item.setType(expected);
        assertEquals(expected, item.getType());
    }

    @Test
    public void getContentType() {
        assertEquals(msgType, item.getContentType());
    }

    @Test
    public void setContentType() {
        int expected = 100;
        item.setContentType(expected);
        assertEquals(expected, item.getContentType());
    }

    @Test
    public void setFileName() {
        String expected = "WorldHello";
        item.setFileName(expected);
        assertEquals(expected, item.getFileName());

    }

    @Test
    public void getFileName() {
        assertEquals(null, item.getFileName());
    }
}