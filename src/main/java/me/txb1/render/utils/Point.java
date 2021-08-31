package me.txb1.render.utils;

import lombok.AllArgsConstructor;

import java.awt.*;

/**
 * @author Txb1 at 30.08.2021
 * @project NetSimulation
 */

@AllArgsConstructor
public class Point {
    public Vector position, prevPosition;
    public boolean locked;

    public static Point create(int x, int y, boolean locked) {
        return new Point(new Vector(x, y), new Vector(x, y), locked);
    }

    public void draw(Graphics2D g2d, int i) {
        g2d.drawRect((int) this.position.x - i, (int) this.position.y - i, i * 2, i * 2);
    }
}