// устанавливает общий вид, локализацию, запускает инициализацию в потоке обработки событий

package org.app;

import org.controller.ApplicationController;
import org.gui.frame.MainApplicationFrame;
import org.gui.internal.GameWindow;
import org.gui.internal.LogWindow;
import org.gui.manager.InternalWindowManager;
import org.gui.menu.ApplicationMenuBar;
import org.gui.menu.MenuManager;
import org.service.Logger;
import org.service.state.PreferencesWindowStateService;
import org.model.RobotModel;

import java.awt.*;
import java.util.List;
import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        setDefaultLookAndFeel();
        localizeApp();
        SwingUtilities.invokeLater(RobotsProgram::initializeApp);
    }

//сборщик зависимостей

    private static void initializeApp() {
//        главный контроллер прилаги
        ApplicationController controller = new ApplicationController();
//        Сервис сохранения состояния окон
        PreferencesWindowStateService stateService = new PreferencesWindowStateService();

        MenuManager menuManager = new MenuManager(controller);
        ApplicationMenuBar menuBar = new ApplicationMenuBar(menuManager);
//        Менеджер внутренних окон в кот передаются созданные окна игры и логов
        InternalWindowManager windowManager = new InternalWindowManager(
                List.of(

                        createGameWindow(),
                        createLogWindow()
                ),
                stateService
        );
//        Отображает главное окно на весь экран и восстанавливает тож
        MainApplicationFrame frame = new MainApplicationFrame(
                menuBar,
                windowManager,
                controller
        );
        controller.setView(frame);

        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        windowManager.restoreState();
    }
//    вспомогательный метод для настройки UI и локализации
    private static void setDefaultLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.error("Failed to set system look and feel: " + e.getMessage());
        }
    }

    private static void localizeApp() {
        Locale.setDefault(Locale.of("ru"));
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.okButtonText", "Oк");
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть выбранный файл");
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить файл");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отменить выбор");
        UIManager.put("FileChooser.directoryDescriptionText", "Папка");
        UIManager.put("FileChooser.fileDescriptionText", "Файл");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла:");
        UIManager.put("FileChooser.folderNameLabelText", "Имя папки:");
        UIManager.put("FileChooser.lookInLabelText", "Папка:");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить в:");
        UIManager.put("FileChooser.upFolderToolTipText", "На уровень вверх");
        UIManager.put("FileChooser.homeFolderToolTipText", "Домашняя папка");
        UIManager.put("FileChooser.newFolderToolTipText", "Создать папку");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблица");
    }

//    создаёт окно логов, подписывает его на источник логов
    private static LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setPreferredSize(new Dimension(300, 200));
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

//    создаёт модель робота,передаёт её в конструктор GameWindow,
//    а затем настраивает имя и размер окна
    private static GameWindow createGameWindow() {
        RobotModel robotModel = new RobotModel();   // создаём модель
        GameWindow gameWindow = new GameWindow(robotModel);
        gameWindow.setName("gameWindow");
        gameWindow.setSize(400, 400);
        return gameWindow;
    }
}