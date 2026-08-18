package model.actions.turnActions;

import controller.Simulation;
import model.actions.Action;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.pathfind.PathFinder;
import model.service.CreatureMoveService;
import model.entities.creatures.Creature;
import model.util.EntityMapUtils;

import java.util.List;

public class MoverAndRendererEachCreature implements Action {

    @Override
    public void execute(EntityMap entityMap) {
        List<Creature> creatures = EntityMapUtils.getEntitiesBy(Creature.class, entityMap);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }

            Simulation.CONSOLE_RENDERER.render(entityMap);
            List<Coordinates> path = PathFinder.getPath(entityMap, creature.getCoordinates(), creature);
            CreatureMoveService.execute(creature, path, entityMap);

            Simulation.sleep(Simulation.TICK_SLEEP_MC);
        }
        EntityMapUtils.cleanMapFromDeadCreatures(entityMap);

    }

}


