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
 * @date - 2018-12-10
 * @version 1.0
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
        //menuItem.addMenuKeyListener(new ControllClass(this, 'm'));
        //refresh.addActionListener(e -> System.out.println(e.getActionCommand()));
        menu.add(channelMenu);
        menu.add(refresh);
        add(menu);
    }

    /**
     * Adds single channel to menu.
     * @param cI channel to add.
     */
    public void addChannel(ChannelInfo cI) {
        JMenuItem cItem = new JMenuItem((cI.getName()));
        channelMenu.add(cItem);
        //AItem.addActionListener(e -> System.out.println(e.getActionCommand()));
        itemList.add(cItem);
    }

    /**
     * Adds list of channels to menu.
     * @param cList channel to add.
     */
    public void addChannels(List<ChannelInfo> cList) {
        for (int i = 0; i < cList.size(); i++) {
            JMenuItem cItem = new JMenuItem((cList.get(i).getName()));
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
        for (int i = 0; i < listeners.length; i++) {
            this.channelMenu.removeMenuListener(listeners[i]);
        }
        MenuKeyListener[] refreshListener = refresh.getMenuKeyListeners();
        for (int i = 0; i < refreshListener.length; i++) {
            refresh.removeMenuKeyListener(refreshListener[i]);
        }
        for (JMenuItem jMenuItem : itemList) {
            jMenuItem.addActionListener(a);
        }
        refresh.addActionListener(a);
    }

    public void clearChannels() {
        this.channelMenu.removeAll();
    }
}
