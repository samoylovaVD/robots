package org.controller;

import org.service.Logger;
import org.gui.view.View;
import javax.swing.JOptionPane;

import javax.swing.*;

public class ApplicationController {
    private View view;

    public void setLookAndViewUpdater(View view) {
        this.view = view;
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

    // Логирование
    public void logMessage(String message) {
        Logger.debug(message);
    }

    public void exitApplication() {
        int result = JOptionPane.showConfirmDialog(null,
                "Вы действительно хотите выйти?",
                "Выполняется выход...",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
