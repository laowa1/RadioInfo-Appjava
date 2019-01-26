package controller;

import model.ChannelInfo;
import model.XMLParser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Parses channels to list.
 * @UserID - tfy17jfo
 * @date - 2019-01-25
 * @version 2.0
 * @author Jakob Fridesjö
 */
class ChannelWorker extends SwingWorker<Boolean, ChannelInfo> {

    private final XMLParser xml;
    private List<ChannelInfo> cList;
    private final Controller crl;

    /**
     * Constructs a worker for parsing channels.
     * @param xml - xml to parse
     * @param crl - controller instance
     */
    ChannelWorker(XMLParser xml, Controller crl) throws IOException, SAXException, ParserConfigurationException {
        xml.parseToDoc();
        this.xml = xml;
        this.crl = crl;
    }

    /**
     * Parses channels and adds to a list.
     * @return boolean
     */
    @Override
    protected Boolean doInBackground() throws MalformedURLException {
        this.cList = xml.parseChannels();
        return true;
    }

    /**
     * Sets timer when done.
     */
    @Override
    protected void done() {
        crl.setList(cList);
        crl.signalChannelDone();
        crl.programWorker();
    }
}
