package org.service.state;

import org.gui.view.WindowIdentifiable;
import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.prefs.Preferences;

public class PreferencesWindowStateService implements WindowStateService {
    private final Preferences prefs = Preferences.userNodeForPackage(PreferencesWindowStateService.class);

    @Override
    public void save(JDesktopPane desktopPane) {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String key;
            if (frame instanceof WindowIdentifiable wid) {
                key = wid.getWindowId();      // приоритет – фиксированный ID
            } else {
                key = frame.getName();        // fallback (можно удалить, если все окна реализуют интерфейс)
            }
            if (key == null) continue;

            prefs.putInt(key + "_x", frame.getX());
            prefs.putInt(key + "_y", frame.getY());
            prefs.putInt(key + "_width", frame.getWidth());
            prefs.putInt(key + "_height", frame.getHeight());
            prefs.putBoolean(key + "_isMaximum", frame.isMaximum());
            prefs.putBoolean(key + "_isIcon", frame.isIcon());
        }
    }

    @Override
    public void restore(JDesktopPane desktopPane) {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String key;
            if (frame instanceof WindowIdentifiable wid) {
                key = wid.getWindowId();
            } else {
                key = frame.getName();
            }
            if (key == null) continue;

            int x = prefs.getInt(key + "_x", frame.getX());
            int y = prefs.getInt(key + "_y", frame.getY());
            int width = prefs.getInt(key + "_width", frame.getWidth());
            int height = prefs.getInt(key + "_height", frame.getHeight());
            boolean isMaximum = prefs.getBoolean(key + "_isMaximum", frame.isMaximum());
            boolean isIcon = prefs.getBoolean(key + "_isIcon", frame.isIcon());

            frame.setBounds(x, y, width, height);
            try {
                frame.setMaximum(isMaximum);
                frame.setIcon(isIcon);
            } catch (PropertyVetoException ignored) {
                // логируем ошибку, не игнорируем полностью
                // можно добавить Logger.error(...)
            }
        }
    }
}