package org.model;

import org.observer.ModelListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель робота.
 * Содержит логику движения и координаты.
 * Уведомляет слушателей об изменениях (Observable)
 */

public class RobotModel {
    //Позиция и направление робота
    private double x = 100;
    private double y = 100;
    private double direction = 100;

    // Целевая точка
    private double targetX = 150;
    private double targetY = 100;

    // Параметры движения
    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    // Слушатели изменений
    private final List<ModelListener> listeners = new ArrayList<>();

    /**
     * Добавляет слушателя изменений модели
     */
    public void addListener(ModelListener listener){
        if (listener != null && !listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    /**
     * Удаляет слушателя
     */
    public void removeListener(ModelListener listener){
        listeners.remove(listener);
    }

    /**
     * Уведомляет всех слушателей об изменении координат
     */
    private void notifyListeners(){
        for (ModelListener listener: listeners){
            listener.onCoordinatesChanged(x,y,direction);
        }
    }

    /**
     * Устанавливает целевую точку
     */
    public void setTarget(double x, double y){
        this.targetX = x;
        this.targetY = y;
    }

    /**
     * Обновляет модель движения робота
     * duration время в миллисекундах
     */
    public void updateModel(int duration){
        double distance = distanceTarget();

        // Если уже достигли цели (с допуском 0.5 пикселя)
        if (distance < 0.5){
            return;
        }

        double velocity = MAX_VELOCITY;
        double angleTarget = angleTarget();

        // Вычисляем разницу углов и нормализуем её в диапазон [-PI, PI]
        double angleDiff = angleTarget - direction;

        // Нормализуем разницу углов, чтобы выбрать кратчайший путь поворота
        while (angleDiff > Math.PI) angleDiff -= 2 * Math.PI;
        while (angleDiff < -Math.PI) angleDiff += 2 * Math.PI;

        double angularVelocity = 0;

        // Поворачиваем в нужную сторону (если разница больше допустимой)
        if (Math.abs(angleDiff) > 0.01) {
            angularVelocity = (angleDiff > 0) ? MAX_ANGULAR_VELOCITY : -MAX_ANGULAR_VELOCITY;
        }

        // Выполняем движение
        moveRobot(velocity, angularVelocity, duration);
        }

    /**
    * Вычисляет расстояние до цели
    */
    private double distanceTarget(){
        double diffX = x - targetX;
        double diffY = y - targetY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    /**
     * Вычисляет угол до цели
     */
    private double angleTarget() {
        double diffX = targetX - x;
        double diffY = targetY - y;
        double angle = Math.atan2(diffY, diffX);
        return normalizeAngle(angle);
    }

    /**
     * Нормализует угол в диапазон [0, 2*PI)
     */
    private double normalizeAngle(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    /**
     * Ограничивает значение в заданных пределах
     */
    private double applyLimits(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Перемещает робота (математика движения)
     */
    private void moveRobot(double velocity, double angularVelocity, double duration) {
        // Приводим время к секундам (duration в миллисекундах)
        double dt = duration / 1000.0;

        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

        double newX = x;
        double newY = y;
        double newDirection = direction;

        // Если робот поворачивается
        if (Math.abs(angularVelocity) > 0.0001) {
            newX = x + (velocity / angularVelocity) * (Math.sin(direction + angularVelocity * dt) - Math.sin(direction));
            newY = y - (velocity / angularVelocity) * (Math.cos(direction + angularVelocity * dt) - Math.cos(direction));
        } else {
            // Движение по прямой
            newX = x + velocity * dt * Math.cos(direction);
            newY = y + velocity * dt * Math.sin(direction);
        }

        newDirection = normalizeAngle(direction + angularVelocity * dt);

        // Обновляем состояние робота
        this.x = newX;
        this.y = newY;
        this.direction = newDirection;

        // Уведомляем слушателей об изменении
        notifyListeners();
    }

    // Геттеры
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }
}

