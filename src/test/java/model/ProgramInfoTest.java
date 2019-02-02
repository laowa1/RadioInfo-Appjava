package model;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class ProgramInfoTest {
    private ProgramInfo programInfo;

    @Before
    public void setUp() {
        programInfo = new ProgramInfo("test");
    }

    @Test
    public void testGetName() {
        assertEquals("test",programInfo.getName());
    }

    @Test
    public void testImageURL() throws MalformedURLException {
        programInfo.setImageURL(new URL
                ("https://static-cdn.sr.se/sida/images/4540/3634468_2048_1152.jpg?preset=api-default-square"));
        assertNotNull(programInfo.getImageURL());
    }
}