package Controller;

import Model.*;

import java.util.List;
import java.util.Random;

public final class CreatureMove {
    private CreatureMove() {
    }

    private static EntityMap map;

    public static void execute(Creature creature, List<Coordinates> path, EntityMap map) {
        if (path.isEmpty()) return;

        CreatureMove.map = map;
        Coordinates oldCell = creature.getCoordinates();
        Coordinates targetCell = path.getLast();
        Coordinates nextCell;

        if (isHerbivore(creature)) {
            int step = Math.min(creature.getSpeed(), path.size() - 1);
            nextCell = path.get(step);

        } else if (isRandomMove(path)) {
            boolean cellHasHerbivore = map.getMap().get(targetCell) != null
                    && map.getMap().get(targetCell).getType().equals(EntityType.HERBIVORE);
            nextCell = cellHasHerbivore ? oldCell : targetCell;

        } else {
            int step = Math.min(creature.getSpeed(), path.size() - 2);
            step = Math.max(step, 0);
            nextCell = path.get(step);
        }

        if (creature.getTurnsWithoutFood() > creature.getMAX_TURNS_WITHOUT_FOOD()) {
            hungerEffect(creature);
        }
//        if (isRandomMove(path)) {
//            map.add(creature.getCoordinates(), null);// animal has left cell, so now it's cell null
//            nextCell = path.getFirst();
//        } else {
//            map.add(path.getFirst(), null);// animal has left cell, so now it's cell null
//            nextCell = isLastCell(path) ? path.get(1) : path.get(creature.getSpeed());
//        }


        if (isHerbivore(creature)) {

            if (isNotVoidCell(nextCell)) {
                recoverCreature(creature);

            } else {
                creature.setTurnsWithoutFood(
                        creature.getTurnsWithoutFood() + 1);
            }

        } else { // PREDATOR

            boolean preyStillAtTarget = map.getMap().get(targetCell) != null
                    && isHerbivore(map.getMap().get(targetCell));

            if (preyStillAtTarget && isTwoCellsNextDoor(nextCell, targetCell)) {

                Herbivore attackedHerbivore = (Herbivore) map.getMap().get(targetCell);
                if (isSuccessfulPredatorAttack()) {

                    predatorDamagesHerbivore(attackedHerbivore);

                    if (isPredatorKilledHerbivore(attackedHerbivore)) {
                        recoverCreature(creature);

                        if(isPredatorCanMoveTo(oldCell,targetCell)){
                            nextCell = targetCell;
                        }
                        map.add(targetCell, null);
                    }

                } else {//FAIL ATTACK

                    creature.setTurnsWithoutFood(
                            creature.getTurnsWithoutFood() + 1);
                }
            } else {
                creature.setTurnsWithoutFood(
                        creature.getTurnsWithoutFood() + 1);
            }
        }

        map.add(oldCell, null);
        creature.makeMove(nextCell);
        map.add(nextCell, creature);
        Simulation.setMap(map);
    }
    private static boolean isPredatorCanMoveTo(Coordinates oldCell,Coordinates targetCell){
        int dx = Math.abs(oldCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(oldCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy <=2;
    }
    private static boolean isHerbivore(Creature creature) {
        return creature.getType().equals(EntityType.HERBIVORE);
    }

    private static boolean isHerbivore(Entity creature) {
        return creature.getType().equals(EntityType.HERBIVORE);
    }

    private static boolean isLastCell(List<Coordinates> path) {
        if (path.size() > 2) {
            return false;
        }
        return true;
    }

    private static boolean isRandomMove(List<Coordinates> path) {
        return path.size() == 1;
    }

    private static boolean isNotVoidCell(Coordinates nextCell) {
        return map.getMap().get(nextCell) != null;
    }

    private static void hungerEffect(Creature creature) {
        if (creature.getType().equals(EntityType.PREDATOR)) {
            hungerLowsPredatorSpeed(creature);
        }
        hungerLowsCreatureHP(creature);
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

    private static boolean isHerbivoreTryToEat(Coordinates nextCell) {
        return map.getMap().get(nextCell).getType().equals(EntityType.GRASS);
    }

    private static boolean isSuccessfulPredatorAttack() {
        Random random = new Random();
        double chanceToFailAttack = random.nextDouble();
        return chanceToFailAttack < Predator.getATTACK_CHANCE();
    }

    private static void recoverCreature(Creature creature) {
        creature.setTurnsWithoutFood(0);
        creature.restoreHealthPoints();
        if (creature.getType().equals(EntityType.PREDATOR)) {
            creature.setSpeed(2);// predator returns to it normal speed
        }
    }

    private static boolean isPredatorKilledHerbivore(Herbivore herbivore) {
        return !herbivore.isAlive();
    }

    private static void predatorDamagesHerbivore(Herbivore herbivore) {

        herbivore.setHealthPoints(herbivore.getHealthPoints() - Predator.getATTACK_POWER());
        if (herbivore.getHealthPoints() < 1) {
            herbivore.kill();
            map.add(herbivore.getCoordinates(), null);
            //System.out.println("herv killed");
        }
    }

    private static boolean isTwoCellsNextDoor(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(nextCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy == 1;
    }
}