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

    public static boolean isMultiplyPath(Creature creature, List<Coordinates> path, EntityMap map) {
        if (CellUtils.isCellVoid(path.getLast(), map)) {
            return false;
        }
        Coordinates targetCell = path.getLast();
        return CellUtils.isSameCreaturesOnCells(creature.getCoordinates(), targetCell, map);
    }

    public static Coordinates multiplyMove(Creature creature, Coordinates nextCell, List<Coordinates> path, EntityMap map) {
        Coordinates oldCell = creature.getCoordinates();

        if (CellUtils.isSameCreaturesOnCells(oldCell, nextCell, map)) {
            Creature partner = (Creature) map.get(path.getLast());

            if (!partner.isCanMultiply()) {
                return nextCell;
            }
            nextCell = oldCell;

            creature.setCanMultiply(false);
            partner.setCanMultiply(false);
            addToMapCreatureAfterMultiply(creature, partner, map);
        }
        return nextCell;
    }

    private static void addToMapCreatureAfterMultiply(Creature firstCreature, Creature secondCreature, EntityMap map) {
        List<Creature> parentCreatures = List.of(firstCreature, secondCreature);

        for (Creature parent : parentCreatures) {
            for (Coordinates dir : Directions.NEAR_DIRECTIONS) {
                Coordinates cellToAddCreature = new Coordinates(parent.getCoordinates().getX() + dir.getX(),
                        parent.getCoordinates().getY() + dir.getY());

                if (CellUtils.isCellVoid(cellToAddCreature, map)) {
                    Creature child;
                    if (CreatureUtils.isHerbivore(parent)) {
                        child = new Herbivore(cellToAddCreature, Herbivore.MAX_HEALTH_POINTS, parent.getSpeed());
                    } else {
                        child = new Predator(cellToAddCreature, Predator.MAX_HEALTH_POINTS, Predator.MAX_SPEED);
                    }
                    map.add(cellToAddCreature, child);
                    return;
                }
            }
        }
    }
}
