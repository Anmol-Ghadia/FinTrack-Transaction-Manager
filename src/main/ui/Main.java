package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.util.Iterator;

public class Main {
    public static void main(String []args) {
//        new FinTrack();
        SwingUtilities.invokeLater(new GuiSwing());
    }
}
