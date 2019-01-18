package controller;

import model.ChannelInfo;
import model.XMLParser;
import view.MenuBar;
import view.RadioView;

import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

/**
 * controller class for starting a test instance.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class Controller {
    private final RadioView view;
    private List<ChannelInfo> cList;
    private final String url = "http://api.sr.se/api/v2/channels?pagination=false";
    private XMLParser xml;
    private ChannelWorker cWorker;
    private ProgramWorker pWorker;


    public void setList(List<ChannelInfo> cList) {
        this.cList = cList;
    }

    /**
     * Constructor for controller
     */
    public Controller() {
        view = new RadioView("RadioInfo");
        channelWorker();
        //view.setListeners(new Listeners());
    }

    private void programWorker() {
        if (cWorker.done) {
            xml.update();
            pWorker = new ProgramWorker(xml, cList);
            pWorker.execute();
        } else {
            System.out.println("Not done");
        }
    }

    /**
     * Function that refreshes worker.
     */
    private void channelWorker() {
        try {
            xml = new XMLParser(new URL(url));
            xml.parseToDoc();
            this.cWorker = new ChannelWorker((MenuBar) view.getMenuBarInfo(), xml, this);
            cWorker.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes worker hourly.
     */
    public void setTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                programWorker();
            }
        }, 0, 3600000);
    }
}
