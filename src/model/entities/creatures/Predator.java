package model.entities.creatures;

import model.entities.Entity;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.util.CellUtils;
import model.util.CreatureUtils;
import resources.SimulationConfig;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Predator extends Creature {

    public static final Class<? extends Entity> TARGET = Herbivore.class;


    public Predator(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, healthPoints, speed);
    }

    private void restoreHealthPoints() {
        setHealthPoints(SimulationConfig.PREDATOR_MAX_HEALTH_POINTS);
    }

    @Override
    public Class<? extends Entity> getTarget() {
        return TARGET;
    }

    @Override
    public void makeMove(EntityMap entityMap) {
        List<Coordinates> path = SimulationConfig.PATH_FINDER.getPath(entityMap, coordinates, TARGET);
        if (path.isEmpty()) {
            hungryService.addHungryTurn(this);
            return;
        }
        Coordinates targetCell = path.getLast();
        Coordinates nextCell = getNextCell(path);

        hungryService.apply(this);

        if (canAttack(nextCell, targetCell, entityMap)) {
            nextCell = attack(targetCell, entityMap, nextCell);
        } else {
            hungryService.addHungryTurn(this);
        }

        entityMap.clearCell(coordinates);
        setCoordinates(nextCell);
        entityMap.add(nextCell, this);
    }

    private Coordinates getNextCell(List<Coordinates> path) {
        int step = Math.min(speed, path.size() - SimulationConfig.PREDATOR_MAX_SPEED);
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
                speed = SimulationConfig.PREDATOR_MAX_SPEED;
                turnsWithoutFood = 0;

                if (canMoveOnTarget(coordinates, targetCell)) {
                    nextCell = targetCell;
                }
                attackedHerbivore.kill();
                entityMap.clearCell(targetCell);
            }
        } else { // if predator fails attack
            hungryService.addHungryTurn(this);
        }
        return nextCell;
    }

    private boolean isSuccessfulAttack() {
        Random random = new Random();
        double chanceToFailAttack = random.nextDouble();
        return chanceToFailAttack < SimulationConfig.PREDATOR_ATTACK_CHANCE;
    }

    private void damageHerbivore(Herbivore herbivore) {
        herbivore.setHealthPoints(herbivore.getHealthPoints() - SimulationConfig.PREDATOR_ATTACK_POWER);
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
        return dx + dy <= SimulationConfig.PREDATOR_MAX_SPEED;
    }
}
