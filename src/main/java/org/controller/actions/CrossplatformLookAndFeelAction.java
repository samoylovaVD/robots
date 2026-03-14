package org.controller.actions;

import org.controller.ApplicationController;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class CrossplatformLookAndFeelAction extends AbstractAction {
    private final ApplicationController controller;

    public CrossplatformLookAndFeelAction(ApplicationController controller) {
        super("Универсальная схема");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.setCrossPlatformLookAndFeel();
    }
}
