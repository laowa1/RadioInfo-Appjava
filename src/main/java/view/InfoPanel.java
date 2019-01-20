package view;

import com.sun.jndi.toolkit.url.Uri;
import model.ProgramInfo;
import javax.swing.*;
import java.awt.*;
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
    private JTextArea info;

    public InfoPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        this.image = new
                     ImageIcon("/home/jakob/Desktop/RadioInfo/src/main/resources/textures/sr.png");
        label = new JLabel(this.image);
        info = new JTextArea();
        info.setFont(new Font("Serif", Font.PLAIN, 14));
        info.append("Ett program som läser\nin kanaler och visar\n");
        info.append("tidstablå, tryck på\nmenyn för val. Du kan\n");
        info.append("välja att uppdatera\neller välja en kanal.\n");
        this.setVisible(true);
        add(label, BorderLayout.EAST);
        add(info, BorderLayout.WEST);
    }

    /**
    * Sets info about program.
    */
    public void setProgramInfo(ProgramInfo pI) {
        info.setText(pI.getTagLine());
        if (pI.getImageURL() != null) {
            this.image = new ImageIcon(
                             getClass().getResource("/textures/sr.png"));
        } else {
            this.image = new ImageIcon(pI.getImageURL().getFile());
        }
    }
}
