package org.controller.actions;

import org.controller.ApplicationController;

import javax.swing.*;
import java.awt.event.ActionEvent;


/**
 * Action, который логирует текст сообщения (в данной реализации сообщение - hardcode)
 */
public class LogMessageAction extends AbstractAction {
    private final ApplicationController controller;

    public LogMessageAction(ApplicationController controller) {
        super("Сообщение в лог");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.logMessage("Новая строка");
    }
}
