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
public class ProgramWorker extends SwingWorker {

    private XMLParser xml;
    private List<ChannelInfo> cList;

    /**
     * Constructs an worker for programs.
     */
    public ProgramWorker(XMLParser xml, List<ChannelInfo> cList) {
        this.xml = xml;
        this.cList = cList;
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
}
