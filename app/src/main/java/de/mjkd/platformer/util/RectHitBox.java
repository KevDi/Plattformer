package de.mjkd.platformer.util;

/**
 * Created by reaste on 28.08.17.
 */

public class RectHitBox {

    public float top;
    public float bottom;
    public float left;
    public float right;
    public float height;

    public boolean intersects(RectHitBox rectHitBox) {
        boolean hit = false;

        if (this.right > rectHitBox.left && this.left < rectHitBox.right) {
            if (this.top < rectHitBox.bottom && this.bottom > rectHitBox.top) {
                hit = true;
            }
        }
        return hit;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
