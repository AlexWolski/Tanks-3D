package Tanks3D;

import Tanks3D.Object.Entity.Entity;

import java.util.ArrayList;

//A 'struct' to contain objects used in the game world
public class GameData {
    //The 'Level' object stores all of the data for the game world and draws it to the screen.
    protected Level gameLevel;
    //A list of entities in the game. The first index is always player 1 and the second index is always player 2.
    protected ArrayList<Entity> entityList;

    //The two objects that controls player input and displays their side of the screen.
    protected Player player1;
    protected Player player2;
    //The object that controls the minimap
    protected Minimap minimap;
}
