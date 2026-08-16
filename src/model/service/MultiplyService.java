package model.service;

import model.entities.creatures.Creature;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.entitymap.Coordinates;
import model.entitymap.Directions;
import model.entitymap.EntityMap;
import model.util.CellUtils;
import model.util.CreatureUtils;

import java.util.List;

public final class MultiplyService {
    private MultiplyService() {
    }

    public static boolean isMultiplyPath(Creature creature, List<Coordinates> path, EntityMap entityMap) {
        if (CellUtils.isCellVoid(path.getLast(), entityMap)) {
            return false;
        }
        Coordinates targetCell = path.getLast();
        return CellUtils.isSameCreaturesOnCells(creature.getCoordinates(), targetCell, entityMap);
    }

    public static Coordinates multiplyMove(Creature creature, Coordinates nextCell, List<Coordinates> path, EntityMap entityMap) {
        Coordinates oldCell = creature.getCoordinates();

        if (CellUtils.isSameCreaturesOnCells(oldCell, nextCell, entityMap)) {
            Creature partner = (Creature) entityMap.get(path.getLast());

            if (!partner.isCanMultiply()) {
                return nextCell;
            }
            nextCell = oldCell;

            creature.setCanMultiply(false);
            partner.setCanMultiply(false);
            addToMapCreatureAfterMultiply(creature, partner, entityMap);
        }
        return nextCell;
    }

    private static void addToMapCreatureAfterMultiply(Creature firstCreature, Creature secondCreature, EntityMap entityMap) {
        List<Creature> parentCreatures = List.of(firstCreature, secondCreature);

        for (Creature parent : parentCreatures) {
            for (Coordinates dir : Directions.NEAR_DIRECTIONS) {
                Coordinates cellToAddCreature = new Coordinates(parent.getCoordinates().getX() + dir.getX(),
                        parent.getCoordinates().getY() + dir.getY());

                if (CellUtils.isCellVoid(cellToAddCreature, entityMap)) {
                    Creature child;
                    if (CreatureUtils.isHerbivore(parent)) {
                        child = new Herbivore(cellToAddCreature, Herbivore.MAX_HEALTH_POINTS, parent.getSpeed());
                    } else {
                        child = new Predator(cellToAddCreature, Predator.MAX_HEALTH_POINTS, Predator.MAX_SPEED);
                    }
                    entityMap.add(cellToAddCreature, child);
                    return;
                }
            }
        }
    }
}
