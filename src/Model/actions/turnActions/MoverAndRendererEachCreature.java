package Model.actions.turnActions;

import Controller.Simulation;
import Model.actions.Action;
import Model.map.Coordinates;
import Model.map.EntityMap;
import Model.pathfind.PathFinder;
import Model.service.CreatureMoveService;
import Model.entities.creatures.Creature;
import Model.util.MapUtils;

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


