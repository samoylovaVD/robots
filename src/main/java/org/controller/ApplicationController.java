package org.controller;

import org.service.Logger;
import org.gui.view.View;

import javax.swing.*;

public class ApplicationController {
    private View view;

    public void setView(View view) {
        this.view = view;
    }

    public void requestExit() {
        if (view.confirmExit()) {
            view.shutdown();
        }
    }

    // Сменить Look & Feel на системный
    public void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            view.updateUI();
        } catch (Exception ignored) {
        }
    }

    // Сменить Look & Feel на кроссплатформенный
    public void setCrossPlatformLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            view.updateUI();
        } catch (Exception ignored) {
        }
    }

    public void logMessage(String message) {
        Logger.debug(message);
    }
}
