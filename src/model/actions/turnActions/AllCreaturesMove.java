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

public class AllCreaturesMove implements Action {

    @Override
    public void execute(EntityMap entityMap) {
        List<Creature> creatures = EntityMapUtils.getEntitiesBy(Creature.class,entityMap);
        Simulation.CONSOLE_RENDERER.render(entityMap);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }
            List<Coordinates> path = PathFinder.getPath(entityMap, creature.getCoordinates(), creature.getTarget());
            CreatureMoveService.execute(creature, path, entityMap);
        }
        EntityMapUtils.cleanMapFromDeadCreatures(entityMap);
    }


}
