package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("EmptyMethod")
public class ParserTest {


    private XMLParser parser;

    @Before
    public void setUp() {
        try {
            parser = new XMLParser(new URL("http://api.sr.se/api/v2/channels?pagination=false"));
            parser.parseToDoc();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        parser = null;
    }

    @Test
    public void parseToDoc() {
        assertNotNull(parser.doc);
    }

    @Test
    public void getElement() {
    }

    @Test
    public void parsePrograms() {

    }

    @Test
    public void parseChannels() {
        List<ChannelInfo> cList = parser.parseChannels();
        for (ChannelInfo channelInfo : cList) {
            System.out.println(channelInfo.getName());
        }
    }

    @Test
    public void update() {
    }
}