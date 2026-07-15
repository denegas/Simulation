package Controller;

import Model.*;
import View.Renderer;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

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
        cleanMapFromDeadCreatures(map);
    }

    private static List<Creature> getCreatures(EntityMap map) {
        return map.getCreatures().values().stream().toList();
    }

    private static void cleanMapFromDeadCreatures(EntityMap map) {
        for (Creature creature : getCreatures(map)) {
            if (!creature.isAlive()) {
                map.removeEntity(creature.getCoordinates());
            }
        }
    }

}
