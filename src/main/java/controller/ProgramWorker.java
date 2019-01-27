package controller;

import model.ChannelInfo;
import model.XMLParser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Parses programs to list.
 * @UserID - tfy17jfo
 * @date - 2019-01-25
 * @version 2.0
 * @author Jakob Fridesjö
 */
class ProgramWorker extends SwingWorker {

    private final XMLParser xml;
    private final List<ChannelInfo> cList;
    private final Controller controller;

    /**
     * Constructs an worker for programs.
     */
    public ProgramWorker(XMLParser xml, List<ChannelInfo> cList, Controller c) {
        this.xml = xml;
        this.cList = cList;
        this.controller = c;
    }

    /**
     * Parses programs.
     * @return if done
     * @throws ParserConfigurationException config error
     * @throws SAXException sax error
     * @throws IOException io error
     */
    @Override
    protected Object doInBackground() throws
                                                 ParserConfigurationException,
                                                 SAXException, IOException {
        xml.parseProgram(cList);
        return true;
    }

    /**
     * Signals controller when done.
     */
    @Override
    protected void done() {
        controller.signalProgramDone();
    }
}
