package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@SuppressWarnings("EmptyMethod")
public class ParserTest {


    private XMLParser parser;

    @Before
    public void setUp() throws IOException, SAXException,
            ParserConfigurationException {
        parser = new XMLParser(new URL(
                "http://api.sr.se/api/v2/channels?pagination=false"));
        parser.parseToDoc();

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
    public void parseChannelsAndPrograms() throws IOException,
            ParserConfigurationException, SAXException {
        List<ChannelInfo> cList = parser.parseChannels();
        parser.parseProgram(cList);
        assertNotNull(cList.get(0).getName());
        assertNotNull(cList.get(0).getProgramList().get(0).getName());
    }
}