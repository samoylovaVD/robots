package org.gui.view;

public interface View {
    void shutdown();
    void updateUI();
    boolean confirmExit();
}
