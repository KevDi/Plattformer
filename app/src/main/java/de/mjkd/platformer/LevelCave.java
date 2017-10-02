package de.mjkd.platformer;

import java.util.ArrayList;

/**
 * Created by reaste on 26.08.17.
 */

public class LevelCave extends LevelData {

    LevelCave() {
        tiles = new ArrayList<String>();
        this.tiles.add("p.............................................");
        this.tiles.add(".....................................11.......");
        this.tiles.add(".....11..............111111...................");
        this.tiles.add("..............................................");
        this.tiles.add("............111111............................");
        this.tiles.add("..............................11111...........");
        this.tiles.add(".........1111111..............................");
        this.tiles.add("..............................................");
        this.tiles.add("...........11111111...................11......");
        this.tiles.add("..............................................");
        this.tiles.add("..............................11111111........");
        this.tiles.add("..............................................");
    }
}
