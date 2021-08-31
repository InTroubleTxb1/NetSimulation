package me.txb1.render.utils;

import lombok.AllArgsConstructor;

/**
 * @author Txb1 at 30.08.2021
 * @project NetSimulation
 */

@AllArgsConstructor
public class Vector {
    public float x, y;

    @Override
    public String toString() {
        return "x = " + x + " | y = " + y;
    }

    public Vector clone() {
        return new Vector(this.x, this.y);
    }

    public Vector devide(Vector vector) {
        if (vector.x != 0)
            this.x /= vector.x;
        if (vector.y != 0)
            this.y /= vector.y;
        return this;
    }

    public Vector add(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vector subtract(Vector vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public Vector multiply(Vector vector) {
        this.x *= vector.x;
        this.y *= vector.y;
        return this;
    }

    public Vector normalize() {
        // sets length to 1
        //
        double length = Math.sqrt(x * x + y * y);

        if (length != 0.0) {
            float s = 1.0f / (float) length;
            x = x * s;
            y = y * s;
        }
        return this;
    }

    public float distance(Vector vector) {
        float v0 = vector.x - this.x;
        float v1 = vector.y - this.y;
        return (float) Math.sqrt(v0 * v0 + v1 * v1);
    }
}