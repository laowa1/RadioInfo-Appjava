package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * controller class for starting a test instance.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class RadioView extends JFrame {

    private static final long serialVersionUID = -471689426345376977L;
    private final int F_WIDTH = 500; //Minimum 400
    private final int F_HEIGHT = 640; //Minimum 400
    private final JMenuBar menuBar;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;

    /**
     * Creates the view.
     * @param title of program
     */
    public RadioView(String title) {

        setMinimumSize(new Dimension(F_WIDTH, F_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setSize(F_WIDTH, F_HEIGHT);
        createPanels();
        //table = new Table(2,12);
        menuBar = new MenuBar();
        northPanel.add(menuBar, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Creates panels.
     */
    private void createPanels() {
        northPanel = new JPanel(new BorderLayout());
        centerPanel = new TablePanel();
        southPanel = new InfoPanel();
        //centerPanel.setBackground(Color.WHITE);
        southPanel.setPreferredSize(new Dimension(400,300));
    }

    /**
     * Gets the menuBar.
     * @return
     */
    public JMenuBar getMenuBarInfo() {
        return menuBar;
    }

    /**
     * Non-implemented listener function.
     * @param l listener
     */
    public void setListeners(ActionListener l){
        menuBar.addMouseListener((MouseListener) l);
        //table.addActionListener(l);
    }
}
