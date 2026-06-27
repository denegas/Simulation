package Controller;

import Model.Coordinates;
import Model.EntityMap;

public class MapCreator implements Action{
    public MapCreator(EntityMap map){
        this.map = map;
    }
    private final EntityMap map;
    @Override
    public void execute(){
        int mapSize = Simulation.getMapSize();
        for (int i =0;i<mapSize;i++){
            for (int j =0; j<mapSize;j++){
                map.add(new Coordinates(i,j));
            }
        }
        Simulation.setMap(map.getMap());
        }
    }

