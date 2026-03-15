package org.gui.menu;

import org.controller.ApplicationController;
import org.controller.actions.CrossplatformLookAndFeelAction;
import org.controller.actions.LogMessageAction;
import org.controller.actions.SystemLookAndFeelAction;
import org.controller.actions.ExitAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class ApplicationMenuBar extends JMenuBar {
    private final ApplicationController controller;

    public ApplicationMenuBar(ApplicationController controller) {
        this.controller = controller;

        add(createFileMenu());
        add(createLookAndFeelMenu());
        add(createTestMenu());
    }

    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        lookAndFeelMenu.add(createSystemLookAndFeelMenuItem());
        lookAndFeelMenu.add(createCrossplatformLookAndFeelMenuItem());
        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        testMenu.add(new JMenuItem(new LogMessageAction(controller)));

        // Новый пункт для вызова JFileChooser
        JMenuItem fileChooserItem = new JMenuItem("Открыть файл...");
        fileChooserItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
        });
        testMenu.add(fileChooserItem);
        return testMenu;
    }

    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Команды управления файлом");
        fileMenu.add(new JMenuItem(new ExitAction(controller)));
        return fileMenu;
    }

    private JMenuItem createAddLogMessageItem() {
        return new JMenuItem(new LogMessageAction(controller));
    }

    private JMenuItem createSystemLookAndFeelMenuItem()
    {
        return new JMenuItem(new SystemLookAndFeelAction(controller));
    }

    private JMenuItem createCrossplatformLookAndFeelMenuItem() {
        return new JMenuItem(new CrossplatformLookAndFeelAction(controller));
    }
}
