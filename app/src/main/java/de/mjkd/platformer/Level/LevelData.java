package de.mjkd.platformer.Level;

import java.util.ArrayList;

import de.mjkd.platformer.Level.StaticObjects.BackgroundData;
import de.mjkd.platformer.util.Location;

/**
 * Created by reaste on 26.08.17.
 */


//Tile types
//        . = no tile
//        1 = Grass
//        2 = Snow
//        3 = Brick
//        4 = Coal
//        5 = Concrete
//        6 = Scorched
//        7 = Stone
//Active objects
// g = guard
// d = drone
// t = teleport
// c = coin
// u = upgrade
// f = fire
// e = extra life
//Inactive objects
// w = tree
// x = tree2 (snowy)
// l = lampost
// r = stalactite
// s = stalacmite
// m = mine cart
// z = boulders


public class LevelData {
    public ArrayList<String> tiles;
    public ArrayList<BackgroundData> backgroundDataList;
    public ArrayList<Location> locations;
}
