package org.service.logging;

import org.model.LogEntry;
import org.model.LogLevel;
import org.observer.LogChangeListener;

import java.lang.ref.WeakReference;
import java.util.*;

public class LogBuffer {
    private final int queueLength;
    private final Deque<LogEntry> messages = new ArrayDeque<>();
    private final List<WeakReference<LogChangeListener>> listeners;

    public LogBuffer(int queueLength) {
        this.queueLength = queueLength;
        this.listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.add(new WeakReference<>(listener));
        }
    }

    public void append(LogLevel logLevel, String strMessage) {
        synchronized (messages) {
            if (messages.size() >= queueLength) {
                messages.removeFirst();
            }
            messages.addLast(new LogEntry(logLevel, strMessage));
        }
        notifyListeners();
    }

    private void notifyListeners() {
        synchronized (listeners) {
            Iterator<WeakReference<LogChangeListener>> it = listeners.iterator();
            while (it.hasNext()) {
                LogChangeListener listener = it.next().get();
                if (listener == null) {
                    it.remove();
                } else {
                    listener.onLogChanged();
                }
            }
        }
    }

    public int size() {
        synchronized (messages) {
            return messages.size();
        }
    }

    public Iterable<LogEntry> all() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }
}