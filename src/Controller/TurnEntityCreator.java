package Controller;

import Model.Coordinates;
import Model.EntityMap;
import Model.EntityType;

import java.util.List;
import java.util.Map;

public class TurnEntityCreator extends EntityCreator implements Action {
    public TurnEntityCreator(){
        super();
    }


    @Override
    public void execute(EntityMap map) {

        boolean hasPredatorFood = false;
        boolean hasHerbivoreFood = false;

      for (var entity : map.getNotNullEntities()){
          switch(entity.getType()){
              case EntityType.HERBIVORE:
                  hasPredatorFood = true;
                  break;
              case EntityType.GRASS:
                  hasHerbivoreFood = true;
                  break;
          }
      }
        Map<EntityType,Boolean> hasFoodForCreature = Map.of(EntityType.HERBIVORE,hasHerbivoreFood,EntityType.PREDATOR,hasPredatorFood);
        for(var entry:hasFoodForCreature.entrySet()){
            if(!entry.getValue()){
               addMissingFood(entry.getKey(),map);
            }
        }
      Simulation.setMap(map);
    }
    private void addMissingFood(EntityType hungryCreature, EntityMap map){

        int foodQuantity = random.nextInt(1,Simulation.getMapSize());

        for ( int i = 0; i < foodQuantity; i++){
            List<Coordinates> voidCells = Simulation.getEntityMap().getVoidCells();
            Coordinates voidCell = voidCells.get(random.nextInt(voidCells.size()));
            map.add(voidCell, getEntityFromType(getFoodType(hungryCreature), voidCell));
        }
    }
    private EntityType getFoodType(EntityType creatureType){
        return switch (creatureType){
            case HERBIVORE -> EntityType.GRASS;
            case PREDATOR -> EntityType.HERBIVORE;
            default -> throw new IllegalArgumentException("unexpected creature type: " + creatureType);
        };
    }
}

