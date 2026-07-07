package Controller;

import Model.Coordinates;
import Model.Creature;
import Model.EntityMap;
import Model.PathFinder;
import View.Renderer;

import java.util.List;

public class AllCreaturesMove implements Action {

    @Override
    public void execute(EntityMap map) {
        List<Creature> creatures = getCreatures();
        Renderer.render(map.getMap());

        for (Creature creature : creatures) {
            if (!creature.isAlive()) {
                continue;
            }
            List<Coordinates> path = PathFinder.getPath(Simulation.getEntityMap(), creature.getCoordinates(), creature);
            CreatureMove.execute(creature, path, Simulation.getEntityMap());


        }

    }

    private static List<Creature> getCreatures() {
        return Simulation.getEntityMap().getCreatures().values().stream().toList();
    }

}
