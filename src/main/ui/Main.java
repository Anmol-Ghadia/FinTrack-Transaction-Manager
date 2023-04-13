package ui;

import javax.swing.*;

/**
 *   Main class of project
 *   Run this for the app
 */
public class Main {
    public static void main(String []args) {
//        new FinTrack();
        SwingUtilities.invokeLater(new GuiSwing());
    }
}
