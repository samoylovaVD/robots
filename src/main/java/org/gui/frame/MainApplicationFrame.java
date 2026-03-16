package org.gui.frame;

import org.gui.menu.ApplicationMenuBar;
import org.gui.manager.InternalWindowManager;
import org.gui.view.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;


public class MainApplicationFrame extends JFrame implements View {
    private static final int FRAME_INSET = 50;

    private final InternalWindowManager windowManager;

    public MainApplicationFrame(
            ApplicationMenuBar menuBar,
            InternalWindowManager windowManager
    ) {
        this.windowManager = windowManager;

        setJMenuBar(menuBar);
        setUpFrameBounds();
        setContentPane(windowManager.getDesktopPane());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void setUpFrameBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(
                FRAME_INSET,
                FRAME_INSET,
                screenSize.width  - FRAME_INSET * 2,
                screenSize.height - FRAME_INSET * 2
        );
    }

    public void shutdown() {
        windowManager.shutdownWindows();
        dispose();
    }

    @Override
    public void updateUI() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    public boolean confirmExit() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Вы действительно хотите выйти?",
                "Выполняется выход...",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
}
