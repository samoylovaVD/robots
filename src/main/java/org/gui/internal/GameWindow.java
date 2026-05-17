package org.gui.internal;

import org.gui.view.Shutdownable;
import org.gui.view.WindowIdentifiable;  // добавить
import org.gui.panel.GameVisualizer;
import org.model.RobotModel;
import org.controller.RobotController;

import javax.swing.*;

/** окно игрового поля — дочернее окно внутри главного приложения
отвечает за отображение графики робота и цели,
а также за управление логикой движения робота через контроллер **/

public class GameWindow extends JInternalFrame implements Shutdownable, WindowIdentifiable {
    private final GameVisualizer visualizer;

//    принимает модель робота RobotModel извне
//    окно не создаёт модель само, а получает уже готовую
    public GameWindow(RobotModel model) {
        super("Игровое поле", true, true, true, true);
        RobotController controller = new RobotController(model);
//        Создаётся панель визуализатора, кот получ контроллер
//        визуализатор только читает данные через контроллер и перерисовывает
        visualizer = new GameVisualizer(controller);
//        устанавл коллбэк.
//        заствал окно перересовываться с новми координатами робота
        controller.setRepaintCallback(visualizer::repaint);
//        запуск таймеров контроллера
        controller.start();
        setContentPane(visualizer);
        visualizer.setBorder(null);
        setSize(400, 400);
    }

    @Override
    public void shutdown() {
        visualizer.shutdown();
    }
//    возвр постоянный идентификатор окна
    @Override
    public String getWindowId() {
        return "game_window";   // фиксированный идентификатор
    }
}