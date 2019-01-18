import javax.swing.*;

import controller.Controller;

/**
 * Program for testing classes in a similar fashion to
 * testing them using the JUnit framework.
 * @UserID - tfy17jfo
 * @date - 2018-11-21
 * @version: 1.0
 * @author Jakob Fridesjo
 */
class RadioInfo	{
    public static void main(String[] args) {
        try {
            //Set look and feel according to system
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Controller::new);
    }
}
