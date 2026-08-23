package model.actions.initializeActions;


import model.actions.Action;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.spawning.EntityCreator;
import model.entities.Entity;
import model.spawning.EntitySpawnChance;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.util.EntityMapUtils;
import java.util.*;

public final class InitializeEntityCreator extends EntityCreator implements Action {

    @Override
    public void execute(EntityMap map) {
        initializeEntities(map);

        addMissingCreatures(map);

    }

    private void initializeEntities(EntityMap entityMap) {
        List<Coordinates> allCells = EntityMapUtils.getVoidCells(entityMap);
        for (var cell : allCells) {
            double randomChance = RANDOM.nextDouble();
            for (EntitySpawnChance spawnChance : spawnChances) {
                if (randomChance <= spawnChance.chance()) {
                    Class<? extends Entity> type = spawnChance.type();
                    entityMap.add(cell, getEntityFromClass(type, cell));
                    break;
                }
            }
        }
    }

    private void addMissingCreatures(EntityMap map) {
        if (hasNoEntity(Herbivore.class, map)) {
            addOneEntityToRandomVoidCell(Herbivore.class, map);
        }
        if (hasNoEntity(Predator.class, map)) {
            addOneEntityToRandomVoidCell(Predator.class, map);
        }
    }

    private boolean hasNoEntity(Class<? extends Entity> entityClass, EntityMap entityMap) {
        for (Entity entity : EntityMapUtils.getEntitiesBy(Entity.class, entityMap)) {

            if (entity.getClass() == entityClass) {
                return false;
            }
        }
        return true;
    }

    private void addOneEntityToRandomVoidCell(Class<? extends Entity> entityClass, EntityMap map) {

        List<Coordinates> voidCells = EntityMapUtils.getVoidCells(map);
        Coordinates randomCoordinates = voidCells.get(RANDOM.nextInt(voidCells.size()));

        map.add(randomCoordinates, getEntityFromClass(entityClass, randomCoordinates));
    }

}
