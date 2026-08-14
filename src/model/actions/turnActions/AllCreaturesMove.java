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
    public void execute(EntityMap map) {
        List<Creature> creatures = EntityMapUtils.getCreatures(map);
        Simulation.CONSOLE_RENDERER.render(map);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }
            List<Coordinates> path = PathFinder.getPath(map, creature.getCoordinates(), creature);
            CreatureMoveService.execute(creature, path, map);
        }
        EntityMapUtils.cleanMapFromDeadCreatures(map);
    }


}
