package Controller;

import Model.*;

import java.util.List;
import java.util.Random;

public class CreatureMove {
    private CreatureMove() {
    }

    public static void execute(Creature creature, List<Coordinates> path, EntityMap map) {
        if (path.isEmpty()) return;

        Coordinates nextCell;

        if(creature.getTurnsWithoutFood() > creature.getMAX_TURNS_WITHOUT_FOOD()){
            hungerEffect(creature);
        }
        if (isRandomMove(path)) {
            map.add(creature.getCoordinates(),null);// animal has left cell, so now it's cell null
            nextCell = path.getFirst();
        } else {
            map.add(path.getFirst(), null);// animal has left cell, so now it's cell null
            nextCell = isLastCell(path) ? path.get(1) : path.get(creature.getSpeed());
        }


        if (isNotVoidCell(nextCell,map)) {

            if (isHerbivoreTryToEat(nextCell,map)) {
                recoverCreature(creature);

            }
            else if (isPredatorTryToEat(nextCell,map)) {

                if( isSuccessfulPredatorAttack() ) {
                    recoverCreature(creature);
                    ((Herbivore) map.getMap().get(nextCell)).kill();

                } else {//FAIL ATTACK

                    nextCell = path.getFirst();
                    creature.setTurnsWithoutFood(
                            creature.getTurnsWithoutFood() + 1);
                }
            }

        } else {
            creature.setTurnsWithoutFood(
                    creature.getTurnsWithoutFood() + 1);
        }
        creature.makeMove(nextCell);
        map.add(nextCell, creature);
        Simulation.setMap(map);
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
    private static boolean isNotVoidCell(Coordinates nextCell, EntityMap map){
        return map.getMap().get(nextCell) != null;
    }
    private static void hungerEffect(Creature creature){
        if (creature.getType().equals(EntityType.PREDATOR)){
            hungerLowsPredatorSpeed(creature);
        }
        hungerLowsCreatureHP(creature);
    }
    private static void hungerLowsPredatorSpeed(Creature predator){
        predator.setSpeed(1);
    }
    private static void hungerLowsCreatureHP(Creature creature){
        if(creature.getHealthPoints() < 1){
            creature.kill();
            return;
        }
        creature.setHealthPoints(creature.getHealthPoints()-1);
    }
    private static boolean isPredatorTryToEat(Coordinates nextCell, EntityMap map){
        return map.getMap().get(nextCell).getType().equals(EntityType.HERBIVORE);
    }
    private static boolean isHerbivoreTryToEat(Coordinates nextCell, EntityMap map){
        return map.getMap().get(nextCell).getType().equals(EntityType.GRASS);
    }
    private static boolean isSuccessfulPredatorAttack(){
        Random random = new Random();
        double chanceToFailAttack = random.nextDouble();
        return chanceToFailAttack < Predator.getATTACK_CHANCE();
    }
    private static void recoverCreature(Creature creature){
        creature.setTurnsWithoutFood(0);
        creature.restoreHealthPoints();
        if(creature.getType().equals(EntityType.PREDATOR)){
            creature.setSpeed(2);// predator returns to it normal speed
        }
    }

}
