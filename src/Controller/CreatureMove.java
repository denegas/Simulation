package Controller;

import Model.*;
import Model.services.HungryService;
import Model.utils.CellUtils;
import Model.utils.CreatureUtils;

import java.util.List;
import java.util.Random;

public final class CreatureMove {
    private static EntityMap map;
    private static Coordinates nextCell;

    private CreatureMove() {
    }

    public static void execute(Creature creature, List<Coordinates> path, EntityMap map) {
        if (path.isEmpty()) {
            return;
        }

        CreatureMove.map = map;
        Coordinates oldCell = creature.getCoordinates();
        Coordinates targetCell = path.getLast();
        nextCell = getNextCell(creature, path);

        HungryService.apply(creature);

        if (isMultiplyPath(creature, path)) {
            multiplyMove(creature, oldCell, path);
        } else {

            if (CreatureUtils.isHerbivore(creature)) {
                herbivoreMove(creature);

            } else {
                predatorMove(creature, oldCell, targetCell);
            }
        }

        finishMove(creature, oldCell, nextCell);
    }

    private static Coordinates getNextCell(Creature creature, List<Coordinates> path) {
        Coordinates nextCell;

        if (CreatureUtils.isHerbivore(creature)) {
            int step = Math.min(creature.getSpeed(), path.size() - creature.getSpeed());
            nextCell = path.get(step);

        } else {
            int step = Math.min(creature.getSpeed(), path.size() - Predator.MAX_SPEED);
            step = Math.max(step, 0);
            nextCell = path.get(step);
        }

        return nextCell;
    }

    private static boolean isMultiplyPath(Creature creature, List<Coordinates> path) {
        if (CellUtils.isCellVoid(path.getLast(), map)) {
            return false;
        }
        return creature.getType().equals(map.get(path.getLast()).getType());
    }

    private static void multiplyMove(Creature creature, Coordinates oldCell, List<Coordinates> path) {
        if (CellUtils.isSameCreaturesOnCells(oldCell, nextCell, map)) {
            Creature partner = (Creature) map.get(path.getLast());
            if (!partner.isCanMultiply()) {
                return;
            }
            nextCell = oldCell;

            creature.setCanMultiply(false);
            partner.setCanMultiply(false);
            addToMapCreatureAfterMultiply(creature, partner);
        }
    }

    private static void addToMapCreatureAfterMultiply(Creature firstCreature, Creature secondCreature) {
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

    private static void herbivoreMove(Creature herbivore) {
        if (isGrass(nextCell)) {
            restoreAfterEating(herbivore);

        } else {
            addHungryTurn(herbivore);
        }
    }

    private static boolean isGrass(Coordinates nextCell) {
        Entity entity = map.get(nextCell);
        if (entity == null) {
            return false;
        }
        return entity.getType().equals(EntityType.GRASS);
    }

    private static void restoreAfterEating(Creature creature) {
        creature.setTurnsWithoutFood(0);
        creature.restoreHealthPoints();

        if (CreatureUtils.isPredator(creature)) {
            creature.setSpeed(Predator.MAX_SPEED);
        }
    }

    private static void addHungryTurn(Creature creature) {
        creature.setTurnsWithoutFood(creature.getTurnsWithoutFood() + 1);
    }

    private static void predatorMove(Creature predator, Coordinates oldCell, Coordinates targetCell) {
        if (canAttack(nextCell, targetCell)) {
            predatorAttack(predator, oldCell, targetCell);

        } else {
            addHungryTurn(predator);
        }

    }

    private static boolean canAttack(Coordinates nextCell, Coordinates targetCell) {
        Entity targetEntity = map.get(targetCell);
        boolean herbivoreStillAtTarget = (targetEntity != null) && CreatureUtils.isHerbivore(targetEntity);

        return (herbivoreStillAtTarget && CellUtils.isNeighbours(nextCell, targetCell));
    }

    private static void predatorAttack(Creature predator, Coordinates oldCell, Coordinates targetCell) {
        Herbivore attackedHerbivore = (Herbivore) map.get(targetCell);
        if (isSuccessfulPredatorAttack()) {

            predatorDamagesHerbivore(attackedHerbivore);
            if (isPredatorKilledHerbivore(attackedHerbivore)) {
                restoreAfterEating(predator);

                if (canMoveOnTarget(oldCell, targetCell)) {
                    nextCell = targetCell;
                }
                attackedHerbivore.kill();
                map.clearCell(targetCell);
            }

        } else { // if predator fails it's attack
            addHungryTurn(predator);
        }
    }

    private static boolean isSuccessfulPredatorAttack() {
        Random random = new Random();
        double chanceToFailAttack = random.nextDouble();
        return chanceToFailAttack < Predator.ATTACK_CHANCE;
    }

    private static void predatorDamagesHerbivore(Herbivore herbivore) {
        herbivore.setHealthPoints(herbivore.getHealthPoints() - Predator.ATTACK_POWER);
        if (herbivore.getHealthPoints() < 1) {
            herbivore.kill();
        }
    }

    private static boolean isPredatorKilledHerbivore(Herbivore herbivore) {
        return herbivore.isDead();
    }

    private static boolean canMoveOnTarget(Coordinates oldCell, Coordinates targetCell) {
        int dx = Math.abs(oldCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(oldCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy <= 2;
    }

    private static void finishMove(Creature creature, Coordinates oldCell, Coordinates nextCell) {
        map.clearCell(oldCell);
        creature.makeMove(nextCell);
        map.add(nextCell, creature);
        Simulation.setMap(map);
    }
}