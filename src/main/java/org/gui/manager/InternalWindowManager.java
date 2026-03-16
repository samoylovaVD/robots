package org.gui.manager;

import org.controller.Shutdownable;

import javax.swing.*;
import java.awt.*;

import java.util.List;

public class InternalWindowManager {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public InternalWindowManager(List<JInternalFrame> frames) {
        for (JInternalFrame frame: frames) addWindow(frame);
    }

    public void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }


    public void shutdownWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof Shutdownable f) f.shutdown();
            frame.dispose();
        }
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}
