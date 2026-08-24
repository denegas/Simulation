package model.entities.creatures;

import model.entities.Entity;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.service.HungryService;
import model.util.CellUtils;
import model.util.CreatureUtils;
import resources.SimulationConfig;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Predator extends Creature {

    public static final Class<? extends Entity> TARGET = Herbivore.class;
    public static final int MAX_HEALTH_POINTS = 10;
    public static final int MAX_SPEED = 2;
    public static final int LOW_SPEED = 1;
    public static final double ATTACK_CHANCE = 0.9;
    public static final int ATTACK_POWER = 3;

    public Predator(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, healthPoints, speed);
    }

    private void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }

    @Override
    public Class<? extends Entity> getTarget() {
        return TARGET;
    }

    @Override
    public void makeMove(EntityMap entityMap) {
        List<Coordinates> path = SimulationConfig.PATH_FINDER.getPath(entityMap, coordinates, TARGET);
        if (path.isEmpty()) {
            HungryService.addHungryTurn(this);
            return;
        }
        Coordinates targetCell = path.getLast();
        Coordinates nextCell = getNextCell(path);

        HungryService.apply(this);

        if (canAttack(nextCell, targetCell, entityMap)) {
            nextCell = attack(targetCell, entityMap, nextCell);
        } else {
            HungryService.addHungryTurn(this);
        }

        entityMap.clearCell(coordinates);
        setCoordinates(nextCell);
        entityMap.add(nextCell, this);
    }

    private Coordinates getNextCell(List<Coordinates> path) {
        int step = Math.min(speed, path.size() - MAX_SPEED);
        step = Math.max(step, 0);

        return path.get(step);
    }

    private boolean canAttack(Coordinates nextCell, Coordinates targetCell, EntityMap entityMap) {
        Optional<Entity> targetEntity = entityMap.get(targetCell);
        if (targetEntity.isEmpty()) {

            return false;
        }
        boolean herbivoreStillAtTarget = CreatureUtils.isHerbivore(targetEntity.orElseThrow());

        return (herbivoreStillAtTarget && CellUtils.isNeighbours(nextCell, targetCell));
    }

    private Coordinates attack(Coordinates targetCell, EntityMap entityMap, Coordinates nextCell) {
        Herbivore attackedHerbivore = (Herbivore) entityMap.get(targetCell).orElseThrow();

        if (isSuccessfulAttack()) {
            damageHerbivore(attackedHerbivore);

            if (isHerbivoreDied(attackedHerbivore)) {
                restoreHealthPoints();
                speed = MAX_SPEED;
                turnsWithoutFood = 0;

                if (canMoveOnTarget(coordinates, targetCell)) {
                    nextCell = targetCell;
                }
                attackedHerbivore.kill();
                entityMap.clearCell(targetCell);
            }
        } else { // if predator fails attack
            HungryService.addHungryTurn(this);
        }
        return nextCell;
    }

    private boolean isSuccessfulAttack() {
        Random random = new Random();
        double chanceToFailAttack = random.nextDouble();
        return chanceToFailAttack < ATTACK_CHANCE;
    }

    private void damageHerbivore(Herbivore herbivore) {
        herbivore.setHealthPoints(herbivore.getHealthPoints() - ATTACK_POWER);
        if (herbivore.getHealthPoints() < 1) {
            herbivore.kill();
        }
    }

    private boolean isHerbivoreDied(Herbivore herbivore) {
        return !herbivore.isAlive();
    }

    private boolean canMoveOnTarget(Coordinates oldCell, Coordinates targetCell) {
        int dx = Math.abs(oldCell.x() - targetCell.x());
        int dy = Math.abs(oldCell.y() - targetCell.y());
        return dx + dy <= MAX_SPEED;
    }
}
