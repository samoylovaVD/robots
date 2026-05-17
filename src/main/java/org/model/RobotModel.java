package org.model;

import java.awt.Point;

/**
 * Модель робота.
 * Хранит состояние (координаты, направление, цель) и реализует физику движения.
 * Потокобезопасна за счёт synchronized в публичных методах.
 */

public class RobotModel { //константы
    private static final double MAX_VELOCITY = 100.0; // макс. линейная скорость (пикселей/сек)
    private static final double MAX_ANGULAR_VELOCITY = 2.0; // макс. угловая скорость (радиан/сек)
    private static final double DISTANCE_TOLERANCE = 5.0; // расстояние до цели, при котором робот останавливается
    private static final double ANGULAR_EPSILON = 0.001;  // допуск для сравнения углов (устраняет дрожание)

    private double robotX = 100;
    private double robotY = 100;
    private double robotDirection = 0;
    private int targetX = 100;
    private int targetY = 100;

    public synchronized double getRobotX() { return robotX; }
    public synchronized double getRobotY() { return robotY; }
    public synchronized double getRobotDirection() { return robotDirection; }
    public synchronized int getTargetX() { return targetX; }
    public synchronized int getTargetY() { return targetY; }

    /**
     * Устанавливает новую цель.
     * @param p точка клика мыши (координаты в системе координат игрового поля)
     */
    public synchronized void setTarget(Point p) {
        targetX = p.x;
        targetY = p.y;
    }

    public synchronized void update(double deltaTimeMs) {
        double distance = distance(targetX, targetY, robotX, robotY); // вычисляем расстояние до цели
// если робот достаточно близко к цели – останавливаемся и корректируем позицию
        if (distance < DISTANCE_TOLERANCE) {
            if (distance > 0.5) {
                robotX = targetX;
                robotY = targetY;
            }
            return;
        }
        // угол от текущего положения робота к цели
        double angleToTarget = angleTo(robotX, robotY, targetX, targetY);
        // разница между направлением на цель и текущим направлением робота
        double diff = angleToTarget - robotDirection;
        // нормализуем разницу в диапазон
        if (diff > Math.PI) diff -= 2 * Math.PI;
        if (diff < -Math.PI) diff += 2 * Math.PI;

        double angularVelocity = computeAngularVelocity(diff);
        moveRobot(MAX_VELOCITY, angularVelocity, deltaTimeMs / 1000.0);
    }

    /**
     * Определяет угловую скорость в зависимости от отклонения от цели.
     * @param diff разница углов (нормализованная от -π до π)
     * @return угловая скорость (положительная = поворот против часовой стрелки)
     */

    private double computeAngularVelocity(double diff) {
        if (diff > ANGULAR_EPSILON) {
            return MAX_ANGULAR_VELOCITY;
        } else if (diff < -ANGULAR_EPSILON) {
            return -MAX_ANGULAR_VELOCITY;
        } else {
            return 0;
        }
    }

    /**
     * Перемещает робота
     * Формулы учитывают движ по дуге окружн при ненулевой угловой скорости
     * и прямолинейное движ при нулевой
     * @param velocity        линейная скорость
     * @param angularVelocity угловая скорость
     * @param durationSec     длительность шага в сек
     */
    private void moveRobot(double velocity, double angularVelocity, double durationSec) {
        // ограничиваем скорости допустимыми пределами
        velocity = Math.clamp(velocity, 0, MAX_VELOCITY);
        angularVelocity = Math.clamp(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

//        вычисл новые координаты
        double newX = robotX + (velocity / angularVelocity) *
                (Math.sin(robotDirection + angularVelocity * durationSec) - Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotX + velocity * durationSec * Math.cos(robotDirection);
        }
        double newY = robotY - (velocity / angularVelocity) *
                (Math.cos(robotDirection + angularVelocity * durationSec) - Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotY + velocity * durationSec * Math.sin(robotDirection);
        }
        robotX = newX;
        robotY = newY;
        // обновляем направление
        robotDirection = normalizeRadians(robotDirection + angularVelocity * durationSec);
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        return normalizeRadians(Math.atan2(toY - fromY, toX - fromX));
    }

    private static double normalizeRadians(double angle) {
        while (angle < 0) angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI) angle -= 2 * Math.PI;
        return angle;
    }
}