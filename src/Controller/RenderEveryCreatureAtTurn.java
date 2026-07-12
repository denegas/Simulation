package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.EntityMap;
import Model.PathFinder;
import View.Renderer;

import java.util.List;

public class RenderEveryCreatureAtTurn implements Action {
    //add hungry mechanic
    @Override
    public void execute(EntityMap map) {
        List<Creature> creatures = getCreatures(map);

        for (Creature creature: creatures){
            if(!creature.isAlive()){continue;}

            Renderer.render(map);
            List<Coordinates> path = PathFinder.getPath(map,creature.getCoordinates(),creature);
            CreatureMove.execute(creature, path, map);

            Simulation.sleep(500);
        }
        cleanMapFromDeadCreatures(map);


    }
    private static List<Creature> getCreatures(EntityMap map){
        return map.getCreatures().values().stream().toList();
    }
    private static void cleanMapFromDeadCreatures(EntityMap map){
        for(Creature creature: getCreatures(map)){
            if (!creature.isAlive()){
                map.add(creature.getCoordinates(),null);
            }
        }
    }
    }


