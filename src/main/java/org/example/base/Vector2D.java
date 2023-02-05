package org.example.base;

import java.io.Serializable;

/**
 * @author Simon Jern
 * A class to store and use both x and y values at the same, for example when storing positions.
 * Includes methods to make different calculations with the values.
 */
public class Vector2D implements Serializable {
    private double x;
    private double y;

    //region Getters & Setters (click to view)

    public double getX() {
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public int intX() {
        return (int)x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public int intY() {
        return (int)y;
    }

    //endregion

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D vectorToAdd) {
        x += vectorToAdd.getX();
        y += vectorToAdd.getY();
    }

    public void subtract(Vector2D vectorToAdd) {
        x -= vectorToAdd.getX();
        y -= vectorToAdd.getY();
    }
}
