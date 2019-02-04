import javax.swing.*;

import controller.Controller;

/**
 * Program for showing Radio schedules from Swedish Radio SR
 * @UserID - tfy17jfo
 * @date - 2018-11-21
 * @version: 1.0
 * @author Jakob Fridesjo
 */
class RadioInfo	{
    public static void main(String[] args) {
        try {
            //Set look and feel according to system
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Controller::new);
    }
}
