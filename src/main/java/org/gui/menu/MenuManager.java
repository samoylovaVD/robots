package org.gui.menu;

import org.controller.ApplicationController;
import org.controller.actions.*;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Менеджер меню.
 * Отвечает за формирование отдельных {@link JMenu},
 * которые затем могут использоваться в различных {@link JMenuBar}
 */
public class MenuManager {
    private final ApplicationController controller;

    /**
     * Создает MenuManager с привязкой к контроллеру приложения.
     * @param controller контроллер, через который выполняются действия меню.
     */
    public MenuManager(ApplicationController controller) {
        this.controller = controller;
    }

    /**
     * Создает меню "Файл".
     * Содержит команды:
     *     - Выход - команда выхода из приложения
     * @return созданное {@link JMenu} с пунктами меню
     */
    public JMenu createFileMenu() {
        return new MenuBuilder(
                    "Файл",
                    KeyEvent.VK_F,
                    "Команды управления файлом")
                .item(new ExitAction(controller))
                .build();
    }

    /**
     * Создает меню "Режим отображения".
     * Содержит команды, связанные с внешним видом приложения:
     *     - {@link SystemLookAndFeelAction} - переключает UI на системную схему оформления
     *     - {@link CrossplatformLookAndFeelAction} - переключает UI на кроссплатформенную схему оформления
     * @return созданное {@link JMenu} с пунктами меню
     */
    public JMenu createLookAndFeelMenu() {
        return new MenuBuilder(
                    "Режим отображения",
                    KeyEvent.VK_V,
                    "Управление режимом отображения приложения")
                .item(new SystemLookAndFeelAction(controller))
                .item(new CrossplatformLookAndFeelAction(controller))
                .build();
    }

    /**
     * Создает меню "Тест".
     * Содержит тестовые команды, необходимые в отладке:
     *     - {@link LogMessageAction} - Отправляет тестовое сообщение через логгер
     *     - {@link OpenFileAction} - Предлагает пользователю выбрать файл
     * @return созданное {@link JMenu} с пунктами меню
     */
    public JMenu createTestMenu() {
        return new MenuBuilder(
                    "Тест",
                    KeyEvent.VK_T,
                    "Тестовые команды")
                .item(new LogMessageAction(controller))
                .item(new OpenFileAction(controller))
                .build();
    }

    /**
     * Вспомогательный паттерн Builder для удобного создания меню.
     * Позволяет пошагово добавлять пункты {@link Action} и собирать {@link JMenu}.
     */
    private static class MenuBuilder {
        private final JMenu menu;

        MenuBuilder(String title, int mnemonic, String description) {
            menu =  new JMenu(title);
            menu.setMnemonic(mnemonic);
            menu.getAccessibleContext().setAccessibleDescription(description);
        }

        MenuBuilder item(Action action) {
            menu.add(new JMenuItem(action));
            return this;
        }

        JMenu build() {
            return menu;
        }
    }
}
