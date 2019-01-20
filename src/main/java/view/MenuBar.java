package view;

import model.ChannelInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
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

    private List<JMenuItem> itemList;
    private final JMenu channelMenu;
    private JMenuItem refresh;
    /**
     * Constructs the basic menu.
     */
    public MenuBar() {
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
     * Adds list of channels to menu.
     * @param cList list to add.
     */
    public void add(List<ChannelInfo> cList) {
        for (ChannelInfo c : cList) {
            JMenuItem cItem = new JMenuItem(c.getName());
            channelMenu.add(new JMenuItem(c.getName()));
            //cItem.addActionListener(e -> System.out.println(e.getActionCommand()));
            itemList.add(cItem);
        }
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
    public void addListenersToMenu(ActionListener a) {
        for (int i = 0; i < itemList.size(); i++) {
            itemList.get(i).addActionListener(a);
        }
        refresh.addActionListener(a);
    }
}
