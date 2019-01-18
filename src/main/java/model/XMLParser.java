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
    private List<ProgramInfo> pList;
    Document doc = null;
    private URL apiURL;

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
    public void parseToDoc() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(apiURL.openStream());
            //doc.normalizeDocument();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dummy for debugging.
     */
    public void print() {
        for (int i = 0; i < doc.getElementsByTagName("channels").getLength(); i++) {
            System.out.println(doc.getChildNodes().item(i).getTextContent());
        }
    }

    /**
     * Not implemented
     */
    private void updatePrograms() {
        //TODO probably remove.
    }

    /**
     * Parses programs to list.
     * @param cI channel to parse from.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void parseProgram(ChannelInfo cI) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        //URL tempUrl = new URL()
        //System.out.println(cI.getScheduleURL().toString());
        System.out.println(cI.getId());
        Document tempDoc = db.parse(cI.getScheduleURL().openStream());
        NodeList lTemp = tempDoc.getElementsByTagName("pagination");
        Element e = (Element) lTemp.item(0);
        int pages = Integer.parseInt(e.getElementsByTagName("totalpages").item(0).getTextContent());
        pList = new ArrayList<>();
        for (int j = 1; j < pages; j++) {
            URL tempURL = new URL(cI.getScheduleURL() + "&page=" + j);
            tempDoc = db.parse(tempURL.openStream());
            NodeList l = tempDoc.getElementsByTagName("scheduledepisode");
            for (int i = 0; i < l.getLength(); i++) {
                Node n = l.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        Element e2 = (Element) n;
                        ProgramInfo pI = new ProgramInfo(e.getAttribute("title"));
                        //pI.setId(Integer.parseInt(e.getElementsByTagName("episodeid").item(0).getTextContent()));
                        pI.setTagLine(e2.getElementsByTagName("description").item(0).getTextContent());
                        if (e2.getElementsByTagName("imageurl").getLength() > 0) {
                            pI.setImageURL(new URL(e2.getElementsByTagName("imageurl").item(0).getTextContent()));
                        }
                        pI.setStartTimeUTC(e2.getElementsByTagName("starttimeutc").item(0).getTextContent());
                        pI.setEndTimeUTC(e2.getElementsByTagName("endtimeutc").item(0).getTextContent());
                        pList.add(pI);
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        cI.setProgramList(pList);
    }

    /**
     * Parses channels from DOM doc.
     * @return list with channels.
     */
    public List<ChannelInfo> parseChannels() {
        doc.getElementsByTagName("channels").item(0).normalize();
        NodeList l = doc.getElementsByTagName("channel");
        cList = new ArrayList<>();
        //System.out.println(l.getLength()); //Debug output
        for (int i = 0; i < l.getLength(); i++) {
            Node n = l.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                try {
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
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return cList;
    }

    /**
     * Function for updating.
     */
    public void update() {
        //TODO Implement in controller.
        if (apiURL != null) {
            for (int i = 0; i < cList.size(); i++) {
                if (cList.get(i).getProgramList() != null) {
                    cList.get(i).getProgramList().clear();
                }
            }
            this.parseToDoc();
            this.parseChannels();
        }
    }
}
