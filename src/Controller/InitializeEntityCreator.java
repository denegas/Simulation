package Controller;

import Model.*;
import java.util.*;

public final class InitializeEntityCreator extends EntityCreator implements Action {

    public InitializeEntityCreator(){
        super();
    }

    @Override
    public void execute(EntityMap map) {
        initEntityChances();
        for (var cell : map.getMap().keySet()) { // get all cells with coordinates from map
            double randomChance = random.nextDouble();
            var entry = entityChances.ceilingEntry(randomChance);
            if (entry != null) {
                EntityType type = entry.getValue();
                map.add(cell, getEntityFromType(type, cell));
            }
        }
        if (hasNoEntity(EntityType.HERBIVORE,map)) {
            addOneEntity(EntityType.HERBIVORE,map);
        }
        if (hasNoEntity(EntityType.PREDATOR,map)) {
            addOneEntity(EntityType.PREDATOR,map);
        }
        Simulation.setMap(map);
    }

    private boolean hasNoEntity(EntityType entityType, EntityMap map) {
        for (Entity entity : map.getNotNullEntities()) {
            if (entity.getType() == entityType) return false;
        }
        return true;
    }
    // add 1 entity to a random void cell in map
    private void addOneEntity(EntityType entityType, EntityMap map) {

        List<Coordinates> voidCells = map.getVoidCells();

        Coordinates randomCoordinates = voidCells.get(random.nextInt(voidCells.size()));

        map.add(randomCoordinates,getEntityFromType(entityType,randomCoordinates));

    }




}
