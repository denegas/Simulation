package Model.services;

import Model.*;
import Model.utils.CellUtils;
import Model.utils.CreatureUtils;

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
        int[][] directions = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0}
        };
        for (Creature parent : parentCreatures) {
            for (int[] dir : directions) {
                Coordinates cellToAddCreature = new Coordinates(parent.getCoordinates().getCoordinateX() + dir[0],
                        parent.getCoordinates().getCoordinateY() + dir[1]);
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
