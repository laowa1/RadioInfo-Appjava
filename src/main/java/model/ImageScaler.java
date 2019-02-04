package model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class for scaling images
 * @UserID - tfy17jfo
 * @date - 2019-02-02
 * @version 2.0
 * @author Jakob Fridesjö
 */
public class ImageScaler {

    ImageScaler() {}

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
