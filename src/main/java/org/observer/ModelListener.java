package org.observer;

/**
 * Интерфейс для слушателей изменений модели робота
 */
public interface ModelListener {
    /**
     * Вызывается при изменении координат или направления робота
     * @param x координата X
     * @param y координата Y
     * @param direction направление в радианах
     */
    void onCoordinatesChanged(double x, double y, double direction);
}