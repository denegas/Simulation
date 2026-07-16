package Model;

public abstract class Entity {
    protected Coordinates coordinates;
    protected EntityType entityType;

    protected Entity(Coordinates coordinates, EntityType entityType) {
        this.coordinates = coordinates;
        this.entityType = entityType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public EntityType getType() {
        return entityType != null ? entityType : EntityType.VOID;
    }

    protected void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }


}
