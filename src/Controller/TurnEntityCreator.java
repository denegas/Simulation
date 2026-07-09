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
    private final int MAX_TURNS_WITHOUT_FOOD = 3;
    private int withoutHerbivoreFoodCounter = 0;
    private int withoutPredatorFoodCounter = 0;


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

            if (withoutPredatorFoodCounter > MAX_TURNS_WITHOUT_FOOD){
                addMissingFood(EntityType.PREDATOR,map);
                withoutPredatorFoodCounter = 0;
            }
            if ( withoutHerbivoreFoodCounter > MAX_TURNS_WITHOUT_FOOD){
                addMissingFood(EntityType.HERBIVORE,map);
                withoutHerbivoreFoodCounter  = 0;
            }
            if(!entry.getValue()){
                 increaseCounter(entry.getKey());
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
        return switch (creatureType) {
            case HERBIVORE -> EntityType.GRASS;
            case PREDATOR -> EntityType.HERBIVORE;
            default -> throw new IllegalArgumentException("unexpected creature type: " + creatureType);
        };
    }
    private void increaseCounter(EntityType entityType) {
 switch (entityType){
     case HERBIVORE: withoutHerbivoreFoodCounter++;
     break;
     case PREDATOR:
         withoutPredatorFoodCounter++;
         break;
     default:
         throw new IllegalArgumentException("unexpected entityType: " + entityType);

 }
    }
}

