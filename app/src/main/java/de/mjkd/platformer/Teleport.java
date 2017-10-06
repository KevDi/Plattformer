package de.mjkd.platformer;

/**
 * Created by reaste on 06.10.17.
 */

public class Teleport extends GameObject {

    Location target;


    public Teleport(float worldStartX, float worldStartY, char type, Location target) {
        final float HEIGHT = 2;
        final float WIDTH = 2;
        setHeight(HEIGHT); // 2 metres tall
        setWidth(WIDTH); // 1 metre wide
        setType(type);
        setBitmapName("door");
        this.target = new Location(target.level,
                target.x, target.y);
        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }

    public Location getTarget() {
        return target;
    }

    @Override
    public void update(long fps, float gravitiy) {

    }
}
