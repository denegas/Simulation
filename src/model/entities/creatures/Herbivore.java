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

    public static final Class<Grass> TARGET = Grass.class;

    public Herbivore(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, healthPoints, speed);
    }

    private void restoreHealthPoints() {
        setHealthPoints(SimulationConfig.HERBIVORE_MAX_HEALTH_POINTS);
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

        int step = Math.min(SimulationConfig.HERBIVORE_SPEED, path.size() - SimulationConfig.HERBIVORE_SPEED);
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
