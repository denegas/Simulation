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

public class AllCreaturesMove implements Action {

    @Override
    public void execute(EntityMap map) {
        List<Creature> creatures = MapUtils.getCreatures(map);
        Simulation.CONSOLE_RENDERER.render(map);

        for (Creature creature : creatures) {
            if (creature.isDead()) {
                continue;
            }
            List<Coordinates> path = PathFinder.getPath(map, creature.getCoordinates(), creature);
            CreatureMoveService.execute(creature, path, map);
        }
        MapUtils.cleanMapFromDeadCreatures(map);
    }


}
