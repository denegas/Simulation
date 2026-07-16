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
        List<Creature> creatures = MapUtils.getCreatures(map);
        Renderer.render(map);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }
            List<Coordinates> path = PathFinder.getPath(map, creature.getCoordinates(), creature);
            CreatureMove.execute(creature, path, map);
        }
        MapUtils.cleanMapFromDeadCreatures(map);
    }


}
