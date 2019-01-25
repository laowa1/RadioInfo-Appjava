package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * controller class for starting a test instance.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class RadioView extends JFrame {

    private static final long serialVersionUID = -471689426345376977L;
    private final MenuBar menuBar;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private final JLabel topLabel;
    private final JPanel glassPane;
    /**
     * Creates the view.
     * @param title of program
     */
    public RadioView(String title) {
        this.setTitle(title);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        glassPane = (JPanel) this.getGlassPane();
        //Minimum 400
        int f_HEIGHT = 640;//Minimum 400
        int f_WIDTH = 500;
        glassPane.setSize(f_WIDTH, f_HEIGHT);
        glassPane.setLayout(new BorderLayout());
        JLabel glassLabel1 = new JLabel(new ImageIcon("/home/tfy17jfo/IdeaProjects/RadioInfo-Appjava/src/main/resources/textures/loading.gif"));
        JTextField glassText = new JTextField("Loading...");
       /* glassText.setFont(new Font("Sans", Font.PLAIN, 40));
        glassText.setHorizontalAlignment(JTextField.CENTER);
        glassText.setEditable(false);
        glassText.setOpaque(false);
        glassText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        glassPane.add(glassText, BorderLayout.SOUTH);
        */
        glassPane.add(glassLabel1, BorderLayout.CENTER);
        glassPane.setVisible(true);
        glassPane.setOpaque(true);
        glassPane.setBackground(Color.WHITE);

        topLabel = new JLabel();
        topLabel.setSize(70,300);
        setMinimumSize(new Dimension(f_WIDTH, f_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createPanels();
        //table = new Table(2,12);
        menuBar = new MenuBar();
        northPanel.add(menuBar, BorderLayout.NORTH);
        northPanel.add(topLabel, BorderLayout.CENTER);
        contentPanel.add(northPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.WEST);
        contentPanel.add(southPanel, BorderLayout.CENTER);
        contentPanel.setVisible(true);
        this.getContentPane().add(contentPanel);
        pack();
        setVisible(true);
        startLoadingOverlay(false);
        centerPanel.setVisible(false);
        southPanel.setVisible(false);
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
     * @return menuBar
     */
    public JMenuBar getMenuBarInfo() {
        return menuBar;
    }

    /**
     * Gets menuBar.
     * @return menuBar
     */

    public MenuBar getMyMenuBar() {
        return menuBar;
    }

    public void setListeners(ActionListener l){
        menuBar.addMouseListener((MouseListener) l);
        //table.addActionListener(l);
    }

    public TablePanel getMyTable() {
        return (TablePanel) centerPanel;
    }

    public InfoPanel getInfoPanel() {
        return (InfoPanel) southPanel;
    }

    public void setHeader(URL url) {
        //topLabel.setPreferredSize(new Dimension(50, 300));
        try {
            BufferedImage image = ImageIO.read(url);
            topLabel.setOpaque(true);
            topLabel.setIcon(scaleV2(image,70,70));
            topLabel.setBackground(new Color(image.getRGB(0,0)));
            //table.setGridColor(new Color(image2.getRGB(0,0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startLoadingOverlay(boolean opaque) {
        glassPane.setOpaque(opaque);
        glassPane.setVisible(true);
        repaint();
    }

    public void stopLoadingOverlay() {
        glassPane.setVisible(false);
        repaint();
    }

    private ImageIcon scaleV2(BufferedImage bufImg, int w, int h){
        Image render = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(render);

    }

    public void enableContent() {
        centerPanel.setVisible(true);
        southPanel.setVisible(true);
    }
}
