package org.gui.frame;

import org.controller.ApplicationController;
import org.controller.actions.ExitAction;
import org.gui.menu.ApplicationMenuBar;
import org.gui.manager.InternalWindowManager;
import org.gui.view.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


/**
 * Главное окно приложения.
 * Является тонкой GUI-прослойкой и реализует интерфейс {@link View} в архитектуре MVC.
 * Отвечает за отображение {@link InternalWindowManager}, меню и обработку закрытия окна.
 */
public class MainApplicationFrame extends JFrame implements View {
    private static final int FRAME_INSET = 50;

    private final InternalWindowManager windowManager;
    private final ApplicationController controller;

    private final ExitAction exitAction;

    /**
     * Создает главное окно приложения
     * @param menuBar панель меню приложения
     * @param windowManager менеджер внутренних окон
     * @param controller контроллер приложения, через который выполняются действия
     */
    public MainApplicationFrame(
            ApplicationMenuBar menuBar,
            InternalWindowManager windowManager,
            ApplicationController controller
    ) {
        this.windowManager = windowManager;
        this.controller = controller;

        exitAction = new ExitAction(controller);

        // Устанавливаем главную панель меню
        setJMenuBar(menuBar);
        // Задаем размеры
        setUpFrameBounds();
        setContentPane(windowManager.getDesktopPane());
        // Здесь мы хотим предотвратить поведение по умолчанию при нажатии "крестика" в углу GUI и поставить свой кастомный обработчик
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
    }

    /**
     * Устанавливает размеры окна.
     * Размер задается с учетом отступа {@link #FRAME_INSET} от краев экрана
     */
    private void setUpFrameBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(
                FRAME_INSET,
                FRAME_INSET,
                screenSize.width  - FRAME_INSET * 2,
                screenSize.height - FRAME_INSET * 2
        );
    }

    /**
     * Закрывает главное окно и внутренние окна
     */
    @Override
    public void shutdown() {
        windowManager.saveState();
        windowManager.shutdownWindows();
        dispose();
    }

    /**
     * Рекурсивно перерисовывает UI
     */
    @Override
    public void updateUI() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Отображает диалог подтверждения выхода
     * @return  {@code true}, если пользователь подтвердил выход (нажал «Да»), иначе {@code false}
     */
    @Override
    public boolean confirmExit() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Вы действительно хотите выйти?",
                "Выполняется выход...",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
}
