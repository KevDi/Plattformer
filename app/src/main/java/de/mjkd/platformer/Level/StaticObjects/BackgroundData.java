package de.mjkd.platformer.Level.StaticObjects;

/**
 * Created by reaste on 06.10.17.
 */

public class BackgroundData {

    String bitmapName;
    boolean isParallax;
    //layer 0 is the map
    int layer;
    float startY;
    float endY;
    float speed;
    int height;
    int width;

    public BackgroundData(String bitmapName, boolean isParallax, int layer, float startY, float endY, float speed, int height) {
        this.bitmapName = bitmapName;
        this.isParallax = isParallax;
        this.layer = layer;
        this.startY = startY;
        this.endY = endY;
        this.speed = speed;
        this.height = height;
    }
}
