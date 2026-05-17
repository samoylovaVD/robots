/** отвечает за отображение игрового поля с роботом и целью,
 * а также за обработку кликов мыши для установки новой цели
 */

package org.gui.panel;

import org.controller.RobotController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class GameVisualizer extends JPanel {
//через ссылку панель получает координаты для отрисовки и устанавливает новую цель
    private final RobotController controller;

//    конструктор принимает контроллер извне
    public GameVisualizer(RobotController controller) {
        this.controller = controller;
//        обработчик кликов
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.setTarget(e.getPoint());   // View не трогает модель напрямую
                repaint();
            }
        });
//        двойная буферизация для устранения мерцания при перерисовке
        setDoubleBuffered(true);
    }

//    переопред метод рисования
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
//запрашиваем нужные данные (координаты и направл)
//контроллер получает данные из модели, но только предоставляет геттеры
        drawRobot(g2d,
                (int) Math.round(controller.getRobotX()),
                (int) Math.round(controller.getRobotY()),
                controller.getRobotDirection()
        );
        drawTarget(g2d, controller.getTargetX(), controller.getTargetY());
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);
        g.setTransform(new AffineTransform());
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setTransform(new AffineTransform());
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private static void fillOval(Graphics g, int cx, int cy, int w, int h) {
        g.fillOval(cx - w/2, cy - h/2, w, h);
    }

    private static void drawOval(Graphics g, int cx, int cy, int w, int h) {
        g.drawOval(cx - w/2, cy - h/2, w, h);
    }

    public void shutdown() {
        controller.shutdown();
    }
}