package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.EntityMap;

import java.util.List;

public class CreatureMove implements Action{
    public CreatureMove(Creature creature, List<Coordinates> path, EntityMap map){
     this.creature = creature;
     this.map = map;
     this.path = path;
    }
    private final Creature creature;
    private EntityMap map;
    private final List<Coordinates> path;
    @Override
    public void execute() {
      map.add(path.getFirst(),null);// animal has left cell, so now it null
        Coordinates nextCell = path.get(1);
        creature.makeMove(nextCell);
        map.add(nextCell,creature);
        Simulation.setMap(map);
    }
}
