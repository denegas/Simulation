public abstract class Entity {
   protected Entity(int coordinateX,int coordinateY){
      this.coordinateX = coordinateX;
      this.coordinateY = coordinateY;
   }
   protected int coordinateX;
   protected int coordinateY;
   protected int getCoordinateX(){
      return coordinateX;
   }
   protected int getCoordinateY(){
      return coordinateY;
   }
   protected void setCoordinateX(int coordinateX){
      this.coordinateX = coordinateX;
   }
   protected void setCoordinateY(int coordinateY){
      this.coordinateY = coordinateY;
   }
}
