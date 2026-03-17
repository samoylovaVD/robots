package org.gui.menu;

import javax.swing.*;


/**
 * Основная панель меню приложения.
 * Использует {@link MenuManager} для формирования меню
 */
public class ApplicationMenuBar extends JMenuBar {
    /**
     * Создает ApplicationMenuBar и инициализирует необходимые меню, используя {@link MenuManager}
     * @param menuManager менеджер меню
     */
    public ApplicationMenuBar(MenuManager menuManager) {
        add(menuManager.createFileMenu());
        add(menuManager.createLookAndFeelMenu());
        add(menuManager.createTestMenu());
    }
}
