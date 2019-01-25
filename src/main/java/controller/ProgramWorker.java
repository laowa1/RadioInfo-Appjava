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
 * @date - 2018-12-10
 * @version 1.0
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

    @Override
    protected Object doInBackground() throws
                                                 ParserConfigurationException,
                                                 SAXException, IOException {
        for (ChannelInfo cI : this.cList) {
            xml.parseProgram(cI);
        }
        return true;
    }

    /**
     * For getting list.
     * @return list
     */
    public List<ChannelInfo> getList() {
        return cList;
    }
    /*
    @Override
    protected void process(List<ChannelInfo> cData) {
        for (ChannelInfo cI : cData) {
            this.menuBar.addChannel(cI);
        }
    }*/
    @Override
    protected void done() {
        controller.signalDone();
    }
}
