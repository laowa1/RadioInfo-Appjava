package controller;

import model.ChannelInfo;
import model.XMLParser;

import javax.swing.*;
import java.util.List;

/**
 * Parses channels to list.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
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
    ChannelWorker(XMLParser xml, Controller crl) {
        xml.parseToDoc();
        this.xml = xml;
        this.crl = crl;
    }

    /**
     * Parses channels and adds to a list.
     * @return boolean
     */
    @Override
    protected Boolean doInBackground() {
        this.cList = xml.parseChannels();
        /*for (ChannelInfo cI : cList) {
            System.out.println(cI.getName());
            //publish(cI);
        }*/
        return true;
    }

// --Commented out by Inspection START (2019-01-25 22:33):
//    /**
//     * For getting the ChannelInfo list.
//     * @return List with channels
//     */
//    public List<ChannelInfo> getList() {
//        return this.cList;
//    }
// --Commented out by Inspection STOP (2019-01-25 22:33)

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
