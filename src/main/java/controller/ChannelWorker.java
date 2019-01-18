package controller;

import model.ChannelInfo;
import model.XMLParser;
import view.MenuBar;

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

    private final MenuBar menuBar;
    private XMLParser xml;
    private List<ChannelInfo> cList;
    private Controller crl;
    public boolean done = false;

    /**
     * Constructs a worker for parsing channels.
     * @param menuBar menu to add channels to.
     * @param xml xml to parse.
     */
    ChannelWorker(MenuBar menuBar, XMLParser xml, Controller crl) {
        this.menuBar = menuBar;
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
        for (ChannelInfo cI : cList) {
            publish(cI);
        }
        return true;
    }

    /**
     * Constantly updates menu with entries.
     * @param cData list to add to menu
     */
    @Override
    protected void process(List<ChannelInfo> cData) {
        for (ChannelInfo cI : cData) {
            this.menuBar.addChannel(cI);
        }
    }

    /**
     * For getting the ChannelInfo list.
     * @return List with channels
     */
    public List<ChannelInfo> getList() {
        return this.cList;
    }

    /**
     * Sets timer when done.
     */
    @Override
    protected void done() {
        done = true;
        crl.setList(cList);
        crl.setTimer();
    }
}
