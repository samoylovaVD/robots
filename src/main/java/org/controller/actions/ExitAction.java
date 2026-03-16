package org.controller.actions;

import org.controller.ApplicationController;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {
    private final ApplicationController controller;

    public ExitAction(ApplicationController controller){
        super("Выход");
        this.controller= controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.requestExit();
    }
}
