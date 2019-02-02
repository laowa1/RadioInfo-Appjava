package view;

import model.ChannelInfo;

import javax.swing.*;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * For creating a menu.
 * @UserID - tfy17jfo
 * @date - 2018-01-25
 * @version 2.0
 * @author Jakob Fridesjö
 */
public class MenuBar extends JMenuBar {

    private final List<JMenuItem> itemList;
    private final JMenu channelMenu;
    private final JMenuItem refresh;

    /**
     * Constructs the basic menu.
     */
    MenuBar() {
        itemList = new ArrayList<>();
        JMenu menu = new JMenu("Meny");
        channelMenu = new JMenu("Kanaler");
        refresh = new JMenuItem("Uppdatera");
        menu.add(channelMenu);
        menu.add(refresh);
        add(menu);
    }

    /**
     * Adds single channel to menu.
     * @param cI channel to add.
     */
    @SuppressWarnings("unused")
    public void addChannel(ChannelInfo cI) {
        JMenuItem cItem = new JMenuItem((cI.getName()));
        channelMenu.add(cItem);
        itemList.add(cItem);
    }

    /**
     * Adds list of channels to menu.
     * @param cList channel to add.
     */
    public void addChannels(List<ChannelInfo> cList) {
        for (ChannelInfo channelInfo : cList) {
            JMenuItem cItem = new JMenuItem((channelInfo.getName()));
            channelMenu.add(cItem);
            itemList.add(cItem);
        }
    }

    /**
     * Adds listeners to the menu.
     * @param a ActionListener
     */
    public void addListenersToMenu(ActionListener a) {
        MenuListener[] listeners = this.channelMenu.getMenuListeners();
        for (MenuListener listener : listeners) {
            this.channelMenu.removeMenuListener(listener);
        }
        MenuKeyListener[] refreshListener = refresh.getMenuKeyListeners();
        for (MenuKeyListener menuKeyListener : refreshListener) {
            refresh.removeMenuKeyListener(menuKeyListener);
        }
        for (JMenuItem jMenuItem : itemList) {
            jMenuItem.addActionListener(a);
        }
        refresh.addActionListener(a);
    }

    /**
     * Clears the menu.
     */
    public void clearChannels() {
        this.channelMenu.removeAll();
    }
}
