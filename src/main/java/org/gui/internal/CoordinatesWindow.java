package org.gui.internal;

import org.model.RobotCoordinates;

import javax.swing.*;
import java.awt.*;

/**
 * Окно для отображения текущих координат робота
 */

public class CoordinatesWindow extends JInternalFrame implements RobotCoordinates.CoordinatesListener{

    private final JLabel xLabel;
    private final JLabel yLabel;
    private final JLabel directionLabel;
    private final RobotCoordinates robotCoordinates;

    public CoordinatesWindow(RobotCoordinates robotCoordinates){
        super("Координаты робота", true, true, true, true);
        this.robotCoordinates = robotCoordinates;

        this.robotCoordinates.addListener(this);

        setSize(250, 150);
        setLocation(320, 10);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(new JLabel("X координата:"));
        xLabel = new JLabel("100.00");
        panel.add(xLabel);

        panel.add(new JLabel("Y координата:"));
        yLabel = new JLabel("100.00");
        panel.add(yLabel);

        panel.add(new JLabel("Направление (рад):"));
        directionLabel = new JLabel("0.00");
        panel.add(directionLabel);

        add(panel);

        // Устанавливаем имя для сохранения состояния
        setName("coordinatesWindow");
    }

    @Override
    public void onCoordinatesChanged(double x, double y, double direction) {
        // Обновляем отображение в EDT
        SwingUtilities.invokeLater(() -> {
            xLabel.setText(String.format("%.2f", x));
            yLabel.setText(String.format("%.2f", y));
            directionLabel.setText(String.format("%.2f", direction));
        });
    }

    @Override
    public void dispose() {
        // Отписываемся от обновлений при закрытии окна
        robotCoordinates.removeListener(this);
        super.dispose();
    }
}

