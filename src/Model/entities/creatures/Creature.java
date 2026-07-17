package Model.entities.creatures;

import Model.map.Coordinates;
import Model.entities.Entity;
import Model.entities.EntityType;

public abstract class Creature extends Entity {

    public static final int MAX_DISTANCE_TO_MULTIPLY = 4;
    public static final int MAX_TURNS_WITHOUT_FOOD = 7;
    protected int speed;
    protected int healthPoints;
    protected boolean isAlive = true;
    protected int turnsWithoutFood = 0;
    protected boolean canMultiply = true;

    protected Creature(Coordinates coordinates, EntityType entityType, int healthPoints, int speed) {
        super(coordinates, entityType);
        this.speed = speed;
        this.healthPoints = healthPoints;
    }

    public boolean isCanMultiply() {
        return canMultiply;
    }

    public void setCanMultiply(boolean canMultiply) {
        this.canMultiply = canMultiply;
    }

    public abstract void restoreHealthPoints();

    public void kill() {
        this.isAlive = false;
    }

    public boolean isDead() {
        return !isAlive;
    }

    public void makeMove(Coordinates newCoordinates) {
        setCoordinates(newCoordinates);
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int newValue) {
        this.speed = newValue;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setTurnsWithoutFood(int newValue) {
        turnsWithoutFood = newValue;
    }

    public int getTurnsWithoutFood() {
        return turnsWithoutFood;
    }
}
