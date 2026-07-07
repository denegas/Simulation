package Controller;

import Model.Coordinates;
import Model.EntityMap;

public class MapCreator implements Action{
    @Override
    public void execute(EntityMap entityMap){
        int mapSize = entityMap.getSIZE();
        EntityMap map = new EntityMap(mapSize);
        for (int i = 0; i < mapSize; i++){
            for (int j = 0; j < mapSize;j++){
                map.add(new Coordinates(i,j));
            }
        }
        Simulation.setMap(map);
        Simulation.setMapSize(map.getSIZE());
        }
    }

