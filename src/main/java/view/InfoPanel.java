package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * InfoPanel for showing description and image.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */


public class InfoPanel extends JPanel {
    private final JLabel label;
    private final JTextArea info;

    /**
     * Constructor for InfoPanel
     */
    InfoPanel() {
        setLayout(new BorderLayout());

        BufferedImage image;
        ImageIcon image2 = null;
        try {
            final String sr = "/textures/sr.jpg";
            image = ImageIO.read(getClass().getResource(sr));
            image2 = scaleV2(image, 200,200,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        label = new JLabel(image2);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        info = setupTextArea();
        info.setFont(new Font("Sans", Font.PLAIN, 17));
        JScrollPane scrollPane = setupScrollPane(info);
        add(label, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        setBackground(Color.WHITE);
    }

    /**
     * Setups a scroll pane.
     * @return JScrollPane
     */
    private JScrollPane setupScrollPane(JTextArea info) {
        JScrollPane scrollPane = new JScrollPane(info);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        return scrollPane;
    }

    /**
     * Setups a text area.
     * @return JTextArea
     */
    private JTextArea setupTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.append("Tryck på en kanal för att se information.");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        return textArea;
    }
    /**
    * Sets info about program.
    */
    public void setDescription(String text) {
        info.setText("\n" + text);
    }

    /**
     * Sets image from url
     * @param url url to load
     * @throws IOException if image not loadable
     */
    public void setImage(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        if (label.getHeight() > label.getWidth()) {
            ImageIcon img1 = scaleV1(image, label.getWidth(),label.getWidth(),
                    false);
            label.setIcon(img1);
        } else {
            scaleV1(image, label.getWidth(), label.getWidth(),false);
            ImageIcon img1 = scaleV1(image, label.getHeight(),label.getHeight(),
                    false);
            label.setIcon(img1);
        }
        this.label.setBackground(new Color(image.getRGB(0,0)));
    }

    /**
     * Sets image from path.
     * @param name path
     * @throws IOException if image not loadable
     */
    public void setImage(String name) throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResource(name));
        if (label.getHeight() > label.getWidth()) {
            ImageIcon image2 = scaleV1(image,label.getWidth(),label.getWidth(),
                    false);
            label.setIcon(image2);
        } else {
            ImageIcon image2 = scaleV1(image,label.getHeight(),label.getHeight()
                    , false);
            label.setIcon(image2);
        }
        label.setBackground(new Color(image.getRGB(0, 0)));
    }

    /**
     * Scales images smoothly
     * @param bufImg Buffered image
     * @param w width
     * @param h height
     * @param fast algorithm
     * @return Scaled ImageIcon
     */
    @SuppressWarnings("SameParameterValue")
    private ImageIcon scaleV2(BufferedImage bufImg, int w, int h, boolean fast){
        Image render;
        if (fast) {
            render = bufImg.getScaledInstance(w, h, Image.SCALE_FAST);
        } else {
            render = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        }
        return new ImageIcon(render);
    }

    /**
     * Scales images sharply
     * @param img image
     * @param w width
     * @param h height
     * @param fast algorithm
     * @return Scaled ImageIcon
     */
    @SuppressWarnings("SameParameterValue")
    private ImageIcon scaleV1(Image img, int w, int h, boolean fast){
        BufferedImage scaledImg = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImg.createGraphics();
        if (fast) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        } else {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(scaledImg);
    }
}
