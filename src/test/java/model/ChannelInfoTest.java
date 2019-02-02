package model;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class ChannelInfoTest {
    private ChannelInfo channelInfo;

    @Before
    public void setUp() {
        channelInfo = new ChannelInfo("test");
    }

    @Test
    public void testGetName() {
        assertEquals("test",channelInfo.getName());
    }

    @Test
    public void testImageURL() throws MalformedURLException {
        channelInfo.setImageURL(new URL
                ("https://static-cdn.sr.se/sida/images/4540/3634468_2048_1152.jpg?preset=api-default-square"));
        assertNotNull(channelInfo.getImageURL());
    }
}