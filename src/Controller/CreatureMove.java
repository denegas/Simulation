package Controller;

import Model.*;

import java.util.List;
import java.util.Random;

public final class CreatureMove {
    private CreatureMove() {
    }

    private static EntityMap map;
    private static Coordinates nextCell;

    public static void execute(Creature creature, List<Coordinates> path, EntityMap map) {
        if (path.isEmpty()) return;

        CreatureMove.map = map;
        Coordinates oldCell = creature.getCoordinates();
        Coordinates targetCell = path.getLast();
        nextCell = getNextCell(creature, path);

        applyHungry(creature);

        if (isHerbivore(creature)) {
            herbivoreMove(creature);

        } else {
            predatorMove(creature, oldCell, targetCell);
        }

        finishMove(creature, oldCell, nextCell);
    }

    private static Coordinates getNextCell(Creature creature, List<Coordinates> path) {
        Coordinates nextCell;

        if (isHerbivore(creature)) {
            int step = Math.min(creature.getSpeed(), path.size() - 1);
            nextCell = path.get(step);

        } else {
            int step = Math.min(creature.getSpeed(), path.size() - 2);
            step = Math.max(step, 0);
            nextCell = path.get(step);
        }

        return nextCell;
    }

    private static void applyHungry(Creature creature) {
        if (creature.getTurnsWithoutFood() > creature.getMAX_TURNS_WITHOUT_FOOD()) {
            hungerEffect(creature);
        }
    }

    private static void hungerEffect(Creature creature) {
        if (isPredator(creature)) {
            hungerLowsPredatorSpeed(creature);
        }
        hungerLowsCreatureHP(creature);
    }

    private static boolean isPredator(Creature creature) {
        return creature.getType().equals(EntityType.PREDATOR);
    }

    private static void hungerLowsPredatorSpeed(Creature predator) {
        predator.setSpeed(1);
    }

    private static void hungerLowsCreatureHP(Creature creature) {
        if (creature.getHealthPoints() < 1) {
            creature.kill();
            return;
        }
        creature.setHealthPoints(creature.getHealthPoints() - 1);
    }

    private static boolean isHerbivore(Creature creature) {
        return creature.getType().equals(EntityType.HERBIVORE);
    }

    private static void herbivoreMove(Creature herbivore) {
        if (isGrass(nextCell)) {
            restoreAfterEating(herbivore);

        } else {
            addHungryTurn(herbivore);
        }
    }

    private static boolean isGrass(Coordinates nextCell) {
        Entity entity = map.getMap().get(nextCell);
        if (entity == null) {
            return false;
        }
        return entity.getType().equals(EntityType.GRASS);
    }

    private static void restoreAfterEating(Creature creature) {
        creature.setTurnsWithoutFood(0);
        creature.restoreHealthPoints();
        if (isPredator(creature)) {
            creature.setSpeed(2);// predator returns to it normal speed
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
        Entity targetEntity = map.getMap().get(targetCell);
        boolean herbivoreStillAtTarget = (targetEntity != null) && isHerbivore(targetEntity);

        return (herbivoreStillAtTarget && isNeighbours(nextCell, targetCell));
    }

    private static boolean isHerbivore(Entity creature) {
        return creature.getType().equals(EntityType.HERBIVORE);
    }

    private static boolean isNeighbours(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(nextCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy == 1;
    }

    private static void predatorAttack(Creature predator, Coordinates oldCell, Coordinates targetCell) {
        Herbivore attackedHerbivore = (Herbivore) map.getMap().get(targetCell);
        if (isSuccessfulPredatorAttack()) {

            predatorDamagesHerbivore(attackedHerbivore);

            if (isPredatorKilledHerbivore(attackedHerbivore)) {
                restoreAfterEating(predator);

                if (canMoveOnTarget(oldCell, targetCell)) {
                    nextCell = targetCell;
                }
                attackedHerbivore.kill();
                map.removeEntity(targetCell);
            }

        } else { // if predator fails it's attack
            addHungryTurn(predator);
        }

    }

    private static boolean isSuccessfulPredatorAttack() {
        Random random = new Random();
        double chanceToFailAttack = random.nextDouble();
        return chanceToFailAttack < Predator.getATTACK_CHANCE();
    }

    private static void predatorDamagesHerbivore(Herbivore herbivore) {
        herbivore.setHealthPoints(herbivore.getHealthPoints() - Predator.getATTACK_POWER());
        System.out.println("attack");
        if (herbivore.getHealthPoints() <1){
            herbivore.kill();
//            map.removeEntity(herbivore.getCoordinates());
        }
    }

    private static boolean isPredatorKilledHerbivore(Herbivore herbivore) {
        return !herbivore.isAlive();
    }

    private static boolean canMoveOnTarget(Coordinates oldCell, Coordinates targetCell) {
        int dx = Math.abs(oldCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(oldCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy <= 2;
    }

    private static void finishMove(Creature creature, Coordinates oldCell, Coordinates nextCell) {
        map.removeEntity(oldCell);
        creature.makeMove(nextCell);
        map.add(nextCell, creature);
        Simulation.setMap(map);
    }
//    private static boolean isRandomMove(List<Coordinates> path) {
//        return path.size() == 1;
//    }
}