package com.example.eldermap.P2PPkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * MsgItem is an object carrying information for P2P transfer.
 * The class is consisted of getters and setters.
 */
@RunWith(PowerMockRunner.class)
public class MsgItemTest {
    private String content = "Hello";
    private int type;
    private int msgType;

    private MsgItem item;

    /**
     * SetUp before the test.
     */
    @Before
    public void setup(){
        type = 0;
        msgType = 10;
        item = new MsgItem(content, type, msgType);
    }

    /**
     * Test getContent.
     * if success, it should return content as expected.
     */
    @Test
    public void getContent() {
        assertEquals(content, item.getContent());
    }

    /**
     * Test setContent.
     * If success, it should return a new setted content.
     */
    @Test
    public void setContent() {
        String expected = "HelloWorld";
        item.setContent(expected);
        assertEquals(expected, item.getContent());
    }

    /**
     * Test getType.
     * If success, it should return type as expected.
     */
    @Test
    public void getType() {
        assertEquals(type, item.getType());
    }

    /**
     * Test setType.
     * If success, it should return setting type.
     */
    @Test
    public void setType() {
        int expected = 100;
        item.setType(expected);
        assertEquals(expected, item.getType());
    }

    /**
     * Test getContentType.
     * if success, it should return contentType.
     */
    @Test
    public void getContentType() {
        assertEquals(msgType, item.getContentType());
    }

    /**
     * Test setFileName.
     * If success, it should return a new fileName as expected.
     */
    @Test
    public void setFileName() {
        String expected = "WorldHello";
        item.setFileName(expected);
        assertEquals(expected, item.getFileName());

    }

    /**
     * Test getFileName.
     * If success, it should return a fileName as expected.
     */
    @Test
    public void getFileName() {
        assertEquals(null, item.getFileName());
    }
}