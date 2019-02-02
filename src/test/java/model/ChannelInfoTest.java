package model;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class ChannelInfoTest {
    private ChannelInfo channelInfo;

    /**
     * setUp
     */
    @Before
    public void setUp() {
        channelInfo = new ChannelInfo("test");
    }

    /**
     * tests general setter/getter
     */
    @Test
    public void testGetName() {
        assertEquals("test",channelInfo.getName());
    }

    /**
     * Tests url loading
     * @throws MalformedURLException malformed url
     */
    @Test
    public void testImageURL() throws MalformedURLException {
        channelInfo.setImageURL(new URL
        ("https://static-cdn.sr.se/sida/images/4540/3634468_2048_1152.jpg"));
        assertNotNull(channelInfo.getImageURL());
    }

    /**
     * Tests url loading
     */
    @Test
    public void testNullURL() {
        channelInfo.setImageURL(null);
        assertNull(channelInfo.getImageURL());
    }
}