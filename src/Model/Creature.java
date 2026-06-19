package Model;

public abstract class Creature extends Entity {
    protected Creature(Coordinates coordinates,EntityType entityType,int healthPoints,int speed,Food foodType){
        super(coordinates,entityType);
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
    protected Food getFoodType(){
        return foodType;
    }
}
