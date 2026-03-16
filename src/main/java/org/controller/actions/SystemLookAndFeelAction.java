package org.controller.actions;

import org.controller.ApplicationController;

import javax.swing.*;
import java.awt.event.ActionEvent;


/**
 * Action, переключающий внешний вид интерфейса на системную схему оформления.
 */
public class SystemLookAndFeelAction extends AbstractAction {
    private final ApplicationController controller;

    public SystemLookAndFeelAction(ApplicationController controller) {
        super("Системная схема");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.setSystemLookAndFeel();
    }
}
