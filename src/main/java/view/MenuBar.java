package view;

import model.ChannelInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * For creating a menu.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class MenuBar extends JMenuBar {

    private final JMenu channelMenu;

    /**
     * Constructs the basic menu.
     */
    public MenuBar() {
        JMenu menu = new JMenu("Meny");
        channelMenu = new JMenu("Kanaler");
        JMenuItem menuItem = new JMenuItem("Uppdatera");
        //menuItem.addMenuKeyListener(new ControllClass(this, 'm'));
        menu.add(channelMenu);
        menu.add(menuItem);
        add(menu);
    }

    /**
     * Deprecated debug function.
     */
    private void addListeners() {

    }

    /**
     * Adds list of channels to menu.
     * @param cList list to add.
     */
    public void add(List<ChannelInfo> cList) {
        for (ChannelInfo c : cList) {
            JMenuItem cItem = new JMenuItem(c.getName());
            channelMenu.add(new JMenuItem(c.getName()));
            cItem.addActionListener(e -> System.out.println(e.getActionCommand()));
        }
    }

    /**
     * Adds single channel to menu.
     * @param cI channel to add.
     */
    public void addChannel(ChannelInfo cI) {
        JMenuItem cItem = new JMenuItem((cI.getName()));
        channelMenu.add(cItem);
        cItem.addActionListener(e -> System.out.println(e.getActionCommand()));
    }

}
