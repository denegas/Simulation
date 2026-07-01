package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.PathFinder;
import View.Renderer;

import java.util.List;

public class RenderEveryCreatureAtTurn implements Action{
    @Override
    public void execute() {
        List<Creature> creatures = getCreatures();
        for (Creature creature: creatures){
            if(!creature.isAlive()){continue;}
            List<Coordinates> path = PathFinder.getPath(Simulation.getEntityMap(),creature.getCoordinates(),creature);
            CreatureMove.execute(creature, path, Simulation.getEntityMap());

            Renderer.render(Simulation.getEntityMap().getMap());
            Simulation.pause(500);
        }

    }
    private static List<Creature> getCreatures(){
        return Simulation.getEntityMap().getCreatures().values().stream().toList();
    }
    }

