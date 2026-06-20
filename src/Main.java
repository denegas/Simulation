import Model.*;

class Main{
    public static void main(String[] args) {
        Coordinates a = new Coordinates(1,0);
        Coordinates b = new Coordinates(0,0);
        Creature herb = new Herbivore(a,1,2);
        Creature pred = new Predator(b,1,2);

        EntityMap.add(herb.getCoordinates(),herb);
        EntityMap.add(pred.getCoordinates(),pred);
        EntityMap.getMap().forEach((coordinates, entity) -> System.out.println(coordinates.getCoordinateX()+ entity.getType().getEntityView()));

    }
}
