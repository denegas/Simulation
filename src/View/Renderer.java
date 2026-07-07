package View;

import Controller.Simulation;
import Model.Coordinates;
import Model.Entity;
import Model.EntityMap;
import Model.EntityType;

import java.util.Map;
import java.util.TreeMap;

public final class Renderer {
    private Renderer() {
    }

    public static void render(EntityMap map) {
        Map<Coordinates, Entity> sortedMap = new TreeMap<>(map.getMap());
        int mapSize = Simulation.getMapSize();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (sortedMap.get(new Coordinates(i, j)) == null)
                    System.out.print(EntityType.VOID.getEntityView() + " ");
                else {
                    System.out.print(sortedMap.get(new Coordinates(i, j)).getType().getEntityView() + " ");
                }
            }
            System.out.println();//next row
        }
        System.out.println(); // space between two maps

    }
}
