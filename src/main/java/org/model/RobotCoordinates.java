package org.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель для хранения координат робота
 * Реализует паттерн Observer для уведомления слушателей об изменениях
 */

public class RobotCoordinates {
    private double x;
    private double y;
    private double direction;
    private final List<CoordinatesListener> listeners = new ArrayList<>();

    public RobotCoordinates() {
        this.x = 100;
        this.y = 0;
        this.direction = 0;
    }

    public void updateCoordinates(double x, double y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        notifyListeners();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public void addListener(CoordinatesListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CoordinatesListener listener) {
        listeners.remove(listener);

    }

    private void notifyListeners() {
        for (CoordinatesListener listener : listeners) {
            listener.onCoordinatesChanged(x, y, direction);
        }
    }

    /**
    * Интерфейс для слушателей изменений координат
    */
    public interface CoordinatesListener{
        void onCoordinatesChanged(double x, double y, double direction);
    }
}