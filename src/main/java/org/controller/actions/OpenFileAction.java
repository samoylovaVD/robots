package org.controller.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Тестовая реализация Action, который вызывает окно выбора файла пользователем
 */
public class OpenFileAction extends AbstractAction {

    public OpenFileAction() {
        super("Открытие файла");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
    }
}