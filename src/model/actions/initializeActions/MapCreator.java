package model.actions.initializeActions;

import controller.Simulation;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.actions.Action;

public class MapCreator implements Action {
    @Override
    public void execute(EntityMap entityMap) {

        fillMap(entityMap);
        Simulation.setEntityMap(entityMap);

    }

    private static void fillMap(EntityMap entityMap) {
        int mapWidth = entityMap.getWidth();
        int mapHeight = entityMap.getHeight();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                entityMap.add(new Coordinates(i, j));
            }
        }
    }
}


