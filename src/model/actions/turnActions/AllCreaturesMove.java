package model.actions.turnActions;


import model.actions.Action;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.service.CreatureMoveService;
import model.entities.creatures.Creature;
import model.util.EntityMapUtils;
import resources.SimulationConfig;

import java.util.List;

public class AllCreaturesMove implements Action {

    @Override
    public void execute(EntityMap entityMap) {
        List<Creature> creatures = EntityMapUtils.getEntitiesBy(Creature.class,entityMap);

        for (Creature creature : creatures) {
            if (!creature.isAlive()) {
                continue;
            }
            List<Coordinates> path = SimulationConfig.PATH_FINDER.getPath(entityMap, creature.getCoordinates(), creature.getTarget());
            CreatureMoveService.execute(creature, path, entityMap);
        }
        EntityMapUtils.cleanMapFromDeadCreatures(entityMap);
    }


}
