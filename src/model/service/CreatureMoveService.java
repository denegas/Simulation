package model.service;

import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.entities.Entity;
import model.entities.creatures.Creature;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.util.CellUtils;
import model.util.CreatureUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public final class CreatureMoveService {
    private static EntityMap entityMap;
    private static Coordinates nextCell;

    private CreatureMoveService() {
    }

    public static void execute(Creature creature, List<Coordinates> path, EntityMap entityMap) {
        if (path.isEmpty()) {
            return;
        }

        CreatureMoveService.entityMap = entityMap;
        Coordinates oldCell = creature.getCoordinates();
        Coordinates targetCell = path.getLast();
        nextCell = getNextCell(creature, path);

        HungryService.apply(creature);

        if (CreatureUtils.isHerbivore(creature)) {
            herbivoreMove(creature);

        } else {
            predatorMove(creature, oldCell, targetCell);
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

    private static void herbivoreMove(Creature herbivore) {
        if (CellUtils.isCellGrass(nextCell, entityMap)) {
            restoreAfterEating(herbivore);

        } else {
            HungryService.addHungryTurn(herbivore);
        }
    }

    private static void restoreAfterEating(Creature creature) {
        creature.setTurnsWithoutFood(0);
        creature.restoreHealthPoints();

        if (CreatureUtils.isPredator(creature)) {
            creature.setSpeed(Predator.MAX_SPEED);
        }
    }

    private static void predatorMove(Creature predator, Coordinates oldCell, Coordinates targetCell) {
        if (canAttack(nextCell, targetCell)) {
            predatorAttack(predator, oldCell, targetCell);

        } else {
            HungryService.addHungryTurn(predator);
        }

    }

    private static boolean canAttack(Coordinates nextCell, Coordinates targetCell) {
        Optional<Entity> targetEntity = entityMap.get(targetCell);
        if (targetEntity.isEmpty()) {
            return false;
        }

        boolean herbivoreStillAtTarget = CreatureUtils.isHerbivore(targetEntity.get());

        return (herbivoreStillAtTarget && CellUtils.isNeighbours(nextCell, targetCell));
    }

    private static void predatorAttack(Creature predator, Coordinates oldCell, Coordinates targetCell) {
        Optional<Entity> attackedHerbivoreOptional = entityMap.get(targetCell);

        if (attackedHerbivoreOptional.isPresent()) {

            if (isSuccessfulPredatorAttack()) {
                Herbivore attackedHerbivore = (Herbivore) attackedHerbivoreOptional.get();
                predatorDamagesHerbivore(attackedHerbivore);

                if (isPredatorKilledHerbivore(attackedHerbivore)) {
                    restoreAfterEating(predator);

                    if (canMoveOnTarget(oldCell, targetCell)) {
                        nextCell = targetCell;
                    }
                    attackedHerbivore.kill();
                    entityMap.clearCell(targetCell);
                }

            } else { // if predator fails it's attack
                HungryService.addHungryTurn(predator);
            }
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
        return !herbivore.isAlive();
    }

    private static boolean canMoveOnTarget(Coordinates oldCell, Coordinates targetCell) {
        int dx = Math.abs(oldCell.x() - targetCell.x());
        int dy = Math.abs(oldCell.y() - targetCell.y());
        return dx + dy <= Predator.MAX_SPEED;
    }

    private static void finishMove(Creature creature, Coordinates oldCell, Coordinates nextCell) {
        entityMap.clearCell(oldCell);
        creature.makeMove(nextCell);
        entityMap.add(nextCell, creature);
    }
}