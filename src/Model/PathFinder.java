package Model;

import java.util.*;


public final class PathFinder {
    private PathFinder() {
    }

    private static final int[][] directions = {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };
    private static EntityType target;

    public static List<Coordinates>
    getPath(EntityMap map, Coordinates startPosition, Creature creature) {
        setCreatureTarget(creature);

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startPosition);

        Map<Coordinates,Coordinates> parent = new HashMap<>();
        Set<Coordinates> visitedDirections = new HashSet<>();
        visitedDirections.add(startPosition);
       // int counter = 0;

        while (!queue.isEmpty()) {
            Coordinates cell = queue.poll();
           // if (counter > 1000) throw new RuntimeException("infinity loop");
            for (var dir : directions) {
                Coordinates nextCell = new Coordinates(cell.getCoordinateX() + dir[0], cell.getCoordinateY() + dir[1]);
                if (!mapHasCell(map, nextCell) || !isCellAvailable(map, nextCell)) {
                    continue;
                } else if (visitedDirections.contains(nextCell)) {
                    continue;
                } else if (map.getMap().get(nextCell) == null) {
                    parent.put(nextCell,cell);
                    visitedDirections.add(nextCell);
                    queue.add(nextCell);
                    continue;
                } else if (map.getMap().get(nextCell).getType().equals(target)) {
                   // System.out.println("target");
                    parent.put(nextCell,cell);
                    visitedDirections.add(nextCell);
                    return buildPath(parent,nextCell);
                }
                parent.put(nextCell,cell);
                visitedDirections.add(nextCell);
                queue.add(nextCell);
            }
            //counter++;
        }
        return buildPath(parent,null);
    }

    private static void setCreatureTarget(Creature creature) {
        switch (creature.entityType) {
            case EntityType.PREDATOR:
                target = EntityType.HERBIVORE;
                break;
            case EntityType.HERBIVORE:
                target = EntityType.GRASS;
                break;
        }
    }

    private static boolean mapHasCell(EntityMap map, Coordinates cell) {
        int border = map.getSIZE();
        int x = cell.getCoordinateX();
        int y = cell.getCoordinateY();
        if (x < 0 || y < 0) {
            return false;
        }
        return (x < border) && (y < border);
    }

    private static boolean isCellAvailable(EntityMap map, Coordinates cell) {
        if (map.getMap().get(cell) == null) return true;
        EntityType entityType = map.getMap().get(cell).getType();
        if (entityType.equals(target)) {
            return true;
        }
        return false;

    }

    static List<Coordinates> buildPath(Map<Coordinates, Coordinates> parent, Coordinates target) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates cur = target;

        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }

        Collections.reverse(path);
//        if (!path.isEmpty()) {
//            path.removeFirst();
//        }
        return path;
    }
}
