public abstract class Creature extends Entity {
    private int speed;
    private int healthPoints;

    public abstract void makeMove();
    public abstract void eat();
}
