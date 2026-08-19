package model.entities.creatures;

import model.entitymap.Coordinates;
import model.entities.Entity;

public abstract class Creature extends Entity {

    public static final int MAX_TURNS_WITHOUT_FOOD = 7;
    protected int speed;
    protected int healthPoints;
    protected boolean isAlive = true;
    protected int turnsWithoutFood = 0;

    protected Creature(Coordinates coordinates, int healthPoints, int speed) {
        this.coordinates = coordinates;
        this.speed = speed;
        this.healthPoints = healthPoints;
    }

    public abstract void restoreHealthPoints();

    public abstract Class<? extends Entity> getTarget();
    protected Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }
    protected void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void kill() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void makeMove(Coordinates Coordinates) {
        setCoordinates(Coordinates);
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setTurnsWithoutFood(int turnsWithoutFood) {
        this.turnsWithoutFood = turnsWithoutFood;
    }

    public int getTurnsWithoutFood() {
        return turnsWithoutFood;
    }
}
