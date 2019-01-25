package controller;

import model.ChannelInfo;
import model.ProgramInfo;
import model.XMLParser;
import view.RadioView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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
    private XMLParser xml;
    private boolean listen2 = false;
    private String programName = null;
    boolean switching = false;


    public synchronized void setList(List<ChannelInfo> cList) {
        this.cList = cList;
    }

    /**
     * Constructor for controller
     */
    public Controller() {
        view = new RadioView("RadioInfo");
        view.startLoadingOverlay(true);
        setTimer();
    }

    public synchronized void programWorker() {
            xml.update();
            ProgramWorker pWorker = new ProgramWorker(xml, cList, this);
            pWorker.execute();
    }

    public synchronized void signalDone() {
        view.stopLoadingOverlay();
    }
    /**
     * Function that refreshes worker.
     */
    private synchronized void channelWorker() {
        try {
            view.startLoadingOverlay(true);
            String url = "http://api.sr.se/api/v2/channels?pagination=false";
            xml = new XMLParser(new URL(url));
            ChannelWorker cWorker = new ChannelWorker(xml, this);
            cWorker.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes worker hourly.
     */
    private synchronized void setTimer() {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    view.startLoadingOverlay(true);
                    channelWorker();
                }
            }, 0, 240000);
    }

    private synchronized void addListenersToMenu() {
        view.getMyMenuBar().addListenersToMenu(e -> {
            if (Objects.equals(e.getActionCommand(), "Uppdatera")) {
                view.startLoadingOverlay(false);
                channelWorker();
            } else {
                //TODO Implement channel loading
                for (ChannelInfo channelInfo : cList) {
                    if (Objects.equals(channelInfo.getName(), e.getActionCommand())) {
                        switching = true;
                        programName = e.getActionCommand();
                        view.setHeader(channelInfo.getImageURL());
                        refreshTable(channelInfo.getProgramList());
                        switching = false;
                    }
                }
            }
        });
    }

    private synchronized void refreshTable(List<ProgramInfo> pList) {
        view.getMyTable().resetScrollPosition();
        view.getMyTable().setpList(pList);
        view.getMyTable().refreshMyTable();
       // addListenersToTable();
        if (!listen2) {
            addTableListeners();
            listen2 = true;
        }
        view.enableContent();
    }

    private synchronized void addTableListeners() {
        view.getMyTable().addListenersToTableV2(new TableSelectionListener(view.getMyTable().getTable(), this));
    }

    synchronized void setProgramInfo(int index) {
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getName().equals(programName)) {
                if (index <= cList.size()) {
                    if (cList.get(i).getProgramList().get(index).getImageURL() != null) {
                        view.getInfoPanel().setImage(cList.get(i).getProgramList().get(index).getImageURL());
                    } else {
                        view.getInfoPanel().setImage("/home/tfy17jfo/IdeaProjects/RadioInfo-Appjava/src/main/resources/textures/sr.jpg");
                    }
                    if (cList.get(i).getProgramList().get(index).getTagLine() != null) {
                        view.getInfoPanel().setDescription(cList.get(i).getProgramList().get(index).getTagLine());
                    } else {
                        view.getInfoPanel().setDescription("Ingen information tillgänglig.");
                    }
                    break;
                }
            }
        }
    }

    synchronized public void signalChannelDone() {
        view.getMyMenuBar().clearChannels();
        view.getMyMenuBar().addChannels(cList);
        addListenersToMenu();
    }
}
