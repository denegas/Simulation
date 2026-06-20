package Controller;

import Model.Coordinates;
import Model.Entity;
import Model.EntityMap;

import java.util.Map;

public class MapCreator implements Action{
    @Override
    public void execute(){
        int mapSize = Simulation.getMapSize();
        for (int i =0;i<mapSize;i++){
            for (int j =0; j<mapSize;j++){
                EntityMap.add(new Coordinates(i,j));
            }
        }
        Simulation.setMap(EntityMap.getMap());
        }
    }

