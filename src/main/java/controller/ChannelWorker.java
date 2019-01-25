package controller;

import model.ChannelInfo;
import model.XMLParser;
import view.MenuBar;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Parses channels to list.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
class ChannelWorker extends SwingWorker<Boolean, ChannelInfo> {

    private XMLParser xml;
    private List<ChannelInfo> cList;
    private Controller crl;
    public boolean done = false;

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

    /**
     * For getting the ChannelInfo list.
     * @return List with channels
     */
/*    public List<ChannelInfo> getList() {
        return this.cList;
    }*/

    /**
     * Sets timer when done.
     */
    @Override
    protected void done() {
        done = true;
        crl.setList(cList);
        crl.signalChannelDone();
        crl.programWorker();
    }
}
