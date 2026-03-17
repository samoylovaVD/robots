package org.controller;

import org.service.Logger;
import org.gui.view.View;

import javax.swing.*;

/**
 * Главный контроллер приложения.
 * Реагирует на {@link Action} и дает пользователю фидбек на его действия.
 */
public class ApplicationController {
    private View view;

    /**
     * Сеттер для {@link View}
     * @param view экземпляр {@link View}, через который контроллер будет управлять отображением
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Инициирует выход из приложения.
     * Вызывает {@link View#confirmExit()} для запроса подтверждения у пользователя.
     * Если пользователь согласен, вызывает {@link View#shutdown()} для корректного закрытия всех окон.
     */
    public void requestExit() {
        if (view.confirmExit()) {
            view.shutdown();
        }
    }

    /**
     * Меняет Look & Feel на системный.
     * После смены вызывает {@link View#updateUI()} для обновления интерфейса.
     */
    public void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            view.updateUI();
        } catch (Exception ignored) {
        }
    }

    /**
     * Меняет Look & Feel на кроссплатформенный.
     * После смены вызывает {@link View#updateUI()} для обновления интерфейса.
     */
    public void setCrossPlatformLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            view.updateUI();
        } catch (Exception ignored) {
        }
    }

    /**
     * Логирует переданное сообщение через {@link Logger}.
     * @param message текст сообщения для логирования
     */
    public void logMessage(String message) {
        Logger.debug(message);
    }
}
