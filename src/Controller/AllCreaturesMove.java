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
        List<Creature> creatures = getCreatures(map);
        Renderer.render(map);

        for (Creature creature : creatures) {
            if (!creature.isAlive()) {
                continue;
            }
            List<Coordinates> path = PathFinder.getPath(map, creature.getCoordinates(), creature);
            CreatureMove.execute(creature, path, map);


        }

    }

    private static List<Creature> getCreatures(EntityMap map) {
        return map.getCreatures().values().stream().toList();
    }

}
