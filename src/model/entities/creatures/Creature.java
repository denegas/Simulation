package model.entities.creatures;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;


public abstract class Creature extends Entity {

    protected int speed;
    protected int healthPoints;
    protected boolean isAlive = true;
    protected int turnsWithoutFood = 0;

    protected Creature(Coordinates coordinates, int healthPoints, int speed) {
        this.coordinates = coordinates;
        this.speed = speed;
        this.healthPoints = healthPoints;
    }

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

    public abstract void makeMove(EntityMap entityMap);

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
