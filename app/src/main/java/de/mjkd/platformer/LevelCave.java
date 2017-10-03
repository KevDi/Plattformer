package de.mjkd.platformer;

import java.util.ArrayList;

/**
 * Created by reaste on 26.08.17.
 */

public class LevelCave extends LevelData {

    LevelCave() {
        tiles = new ArrayList<String>();
        this.tiles.add("p.............................................");
        this.tiles.add("..............................................");
        this.tiles.add("..............................................");
        this.tiles.add("..............................................");
        this.tiles.add("....................c.........................");
        this.tiles.add("....................1........u.......d........");
        this.tiles.add(".................c..........u1.......d........");
        this.tiles.add(".................1.........u1.........d.......");
        this.tiles.add("..............c...........u1.........d........");
        this.tiles.add("..............1..........u1..........d........");
        this.tiles.add("......................e..1....e.....e.........");
        this.tiles.add("....11111111111111111111111111111111111111....");
    }
}
