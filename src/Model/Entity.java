package Model;

public abstract class Entity {
   protected Entity(Coordinates coordinates,EntityType entityType){
      this.coordinates = coordinates;
      this.entityType = entityType;
   }
   protected Coordinates coordinates;
   public Coordinates getCoordinates(){
      return coordinates;
   }

   protected void setCoordinates(Coordinates coordinates){
      this.coordinates = coordinates;
   }
   protected EntityType entityType;
   public EntityType getType(){
      return entityType != null? entityType: EntityType.VOID;
   }
}
