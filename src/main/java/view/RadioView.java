package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
@SuppressWarnings("SameParameterValue")
public class RadioView extends JFrame {

    private static final long serialVersionUID = -471689426345376977L;
    private final MenuBar menuBar;
    private JPanel northPanel;
    private JPanel tablePanel;
    private JPanel infoPanel;
    private final JLabel topLabel;
    private final JPanel glassPane;
    private JTextField infoField;
    private JLabel glassLabel1;
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
        int f_HEIGHT = 600;//Minimum 400
        int f_WIDTH = 800;
        glassPane.setSize(f_WIDTH, f_HEIGHT);
        glassPane.setLayout(new BorderLayout());
        String loading = "/textures/loading.gif";
        glassLabel1 = new JLabel(new ImageIcon(getClass().getResource(loading)));
        infoField = new JTextField();
        infoField.setFont(new Font("Sans", Font.PLAIN, 40));
        infoField.setHorizontalAlignment(JTextField.CENTER);
        infoField.setEditable(false);
        infoField.setOpaque(false);
        infoField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        glassPane.add(infoField, BorderLayout.SOUTH);
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
        contentPanel.add(tablePanel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        contentPanel.setVisible(true);
        this.getContentPane().add(contentPanel);
        pack();
        setVisible(true);
        startLoadingOverlay(false);
        tablePanel.setVisible(false);
        infoPanel.setVisible(false);
    }

    /**
     * Creates panels.
     */
    private void createPanels() {
        northPanel = new JPanel(new BorderLayout());
        tablePanel = new TablePanel();
        tablePanel.setPreferredSize(new Dimension(600, 600));
        infoPanel = new InfoPanel();
        //tablePanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(300,300));
    }


    /**
     * Gets menuBar.
     * @return menuBar
     */

    public MenuBar getMyMenuBar() {
        return menuBar;
    }

    /**
     * Gets the table.
     */
    public TablePanel getMyTable() {
        return (TablePanel) tablePanel;
    }

    /**
     * Gets the infoPanel
     * @return infoPanel
     */
    public InfoPanel getInfoPanel() {
        return (InfoPanel) infoPanel;
    }

    /**
     * Sets the header
     * @param url image to load
     */
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

    /**
     * Starts overlay
     * @param opaque transparent or not
     */
    public void startLoadingOverlay(boolean opaque) {
        glassPane.setOpaque(opaque);
        glassPane.setVisible(true);
        repaint();
    }

    /**
     * Stps overlay
     */
    public void stopLoadingOverlay() {
        glassPane.setVisible(false);
        repaint();
    }

    /**
     * Scales the image
     * @param bufImg Buffered image
     * @param w width
     * @param h height
     * @return Scaled ImageIcon
     */
    @SuppressWarnings("SameParameterValue")
    private ImageIcon scaleV2(BufferedImage bufImg, int w, int h){
        Image render = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(render);

    }

    /**
     * Enables content.
     */
    public void enableContent() {
        tablePanel.setVisible(true);
        infoPanel.setVisible(true);
    }

    /**
     * Set info text, for errors.
     */
    public void setText(String s) {
        //glassLabel1.setVisible(false);
        infoField.setText(s + "\n");
        infoField.setVisible(true);
        glassPane.setVisible(true);
        repaint();
    }

    /**
     * Clears text, for errors.
     */
    public void clearText() {
        infoField.setText(null);
        infoField.setVisible(false);
        glassLabel1.setVisible(true);
        glassPane.setVisible(false);
        repaint();
    }
}
