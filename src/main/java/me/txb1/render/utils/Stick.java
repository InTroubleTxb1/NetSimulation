package me.txb1.render.utils;

import eu.firedata.system.controller.Controller;
import me.txb1.render.Renderer;

import java.awt.*;

/**
 * @author Txb1 at 30.08.2021
 * @project NetSimulation
 */

public class Stick {
    private static final Renderer renderer = Controller.getContext().getComponent(Renderer.class);

    public Point pointA, pointB;
    public float length;

    public Stick(Point pointA, Point pointB, float length) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.length = length;
    }

    public Stick(Point pointA, Point pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.length = pointA.position.distance(pointB.position);
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.drawLine((int) pointA.position.x, (int) pointA.position.y, (int) pointB.position.x, (int) pointB.position.y);
    }
}