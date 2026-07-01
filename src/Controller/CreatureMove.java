package Controller;

import Model.*;

import java.util.List;

public class CreatureMove {
    private CreatureMove() {
    }

    public static void execute(Creature creature, List<Coordinates> path, EntityMap map) {
        if (path.isEmpty()) return;
        Coordinates nextCell;

        if (isRandomMove(path)) {
            map.add(creature.getCoordinates(),null);// animal has left cell, so now it's cell null
            nextCell = path.getFirst();
        } else {
            map.add(path.getFirst(), null);// animal has left cell, so now it's cell null
            nextCell = isLastCell(path) ? path.get(1) : path.get(creature.getSpeed());
        }
        creature.makeMove(nextCell);
        if (map.getMap().get(nextCell) != null) {
            if (map.getMap().get(nextCell).getType().equals(EntityType.HERBIVORE)) {
                ((Herbivore) map.getMap().get(nextCell)).kill();
            }
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
}
