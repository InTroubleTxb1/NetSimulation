package me.txb1.render;

import eu.firedata.system.controller.annotations.method.Construct;
import eu.firedata.system.controller.annotations.type.Component;
import eu.firedata.system.controller.annotations.variable.Fill;
import lombok.Getter;
import me.txb1.event.EventHandler;
import me.txb1.event.EventManager;
import me.txb1.event.Listener;
import me.txb1.event.impl.mouse.EventMousePress;
import me.txb1.render.utils.Point;
import me.txb1.render.utils.Stick;
import me.txb1.render.utils.Vector;
import me.txb1.render.window.Window;
import me.txb1.utils.KeyBoardUtils;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @author Txb1 at 30.08.2021
 * @project NetSimulation
 */

@Getter
@Component
public class Renderer implements Listener {

    @Fill
    private Window window;

    @Fill
    private EventManager eventManager;

    private final List<Point> points = new ArrayList<>();
    private final List<Stick> sticks = new ArrayList<>();
    private final float gravity = 1f;

    private boolean running = false;

    //CHANGE BELOW : ORIGIN : 18, 17, 50, 50

    private final int xCount = 18 * 9;
    private final int yCount = 17 * 3;

    private final int xAdd = 50 / 9;
    private final int yAdd = 50 / 6;

    @Construct
    public void handleConstruct() {
        this.eventManager.registerListener(this);
        this.window.startRenderThread(1000 / 60);

        for (int i = 1; i <= this.xCount; i++) {
            this.points.add(Point.create(i * this.xAdd, this.yAdd, true));
        }

        for (int y = 2; y <= this.yCount; y++) {
            for (int i = 1; i <= this.xCount; i++) {
                this.points.add(Point.create(i * this.xAdd, y * this.yAdd, false));
            }
        }


        for (int i = 0; i < this.points.size(); i++) {
            Point p = this.points.get(i);
            if (i != 0) {
                Point left = this.points.get(i - 1);
                if (left.position.y == p.position.y)
                    this.sticks.add(new Stick(p, left));
            }

            if (i - this.xCount >= 0) {
                Point up = this.points.get(i - this.xCount);
                this.sticks.add(new Stick(p, up));
            }
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                running = true;
            }
        }, 2000);
    }

    public void handleRender(Graphics2D g2d) {
        try {
            if (this.points.isEmpty()) return;
            if (this.running) {

                if (KeyBoardUtils.isKeyDown(87))
                    this.points.stream().filter(point -> point.locked).forEach(point -> point.position.add(new Vector(0,-4)));

                if (KeyBoardUtils.isKeyDown(83))
                    this.points.stream().filter(point -> point.locked).forEach(point -> point.position.add(new Vector(0,4)));

                if (KeyBoardUtils.isKeyDown(68))
                    this.points.stream().filter(point -> point.locked).forEach(point -> point.position.add(new Vector(4,0)));

                if (KeyBoardUtils.isKeyDown(65))
                    this.points.stream().filter(point -> point.locked).forEach(point -> point.position.add(new Vector(-4,0)));

                this.points
                        .stream()
                        .filter(point -> !point.locked)
                        .forEach(point -> {
                            Vector positionBeforeUpdate = point.position.clone();

                            point.position.add(point.position.clone().subtract(point.prevPosition));
                            point.position.add(new Vector(0, 1).multiply(new Vector(1, gravity)));
                            point.prevPosition = positionBeforeUpdate;
                        });

                for (int i = 0; i < 10; i++)
                    this.sticks.iterator().forEachRemaining(stick -> {
                        Vector stickCenter = (stick.pointA.position.clone().add(stick.pointB.position)).divide(new Vector(2, 2));
                        Vector stickDir = (stick.pointA.position.clone().subtract(stick.pointB.position)).normalize();

                        float multiply = stick.length;

                        if (!stick.pointA.locked)
                            stick.pointA.position = stickCenter.clone().add(stickDir.clone().multiply(new Vector(multiply, multiply)).divide(new Vector(2, 2)));
                        if (!stick.pointB.locked)
                            stick.pointB.position = stickCenter.clone().subtract(stickDir.clone().multiply(new Vector(multiply, multiply)).divide(new Vector(2, 2)));
                    });
            }


            this.points.stream().filter(point -> point.locked).forEach(point -> {
                point.draw(g2d, 10);
            });
            this.sticks.stream().filter(stick -> stick.pointB.position.y < 1000 && stick.pointA.position.y < 1000).forEach(stick -> {
                stick.draw(g2d);
            });
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void handleMouseMove(EventMousePress event) {
        int x = event.getMouseEvent().getX();
        int y = event.getMouseEvent().getY();

        Vector vector = new Vector(x, y);
        this.sticks.removeIf(stick -> stick.pointA.position.distance(vector) < 20f || stick.pointB.position.distance(vector) < 20f);
    }
}