/** окно для отображения логов
        Отображает текстовые сообщения из буфера логов в реальном времени,
        подписываясь на уведомления об изменениях **/

package org.gui.internal;

import org.observer.LogChangeListener;
import org.model.LogEntry;
import org.service.logging.LogBuffer;
import org.gui.view.WindowIdentifiable;

import javax.swing.*;
import java.awt.*;

public class LogWindow extends JInternalFrame implements LogChangeListener, WindowIdentifiable {
    private final LogBuffer logSource; //ссылка на буфер логов
    private final JTextArea logContent; //текстовое поле для отображения логов

//    Конструктор принимает источник логов извне
    public LogWindow(LogBuffer logSource) {
        super("Протокол работы", true, true, true, true);
//        сохр ссылку на буфер и регистрируем текущее окно как слушателя
        this.logSource = logSource;
        logSource.registerListener(this);
        logContent = new JTextArea();
        logContent.setEditable(false); // запрещаем редактирование
        logContent.setFont(new Font("Monospaced", Font.PLAIN, 12)); // моноширинный шрифт
        JScrollPane scrollPane = new JScrollPane(logContent); // добавляем полосы прокрутки
        scrollPane.setPreferredSize(new Dimension(400, 300));
        setContentPane(scrollPane); // помещаем скролл-панель в окно
        pack(); //сжимает окно до предпочтительного размера
        updateLogContent(); //заполняет текстовое поле существующими сообщениями из буфера
    }

//    проходит по все сообщ в буфере и склеивает в 1 стр
    private void updateLogContent() {
        StringBuilder sb = new StringBuilder();
        for (LogEntry entry : logSource.all()) {
            sb.append(entry.getMessage()).append("\n");
        }
        logContent.setText(sb.toString());
    }

    @Override
    public void onLogChanged() {
//        гарантирует, что обновление UI произойдёт в потоке обработки событий Swing
        EventQueue.invokeLater(this::updateLogContent);
    }

//    возвр постоянный идентификатор окна, исп для сохр позиции и размера окна
    @Override
    public String getWindowId() {
        return "log_window";   // фиксированный идентификатор
    }
}