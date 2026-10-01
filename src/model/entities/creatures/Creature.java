package model.entities.creatures;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;
import model.service.HungryService;
import resources.SimulationConfig;


public abstract class Creature extends Entity {
    protected final HungryService hungryService = new HungryService(SimulationConfig.CREATURE_MAX_TURNS_WITHOUT_FOOD_BEFORE_HUNGER,
                                                                    SimulationConfig.HUNGER_HP_LOSS, SimulationConfig.PREDATOR_LOW_SPEED);

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
