package Controller;

import Model.*;

import java.util.*;

public final class InitializeEntityCreator extends EntityCreator implements Action {

    public InitializeEntityCreator() {
        super();
    }

    @Override
    public void execute(EntityMap map) {
        initializeEntities(map);

        addMissingCreatures(map);

        Simulation.setMap(map);
    }

    private void initializeEntities(EntityMap map) {
        Set<Coordinates> allCells = map.keySet();
        for (var cell : allCells) {
            double randomChance = random.nextDouble();
            var entry = entityChances.ceilingEntry(randomChance);

            if (isEntityExist(entry)) {
                EntityType type = entry.getValue();
                map.add(cell, getEntityFromType(type, cell));
            }
        }
    }
    private boolean isEntityExist(Map.Entry<Double,EntityType> entry){
        return entry != null;
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

    //
    private void addOneEntityToRandomVoidCell(EntityType entityType, EntityMap map) {

        List<Coordinates> voidCells = map.getVoidCells();

        Coordinates randomCoordinates = voidCells.get(random.nextInt(voidCells.size()));

        map.add(randomCoordinates, getEntityFromType(entityType, randomCoordinates));

    }


}
