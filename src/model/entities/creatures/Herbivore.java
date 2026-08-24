package model.entities.creatures;

import model.entities.Entity;
import model.entities.environment.Grass;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.service.HungryService;
import model.util.CellUtils;
import resources.SimulationConfig;

import java.util.List;

public class Herbivore extends Creature {

    public static final int MAX_HEALTH_POINTS = 15;
    public static final int SPEED = 1;
    public static final Class<Grass> TARGET = Grass.class;

    public Herbivore(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, healthPoints, speed);
    }

    private void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }

    @Override
    public Class<? extends Entity> getTarget() {
        return TARGET;
    }

    @Override
    public void makeMove(EntityMap entityMap) {
        List<Coordinates> path = SimulationConfig.PATH_FINDER.getPath(entityMap,coordinates, TARGET);
        if (path.isEmpty()){
            HungryService.addHungryTurn(this);
            return;
        }

        int step = Math.min(SPEED, path.size() - SPEED);
        Coordinates nextCell = path.get(step);

        HungryService.apply(this);

        if (CellUtils.isCellGrass(nextCell, entityMap)){
            restoreHealthPoints();
            turnsWithoutFood = 0;
        }

        entityMap.clearCell(coordinates);
        setCoordinates(nextCell);
        entityMap.add(nextCell, this);
    }
}
