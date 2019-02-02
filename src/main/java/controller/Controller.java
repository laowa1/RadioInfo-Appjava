package controller;

import model.ChannelInfo;
import model.ProgramInfo;
import model.XMLParser;
import org.xml.sax.SAXException;
import view.RadioView;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;
import java.util.Timer;

/**
 * controller class for starting a test instance.
 * @UserID - tfy17jfo
 * @date - 2019-01-25
 * @version 2.0
 * @author Jakob Fridesjö
 */
public class Controller {
    private final RadioView view;
    private List<ChannelInfo> cList;
    private XMLParser xml;
    private boolean listen2 = false;
    private String programName = null;
    boolean switching = false;
    private boolean done = false;

    /**
     * Sets the list for channels.
     * @param cList channel list.
     */
     void setList(List<ChannelInfo> cList) {
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

    /**
     * Creates a new program worker.
     */
     void programWorker() {
        try {
            xml.update();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            showErrors("Error: " + e.toString());
        }
        ProgramWorker pWorker = new ProgramWorker(xml, cList, this);
        pWorker.execute();
    }

    /**
     * Function that refreshes worker.
     */
    private void channelWorker() {
        try {
            //view.startLoadingOverlay(true);
            String url = "http://api.sr.se/api/v2/channels?pagination=false";
            xml = new XMLParser(new URL(url));
            ChannelWorker cWorker = new ChannelWorker(xml, this);
            cWorker.execute();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            showErrors("Error: " + e.toString());
        }
    }

    /**
     * Shows error in view.
     * @param s string
     */
    private  void showErrors(String s) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.setText(s);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.clearText();
                timer.cancel();
            }
        },100,100);
    }

    /**
     * Refreshes worker hourly.
     */
    private  void setTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                view.startLoadingOverlay(true);
                channelWorker();
            }
        }, 0, 240000);
    }

    /**
     * Adds listeners to the menu.
     */
    private  void addListenersToMenu() {
        view.getMyMenuBar().addListenersToMenu(e -> {
            if (Objects.equals(e.getActionCommand(), "Uppdatera")) {
                view.startLoadingOverlay(false);
                showErrors("Error: " + new SAXException().toString());
                channelWorker();
            } else {
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

    /**
     * Refreshes the table.
     * @param pList list of programs.
     */
    private  void refreshTable(List<ProgramInfo> pList) {
        view.getMyTable().setpList(pList);
        view.getMyTable().refreshMyTable();
       // addListenersToTable();
        if (!listen2) {
            addTableListeners();
            listen2 = true;
        }
        view.enableContent();
    }

    /**
     * Adds listeners to the table
     */
    private  void addTableListeners() {
        view.getMyTable().addListenersToTable(new TableSelectionListener(view.getMyTable().getTable(), this));
    }

    /**
     * Sets info about program
     * @param index list index for program
     */
     void setProgramInfo(int index) {
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getName().equals(programName)) {
                if (index <= cList.size()) {
                    try {
                        System.out.println(cList.get(i).getProgramList().get(index).getImageURL());
                        if (cList.get(i).getProgramList().get(index).getImageURL() != null) {
                            view.getInfoPanel().setImage(cList.get(i).getProgramList().get(index).getImageURL());
                        } else {
                            view.getInfoPanel().setImage("/textures/sr.jpg");
                        }
                    } catch (IOException e) {
                        showErrors("Error: " + e.toString());
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

    /**
     * Signal that channel parsing has been completed.
     */
     void signalChannelDone() {
        view.getMyMenuBar().clearChannels();
        view.getMyMenuBar().addChannels(cList);
        addListenersToMenu();
    }

    /**
     * Disables overlay and loads first channel/program.
     */
     void signalProgramDone() {
        view.stopLoadingOverlay();
        if (!done) {
            if (cList.size() > 0) {
                programName = cList.get(0).getName();
                view.setHeader(cList.get(0).getImageURL());
                refreshTable(cList.get(0).getProgramList());
                view.stopLoadingOverlay();
            }
        }
        done = true;
    }
}