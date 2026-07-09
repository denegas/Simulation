package Controller;

import Model.*;

import java.util.List;

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
        creature.makeMove(nextCell);
        if (isNotVoidCell(nextCell,map)) {

            creature.setTurnsWithoutFood(0);
            creature.restoreHealthPoints();

            if (isHerbivoreOnCell(nextCell,map)) {
                ((Herbivore) map.getMap().get(nextCell)).kill();
                creature.setSpeed(2); // predator returns it normal speed

            }

        } else {
            creature.setTurnsWithoutFood(
                    creature.getTurnsWithoutFood() + 1);
        }

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
    private static boolean isHerbivoreOnCell(Coordinates nextCell, EntityMap map){
        return map.getMap().get(nextCell).getType().equals(EntityType.HERBIVORE);
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
        creature.setHealthPoints(creature.getHealthPoints()-1);
    }

}
