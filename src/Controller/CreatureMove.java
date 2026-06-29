package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.EntityMap;

import java.util.List;

public class CreatureMove{
    private CreatureMove(){
    }

    public static void execute(Creature creature, List<Coordinates> path, EntityMap map) {
        Coordinates nextCell;
      map.add(path.getFirst(),null);// animal has left cell, so now it null
        nextCell = isLastCell(path) ? path.get(1) : path.get(creature.getSpeed());
        creature.makeMove(nextCell);
        map.add(nextCell,creature);
        Simulation.setMap(map);
    }
    private static boolean isLastCell (List<Coordinates> path){
        if (path.size() >2) {
            return false;
        }
        return true;
    }
}
