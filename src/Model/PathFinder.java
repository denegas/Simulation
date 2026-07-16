package Model;

import java.util.*;


public final class PathFinder {

    private static EntityMap map;
    private static Coordinates startPosition;
    private static final int[][] DIRECTIONS = {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };

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
        return switch (creature.entityType) {
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
            for (var dir : DIRECTIONS) {
                Coordinates nextCell = new Coordinates(cell.getCoordinateX() + dir[0], cell.getCoordinateY() + dir[1]);
                if (mapHasNoCell(nextCell) || (!isCellVoid(nextCell) && !isCellTarget(nextCell, target))) {
                    continue;
                } else if (visitedDirections.contains(nextCell)) {
                    continue;
                } else if (isCellVoid(nextCell)) {
                    parent.put(nextCell, cell);
                    visitedDirections.add(nextCell);
                    queue.add(nextCell);
                    continue;

                } else if (isCellTarget(nextCell, target)) {

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
        Random random = new Random();
        Coordinates nextCell;
        Set<int[]> visitedDirections = new HashSet<>();

        while (visitedDirections.size() != DIRECTIONS.length) {
            int[] dir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            visitedDirections.add(dir);

            nextCell = new Coordinates(startPosition.getCoordinateX() + dir[0], startPosition.getCoordinateY() + dir[1]);
            if (mapHasNoCell(nextCell) || !isCellVoid(nextCell)) {
                continue;
            }
            return List.of(nextCell);
        }
        return List.of();
    }

    private static boolean isCellVoid(Coordinates cell) {
        return map.get(cell) == null;
    }

    private static boolean isCellTarget(Coordinates cell, EntityType target) {
        if (map.get(cell) == null) return false;
        return map.get(cell).getType().equals(target);
    }
}
