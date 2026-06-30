package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.PathFinder;
import View.Renderer;

import java.util.List;
import java.util.Optional;

public class RenderEveryCreatureAtTurn implements Action{
    @Override
    public void execute() {
        List<Creature> creatures = getCreatures();
        for (Creature creature: creatures){
            Optional<List<Coordinates>> path = PathFinder.getPath(Simulation.getEntityMap(),creature.getCoordinates(),creature);
            if(path.isPresent()) {
                CreatureMove.execute(creature, path.get(), Simulation.getEntityMap());
            }
            Renderer.render(Simulation.getEntityMap().getMap());
        }

    }
    private static List<Creature> getCreatures(){
        return Simulation.getEntityMap().getCreatures().values().stream().toList();
    }
    }

