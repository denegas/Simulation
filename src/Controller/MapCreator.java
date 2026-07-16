package Controller;

import Model.Coordinates;
import Model.EntityMap;

public class MapCreator implements Action {
    @Override
    public void execute(EntityMap entityMap) {

        fullMap(entityMap);
        Simulation.setMap(entityMap);
        Simulation.setMapSize(entityMap.getSize());

    }

    private static void fullMap(EntityMap entityMap) {
        int mapSize = entityMap.getSize();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                entityMap.add(new Coordinates(i, j));
            }
        }
    }
}


