package Model.pathfind;

import Model.map.Coordinates;
import Model.map.Directions;
import Model.map.EntityMap;
import Model.entities.EntityType;
import Model.entities.creatures.Creature;
import Model.util.CellUtils;

import java.util.*;


public final class PathFinder {

    private static final Random RANDOM = new Random();
    private static EntityMap map;
    private static Coordinates startPosition;

    private PathFinder() {
    }

    public static List<Coordinates> getPath(EntityMap map, Coordinates startPosition, Creature creature) {
        PathFinder.map = map;
        PathFinder.startPosition = startPosition;
        EntityType target = setCreatureTarget(creature);

        Optional<List<Coordinates>> path = getShortestPathToTarget(target);
        if (path.isPresent()) {
            return path.get();
        }
        return randomNextCell();
    }

    private static EntityType setCreatureTarget(Creature creature) {
        if (isAvailableToMultiply(creature)) {

            return creature.getType();
        }
        return switch (creature.getType()) {
            case EntityType.PREDATOR -> EntityType.HERBIVORE;
            case EntityType.HERBIVORE -> EntityType.GRASS;
            default -> throw new IllegalArgumentException("Unexpected Creature: " + creature.getType());
        };
    }

    private static boolean isAvailableToMultiply(Creature creature) {
        return hasPartnersNear(creature) && creature.isCanMultiply();
    }

    private static boolean hasPartnersNear(Creature creature) {
        Optional<List<Coordinates>> path = getShortestPathToTarget(creature.getType());
        if (path.isPresent()) {
            Creature partner = (Creature) map.get(path.get().getLast());
            return path.get().size() <= Creature.MAX_DISTANCE_TO_MULTIPLY && partner.isCanMultiply();
        }
        return false;
    }

    private static Optional<List<Coordinates>> getShortestPathToTarget(EntityType target) {
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startPosition);

        Map<Coordinates, Coordinates> parent = new HashMap<>();
        Set<Coordinates> visitedDirections = new HashSet<>();
        visitedDirections.add(startPosition);


        while (!queue.isEmpty()) {
            Coordinates cell = queue.poll();
            for (var dir : Directions.FOUR_NEAR_DIRECTIONS) {
                Coordinates nextCell = new Coordinates(cell.getCoordinateX() + dir[0], cell.getCoordinateY() + dir[1]);
                if (mapHasNoCell(nextCell) || (!CellUtils.isCellVoid(nextCell, map) && !CellUtils.isCellTarget(nextCell, target, map))) {
                    continue;
                } else if (visitedDirections.contains(nextCell)) {
                    continue;
                } else if (CellUtils.isCellVoid(nextCell, map)) {
                    parent.put(nextCell, cell);
                    visitedDirections.add(nextCell);
                    queue.add(nextCell);
                    continue;

                } else if (CellUtils.isCellTarget(nextCell, target, map)) {

                    parent.put(nextCell, cell);
                    visitedDirections.add(nextCell);
                    return Optional.of(buildPath(parent, nextCell));
                }
                parent.put(nextCell, cell);
                visitedDirections.add(nextCell);
                queue.add(nextCell);
            }
        }
        return Optional.empty();
    }

    private static boolean mapHasNoCell(Coordinates cell) {
        int border = map.size();
        int x = cell.getCoordinateX();
        int y = cell.getCoordinateY();
        if (x < 0 || y < 0) {
            return true;
        }
        return (x >= border) || (y >= border);
    }


    private static List<Coordinates> buildPath(Map<Coordinates, Coordinates> parent, Coordinates target) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates targetCell = target;

        while (targetCell != null) {
            path.add(targetCell);
            targetCell = parent.get(targetCell);
        }

        Collections.reverse(path);
        return path;
    }

    private static List<Coordinates> randomNextCell() {

        Coordinates nextCell;
        Set<int[]> visitedDirections = new HashSet<>();

        while (visitedDirections.size() != Directions.FOUR_NEAR_DIRECTIONS.length) {
            int[] dir = Directions.FOUR_NEAR_DIRECTIONS[PathFinder.RANDOM.nextInt(Directions.FOUR_NEAR_DIRECTIONS.length)];
            visitedDirections.add(dir);

            nextCell = new Coordinates(startPosition.getCoordinateX() + dir[0], startPosition.getCoordinateY() + dir[1]);
            if (mapHasNoCell(nextCell) || !CellUtils.isCellVoid(nextCell, map)) {
                continue;
            }
            return List.of(nextCell);
        }
        return List.of();
    }
}
