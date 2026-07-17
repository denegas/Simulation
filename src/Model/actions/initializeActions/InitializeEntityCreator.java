package Model.actions.initializeActions;

import Controller.Simulation;
import Model.actions.Action;
import Model.spawning.EntityCreator;
import Model.entities.Entity;
import Model.entities.EntityType;
import Model.spawning.EntitySpawnChance;
import Model.map.Coordinates;
import Model.map.EntityMap;

import java.util.*;

public final class InitializeEntityCreator extends EntityCreator implements Action {

    @Override
    public void execute(EntityMap map) {
        initializeEntities(map);

        addMissingCreatures(map);

        Simulation.setMap(map);
    }

    private void initializeEntities(EntityMap map) {
        Set<Coordinates> allCells = map.keySet();
        for (var cell : allCells) {
            double randomChance = RANDOM.nextDouble();
            for (EntitySpawnChance spawnChance : spawnChances) {
                if (randomChance <= spawnChance.chance()) {
                    EntityType type = spawnChance.type();
                    map.add(cell, getEntityFromType(type, cell));
                    break;
                }
            }
        }
    }

    private void addMissingCreatures(EntityMap map) {
        if (hasNoEntity(EntityType.HERBIVORE, map)) {
            addOneEntityToRandomVoidCell(EntityType.HERBIVORE, map);
        }
        if (hasNoEntity(EntityType.PREDATOR, map)) {
            addOneEntityToRandomVoidCell(EntityType.PREDATOR, map);
        }
    }

    private boolean hasNoEntity(EntityType entityType, EntityMap map) {
        for (Entity entity : map.getNotNullEntities()) {
            if (entity.getType() == entityType) return false;
        }
        return true;
    }

    private void addOneEntityToRandomVoidCell(EntityType entityType, EntityMap map) {

        List<Coordinates> voidCells = map.getVoidCells();
        Coordinates randomCoordinates = voidCells.get(RANDOM.nextInt(voidCells.size()));

        map.add(randomCoordinates, getEntityFromType(entityType, randomCoordinates));
    }

}
