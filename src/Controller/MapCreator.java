package Controller;

import Model.Coordinates;
import Model.EntityMap;

public class MapCreator implements Action {
    @Override
    public void execute(EntityMap entityMap) {

        fillMap(entityMap);
        Simulation.setMap(entityMap);

    }

    private static void fillMap(EntityMap entityMap) {
        int mapSize = entityMap.size();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                entityMap.add(new Coordinates(i, j));
            }
        }
    }
}


