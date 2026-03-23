package org.gui.manager;

import org.gui.view.Shutdownable;
import org.service.state.WindowStateService;

import javax.swing.*;

import java.util.List;

/**
 * Менеджер внутренних окон.
 * Отвечает за создание, хранение и управление {@link JInternalFrame} внутри {@link JDesktopPane}.
 * Поддерживает корректное завершение работы окон через интерфейс {@link Shutdownable}.
 */
public class InternalWindowManager {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowStateService stateService;

    /**
     * Создаёт InternalWindowManager и добавляет переданные внутренние окна.
     * @param frames список внутренних окон ({@link JInternalFrame}), которые нужно добавить.
     */
    public InternalWindowManager(List<JInternalFrame> frames, WindowStateService stateService) {
        this.stateService = stateService;
        for (JInternalFrame frame: frames) addWindow(frame);
    }

    /**
     * Добавляет внутреннее окно в {@link JDesktopPane} и делает его видимым.
     * @param frame внутренне окно ({@link JInternalFrame}) для добавления.
     */
    public void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Закрывает все внутренние окна.
     * Если у компонента реализован интерфейс {@link Shutdownable}, то запуск идет рекурсивно.
     */
    public void shutdownWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof Shutdownable f) f.shutdown();
            frame.dispose();
        }
    }

    /**
     * Сохраняет состояние внутренних окон при помощи отдельного сервиса {@link WindowStateService}.
     * Под состоянием подразумевается x-координата, y-координата, длина, высота и свернуто ли окно.
     */
    public void saveState() {
        stateService.save(desktopPane);
    }

    /**
     * Возвращает состояние открытых внутренних окон с последней сессии
     * при помощи отдельного сервиса {@link WindowStateService}.
     * Под состоянием подразумевается x-координата, y-координата, длина, высота и свернуто ли окно.
     */
    public void restoreState() {
        stateService.restore(desktopPane);
    }

    /**
     * Возвращает {@link JDesktopPane}, который хранит внутренние окна.
     * @return панель рабочего стола ({@link JDesktopPane}) с внутренними окнами
     */
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}
