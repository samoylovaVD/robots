package org.controller;

import org.model.RobotModel;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

public class RobotController {
    private static final int MODEL_UPDATE_INTERVAL_MS = 10;   // убрали magic number
    private static final int VIEW_REPAINT_INTERVAL_MS = 50;

    private final RobotModel model;
    private final Timer timer;
    private Runnable repaintCallback;

    public RobotController(RobotModel model) {
        this.model = model;
        this.timer = new Timer("RobotTimer", true);
    }

    public void setRepaintCallback(Runnable callback) {
        this.repaintCallback = callback;
    }

    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                model.update(MODEL_UPDATE_INTERVAL_MS);
            }
        }, 0, MODEL_UPDATE_INTERVAL_MS);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (repaintCallback != null) {
                    java.awt.EventQueue.invokeLater(repaintCallback);
                }
            }
        }, 0, VIEW_REPAINT_INTERVAL_MS);
    }

    public void setTarget(Point p) {
        model.setTarget(p);
    }

    public void shutdown() {
        timer.cancel();
        timer.purge();
    }
    // Геттеры для View – контроллер не раскрывает модель целиком
    public double getRobotX() { return model.getRobotX(); }
    public double getRobotY() { return model.getRobotY(); }
    public double getRobotDirection() { return model.getRobotDirection(); }
    public int getTargetX() { return model.getTargetX(); }
    public int getTargetY() { return model.getTargetY(); }
}