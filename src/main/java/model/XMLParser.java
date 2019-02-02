package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    private SimpleDateFormat dateTime;

    /**
     * Constructs parser.
     * @param url xml file url.
     */
    public XMLParser(URL url) {
        apiURL = url;
        dateTime = new SimpleDateFormat("yyyy-MM-dd");
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

    /**
     * Parses programs to list.
     * @param cList List to parse programs from.
     * @throws IOException IO failure
     * @throws ParserConfigurationException Parser fault in config
     * @throws SAXException Exception in parsing
     */
    public void parseProgram(List<ChannelInfo> cList) throws IOException, ParserConfigurationException, SAXException {

        //URL tempUrl = new URL()
        //System.out.println(cI.getScheduleURL().toString());
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (ChannelInfo cI : cList) {
                URL url1 = new URL(cI.getScheduleURL().toString()
                        + "&pagination=false" + "&date="
                        + localDateTime.minusHours(12).format(format));
                List<ProgramInfo> pList = new ArrayList<>(returnPrograms(url1));
                URL url2 = new URL(cI.getScheduleURL().toString()
                        + "&pagination=false" + "&date="
                        + localDateTime.plusHours(12).format(format));
                pList.addAll(returnPrograms(url2));
                cI.setProgramList(pList);
            }
    }

        private List<ProgramInfo> returnPrograms(URL url) throws
                ParserConfigurationException, IOException, SAXException {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            List<ProgramInfo> pList = new ArrayList<>();
            Document tempDoc = db.parse(url.openStream());
            NodeList l = tempDoc.getElementsByTagName("scheduledepisode");
            for (int k = 0; k < l.getLength(); k++) {
                Node n = l.item(k);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e2 = (Element) n;
                    ProgramInfo pI = new ProgramInfo(
                            e2.getElementsByTagName("title").item(0)
                                    .getTextContent());
                    if (e2.getElementsByTagName("description").getLength() > 0)
                    {
                        pI.setTagLine(e2.getElementsByTagName("description")
                                .item(0).getTextContent());
                    }
                    if (e2.getElementsByTagName("imageurl").getLength() > 0) {
                        pI.setImageURL(new URL(e2.getElementsByTagName(
                                "imageurl").item(0).getTextContent()));
                    }
                    pI.setStartTimeUTC(e2.getElementsByTagName("starttimeutc")
                            .item(0).getTextContent());
                    pI.setEndTimeUTC(e2.getElementsByTagName("endtimeutc")
                            .item(0).getTextContent());
                    if (checkTime(pI)) {
                        pList.add(pI);
                    }
                }
            }
            return pList;
        }

    /**
     * Parses channels from DOM doc.
     * @return list with channels.
     */
    public List<ChannelInfo> parseChannels() throws MalformedURLException {
        doc.getElementsByTagName("channels").item(0).normalize();
        NodeList l = doc.getElementsByTagName("channel");
        cList = new ArrayList<>();
        for (int i = 0; i < l.getLength(); i++) {
            Node n = l.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                ChannelInfo cI = new ChannelInfo(e.getAttribute("name"));
                cI.setId(Integer.parseInt(e.getAttribute("id")));
                if(e.getElementsByTagName("tagline").getLength() > 0) {
                    cI.setTagLine(e.getElementsByTagName("tagline")
                            .item(0).getTextContent());
                }
                cI.setImageURL(new URL(e.getElementsByTagName("image")
                        .item(0).getTextContent()));
                if(e.getElementsByTagName("scheduleurl").getLength() > 0){
                    cI.setScheduleURL(new URL(e.getElementsByTagName(
                            "scheduleurl").item(0).getTextContent()));
                    cI.setSiteURL(new URL(e.getElementsByTagName(
                            "siteurl").item(0).getTextContent()));
                    cList.add(cI);
                }
            }
        }
        return cList;
    }

    /**
     * Checks if time is in range.
     * @return boolean
     */
    private boolean checkTime(ProgramInfo pI) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.parse(pI.getStartTimeUTC()
                .replaceAll("Z$", ""));
        return time.minusHours(12).isBefore(time2)
                && time.plusHours(12).isAfter(time2);
    }

    /**
     * Function for updating.
     */
    public void update() throws IOException, SAXException,
                                ParserConfigurationException {
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
