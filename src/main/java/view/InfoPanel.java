package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Infopanel for showing description and image.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */

public class InfoPanel extends JPanel {
    private ImageIcon image;
    private final JLabel label;
    private final JTextArea info;


    public InfoPanel() {
        setLayout(new BorderLayout());

        BufferedImage image;
        ImageIcon image2 = null;
        try {
            String sr = "/home/tfy17jfo/IdeaProjects/RadioInfo-Appjava/src/main/resources/textures/sr.jpg";
            image = ImageIO.read(new File(sr));
            image2 = scaleV2(image, 300,300,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        label = new JLabel(image2);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        info = new JTextArea();
        info.append("Tryck på en kanal för att se information.");
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(info);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        info.setFont(new Font("Sans", Font.PLAIN, 17));
        //setPreferredSize(new Dimension(400,150));
        add(label, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        setBackground(Color.WHITE);
    }

    /**
    * Sets info about program.
    */
    public void setDescription(String text) {
        info.setText("\n" + text);
    }

    public void setImage(URL url) {
        try {
            BufferedImage image = ImageIO.read(url);
            if (label.getHeight() > label.getWidth()) {
                ImageIcon img1 = scaleV1(image, label.getWidth(),label.getWidth(), false);
                label.setIcon(img1);
            } else {
                scaleV1(image, label.getWidth(), label.getWidth(),false);
                ImageIcon img1 = scaleV1(image, label.getHeight(),label.getHeight(), false);
                label.setIcon(img1);
            }

            this.label.setBackground(new Color(image.getRGB(0,0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(String name) {
        try {
            BufferedImage image = ImageIO.read(new File(name));
            if (label.getHeight() > label.getWidth()) {
                ImageIcon image2 = scaleV1(image, label.getWidth(), label.getWidth(), false);
                label.setIcon(image2);
            } else {
                ImageIcon image2 = scaleV1(image, label.getHeight(), label.getHeight(), false);
                label.setIcon(image2);
            }
            label.setBackground(new Color(image.getRGB(0, 0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageIcon scaleV2(BufferedImage bufImg, int w, int h, boolean fast){
        Image render;
        if (fast) {
            render = bufImg.getScaledInstance(w, h, Image.SCALE_FAST);
        } else {
            render = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        }
        return new ImageIcon(render);
    }

    private ImageIcon scaleV1(Image img, int w, int h, boolean fast){
        BufferedImage scaledImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImg.createGraphics();
        if (fast) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        } else {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(scaledImg);
    }
}
