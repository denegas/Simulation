public abstract class Creature extends Entity {
    protected Creature(int coordinateX, int coordinateY,int healthPoints,int speed,Food foodType){
        super(coordinateX,coordinateY);
        this.speed = speed;
        this.foodType = foodType;
        this.healthPoints = healthPoints;
    }
    protected final int speed;
    protected final Food foodType;
    protected int healthPoints;

    public abstract void makeMove();
    public abstract void eat();
    protected void setHealthPoints(int healthPoints){
        this.healthPoints = healthPoints;
    }
    protected int getHealthPoints(){
        return healthPoints;
    }
}
