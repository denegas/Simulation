package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.EntityMap;
import Model.PathFinder;
import View.Renderer;

import java.util.List;

public class RenderEveryCreatureAtTurn implements Action{
    @Override
    public void execute(EntityMap map) {
        List<Creature> creatures = getCreatures(map);
        for (Creature creature: creatures){
            if(!creature.isAlive()){continue;}
            Renderer.render(Simulation.getEntityMap().getMap());
            List<Coordinates> path = PathFinder.getPath(Simulation.getEntityMap(),creature.getCoordinates(),creature);
            CreatureMove.execute(creature, path, Simulation.getEntityMap());

            Simulation.pause(500);
        }

    }
    private static List<Creature> getCreatures(EntityMap map){
        return map.getCreatures().values().stream().toList();
    }
    }

