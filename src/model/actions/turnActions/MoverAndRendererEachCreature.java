package model.actions.turnActions;

import controller.Simulation;
import model.actions.Action;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.pathfind.PathFinder;
import model.service.CreatureMoveService;
import model.entities.creatures.Creature;
import model.util.MapUtils;

import java.util.List;

public class MoverAndRendererEachCreature implements Action {

    @Override
    public void execute(EntityMap map) {
        List<Creature> creatures = MapUtils.getCreatures(map);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }

            Simulation.CONSOLE_RENDERER.render(map);
            List<Coordinates> path = PathFinder.getPath(map, creature.getCoordinates(), creature);
            CreatureMoveService.execute(creature, path, map);

            Simulation.sleep(Simulation.TICK_SLEEP_MC);
        }
        MapUtils.cleanMapFromDeadCreatures(map);

    }

}


