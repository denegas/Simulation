package model.spawning;

import model.entities.Entity;

public record EntitySpawnChance(Class<? extends Entity> type, double chance) {

}
