package Controller;

import Model.Coordinates;
import Model.EntityMap;

public class MapCreator implements Action{
    public MapCreator(int mapSize){
        this.mapSize = mapSize;
    }
    private final int mapSize;
    @Override
    public void execute(){
        EntityMap map = new EntityMap(mapSize);
        for (int i =0;i<mapSize;i++){
            for (int j =0; j<mapSize;j++){
                map.add(new Coordinates(i,j));
            }
        }
        Simulation.setMap(map);
        }
    }

