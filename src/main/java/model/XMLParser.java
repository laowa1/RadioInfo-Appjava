package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses channels and programs from xml.
 * @UserID - tfy17jfo
 * @date - 2019-01-08
 * @version 1.0
 * @author Jakob Fridesjö
 */

public class XMLParser {
    private List<ChannelInfo> cList;
    Document doc = null;
    private final URL apiURL;

    /**
     * Constructs parser.
     * @param url xml file url.
     */
    public XMLParser(URL url) {
        apiURL = url;
    }

    /**
     * Parses url to DOM doc.
     */
    public void parseToDoc() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(apiURL.openStream());
        //doc.normalizeDocument();
    }

// --Commented out by Inspection START (2019-01-25 22:32):
//    /**
//     * Dummy for debugging.
//     */
//    public void print() {
//        for (int i = 0; i < doc.getElementsByTagName("channels").getLength(); i++) {
//            System.out.println(doc.getChildNodes().item(i).getTextContent());
//        }
//    }
// --Commented out by Inspection STOP (2019-01-25 22:32)

    /**
     * Parses programs to list.
     * @param cI channel to parse from.
     * @throws IOException IO failure
     * @throws ParserConfigurationException Parser fault in config
     * @throws SAXException Exception in parsing
     */
    public void parseProgram(ChannelInfo cI) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        //URL tempUrl = new URL()
        //System.out.println(cI.getScheduleURL().toString());
        Document tempDoc = db.parse(cI.getScheduleURL().openStream());
        NodeList lTemp = tempDoc.getElementsByTagName("pagination");
        Element e = (Element) lTemp.item(0);
        int pages = Integer.parseInt(e.getElementsByTagName("totalpages").item(0).getTextContent());
        List<ProgramInfo> pList = new ArrayList<>();
        for (int j = 1; j < pages; j++) {
            URL tempURL = new URL(cI.getScheduleURL() + "&page=" + j);
            tempDoc = db.parse(tempURL.openStream());
            NodeList l = tempDoc.getElementsByTagName("scheduledepisode");
            for (int i = 0; i < l.getLength(); i++) {
                Node n = l.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e2 = (Element) n;
                    ProgramInfo pI = new ProgramInfo(e2.getElementsByTagName("title").item(0).getTextContent());
                    //pI.setId(Integer.parseInt(e.getElementsByTagName("episodeid").item(0).getTextContent()));
                    pI.setTagLine(e2.getElementsByTagName("description").item(0).getTextContent());
                    if (e2.getElementsByTagName("imageurl").getLength() > 0) {
                        pI.setImageURL(new URL(e2.getElementsByTagName("imageurl").item(0).getTextContent()));
                    }
                    pI.setStartTimeUTC(e2.getElementsByTagName("starttimeutc").item(0).getTextContent());
                    pI.setEndTimeUTC(e2.getElementsByTagName("endtimeutc").item(0).getTextContent());
                    pList.add(pI);
                }
            }
        }
        cI.setProgramList(pList);
    }

    /**
     * Parses channels from DOM doc.
     * @return list with channels.
     */
    public List<ChannelInfo> parseChannels() throws MalformedURLException {
        doc.getElementsByTagName("channels").item(0).normalize();
        NodeList l = doc.getElementsByTagName("channel");
        cList = new ArrayList<>();
        //System.out.println(l.getLength()); //Debug output
        for (int i = 0; i < l.getLength(); i++) {
            Node n = l.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                ChannelInfo cI = new ChannelInfo(e.getAttribute("name"));
                cI.setId(Integer.parseInt(e.getAttribute("id")));
                if(e.getElementsByTagName("tagline").getLength() > 0) {
                    cI.setTagLine(e.getElementsByTagName("tagline").item(0).getTextContent());
                }
                cI.setImageURL(new URL(e.getElementsByTagName("image").item(0).getTextContent()));
                if(e.getElementsByTagName("scheduleurl").getLength() > 0){
                    cI.setScheduleURL(new URL(e.getElementsByTagName("scheduleurl").item(0).getTextContent()));
                    cI.setSiteURL(new URL(e.getElementsByTagName("siteurl").item(0).getTextContent()));
                    cList.add(cI);
                }
            }
        }
        return cList;
    }

    /**
     * Function for updating.
     */
    public void update() throws IOException, SAXException, ParserConfigurationException {
        if (apiURL != null) {
            for (ChannelInfo channelInfo : cList) {
                if (channelInfo.getProgramList() != null) {
                    channelInfo.getProgramList().clear();
                }
            }
            this.parseToDoc();
            this.parseChannels();
        }
    }
}
