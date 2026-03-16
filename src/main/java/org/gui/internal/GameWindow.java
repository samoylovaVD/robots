package org.gui.internal;

import org.controller.Shutdownable;
import org.gui.panel.GameVisualizer;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements Shutdownable {
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
    public GameVisualizer getVisualizer() {
        return m_visualizer;
    }

    @Override
    public void shutdown() {
        m_visualizer.shutdown();
    }
}
