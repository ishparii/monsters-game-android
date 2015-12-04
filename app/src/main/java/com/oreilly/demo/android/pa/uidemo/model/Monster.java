package com.oreilly.demo.android.pa.uidemo.model;


import android.graphics.Canvas;

import java.util.Observable;

/** A monster: the coordinates and status */
public final class Monster extends Observable {
    private final int x, y;
    private boolean isVulnerable;

    /**
     * @param x horizontal coordinate at top left corner.
     * @param y vertical coordinate at top left corner.
     * @param isVulnerable status of monster.
     */
    public Monster(int x, int y, boolean isVulnerable) {
        this.x = x;
        this.y = y;
        this.isVulnerable = isVulnerable;
    }

    /** @return the horizontal coordinate. */
    public float getX() {
        return x;
    }

    /** @return the vertical coordinate. */
    public float getY() {
        return y;
    }

    /** @return the status. */
    public boolean getStatus() {
        return isVulnerable;
    }

    public void draw(Canvas canvas, int squareWidth ){

    }
}