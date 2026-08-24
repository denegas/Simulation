package model.actions.turnActions;


import model.actions.Action;
import model.entitymap.EntityMap;
import model.entities.creatures.Creature;
import model.util.EntityMapUtils;

import java.util.List;

public class AllCreaturesMove implements Action {

    @Override
    public void execute(EntityMap entityMap) {
        List<Creature> creatures = EntityMapUtils.getEntitiesBy(Creature.class,entityMap);

        for (Creature creature : creatures) {
            if (creature.isAlive()) {
                creature.makeMove(entityMap);
            }

        }
        EntityMapUtils.cleanMapFromDeadCreatures(entityMap);
    }


}
