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
    protected boolean isAlive = true;
    public void kill(){
        this.isAlive =false;
    }
    public boolean isAlive(){
        return isAlive;
    }
    public abstract void makeMove(Coordinates coordinates);
    public abstract void eat();
    public int getSpeed(){
        return this.speed;
    }
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
