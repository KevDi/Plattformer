package de.mjkd.platformer;

import android.graphics.PointF;

/**
 * Created by reaste on 03.10.17.
 */

public class Drone extends GameObject {


    long lastWaypointSetTime;
    PointF currentWaypoint;

    final float MAX_X_VELOCITY = 3;
    final float MAX_Y_VELOCITY = 3;

    public Drone(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("drone");
        setMoves(true);
        setActive(true);
        setVisible(true);

        currentWaypoint = new PointF();

        setWorldLocation(worldStartX, worldStartY,0);
        setRectHitBox();
        setFacing(RIGHT);
    }

    @Override
    public void update(long fps, float gravitiy) {
        if (currentWaypoint.x > getWorldLocation().x) {
            setxVelocity(MAX_X_VELOCITY);
        } else if(currentWaypoint.x < getWorldLocation().x) {
            setxVelocity(-MAX_X_VELOCITY);
        } else {
            setxVelocity(0);
        }

        if (currentWaypoint.y >= getWorldLocation().y) {
            setyVelocity(MAX_Y_VELOCITY);
        } else if (currentWaypoint.y < getWorldLocation().y) {
            setyVelocity(-MAX_Y_VELOCITY);
        } else {
            setyVelocity(0);
        }

        move(fps);

        setRectHitBox();
    }

    public void setWaypoint(Vector2Point5D playerLocation) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 2000) {
            lastWaypointSetTime = System.currentTimeMillis();
            currentWaypoint.x = playerLocation.x;
            currentWaypoint.y = playerLocation.y;
        }
    }
}
