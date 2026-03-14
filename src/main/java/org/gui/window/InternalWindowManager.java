package org.gui.window;

import org.gui.GameWindow;
import org.gui.LogWindow;
import org.log.Logger;

import javax.swing.*;
import java.awt.*;

public class InternalWindowManager {
    private final JDesktopPane desktopPane;

    public InternalWindowManager(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    public void initializeWindows() {
        addWindow(createGameWindow());
        addWindow(createLogWindow());
    }

    public void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setPreferredSize(new Dimension(300, 800));
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        return gameWindow;
    }
}
