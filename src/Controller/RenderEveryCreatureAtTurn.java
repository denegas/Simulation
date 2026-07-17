package Controller;

import Model.*;
import Model.utils.MapUtils;
import java.util.List;

public class RenderEveryCreatureAtTurn implements Action {

    @Override
    public void execute(EntityMap map) {
        List<Creature> creatures = MapUtils.getCreatures(map);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }

            Simulation.CONSOLE_RENDERER.render(map);
            List<Coordinates> path = PathFinder.getPath(map, creature.getCoordinates(), creature);
            CreatureMove.execute(creature, path, map);

            Simulation.sleep(Simulation.TICK_SLEEP_MC);
        }
        MapUtils.cleanMapFromDeadCreatures(map);

    }

}


