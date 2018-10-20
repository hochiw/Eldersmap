package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Environment;
import android.support.annotation.BoolRes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.hamcrest.CoreMatchers.isA;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileEncoder.class, Environment.class})
public class FileEncoderTest {
    byte[] bytes;

    @Before
    public void setup(){

    }

    @Test
    public void convertFileToByte() throws Exception{
        MockitoAnnotations.initMocks(this);

        int length = 1;
        File file =PowerMockito.mock(File.class);
        PowerMockito.when(file.length()).thenReturn((long) length);
        Exception convertFileToByteEx = new Exception("mockException");

        FileInputStream fileInputStream = PowerMockito.mock
                (FileInputStream.class);
        PowerMockito.whenNew(FileInputStream.class).
                withAnyArguments().thenReturn(fileInputStream);
        bytes = new byte[(int) file.length()];
        assertThat(FileEncoder.convertFileToByte(file),isA(byte[].class));
    }

    @Test
    public void byteToBase64() {
        byte[] bytes = new byte[2];
        assertNull(FileEncoder.byteToBase64(bytes));
    }

    @Test
    public void base64ToByte() {
        String string = "Hello";
        assertNull(FileEncoder.base64ToByte(string));
    }

    @Test
    public void writeToFile() throws Exception{
        String fileName = "Hello";
        String path = "HelloWorld";
        PowerMockito.mockStatic(Environment.class);
        File dir = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(dir);
        File file = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);

        byte[] data = new byte[2];
        PowerMock.suppress(constructor(FileOutputStream.class, File.class));
        PowerMockito.suppress(method(FileEncoder.class, "write"));
        FileEncoder.writeToFile(data, fileName);
        assertNull(FileEncoder.writeToFile(data,fileName));
    }
}