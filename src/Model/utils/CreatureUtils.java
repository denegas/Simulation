package Model.utils;

import Model.Creature;
import Model.Entity;
import Model.EntityType;

public final class CreatureUtils {
    private CreatureUtils(){}

    public static boolean isPredator(Creature creature) {
        return creature.getType().equals(EntityType.PREDATOR);
    }
    public static boolean isHerbivore(Creature creature) {
        return creature.getType().equals(EntityType.HERBIVORE);
    }
    public static boolean isHerbivore(Entity creature) {
        return creature.getType().equals(EntityType.HERBIVORE);
    }
}
