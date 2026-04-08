package org.gui.internal;

import org.gui.view.Shutdownable;
import org.gui.panel.GameVisualizer;
import org.model.RobotCoordinates;

import java.awt.BorderLayout;

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
    public void setRobotCoordinates(RobotCoordinates robotCoordinates) {
        m_visualizer.setRobotCoordinates(robotCoordinates);
    }

    @Override
    public void shutdown() {
        m_visualizer.shutdown();
        super.dispose();
    }
}
