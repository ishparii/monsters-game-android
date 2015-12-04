package com.oreilly.demo.android.pa.uidemo.model;


/** A monster: the coordinates and status */
public final class Monster {
    private final float x, y;
    private boolean isVulnerable;

    /**
     * @param x horizontal coordinate at top left corner.
     * @param y vertical coordinate at top left corner.
     * @param isVulnerable status of monster.
     */
    public Monster(float x, float y, boolean isVulnerable) {
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
}