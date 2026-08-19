package model.util;
import model.entities.Entity;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;

public final class CreatureUtils {
    private CreatureUtils(){}

    public static <T extends Entity> boolean isPredator(T entity) {
        return entity instanceof Predator;
    }
    public static <T extends Entity> boolean isHerbivore(T entity) {
        return entity instanceof Herbivore;
    }

}
