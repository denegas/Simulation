package Model;

import java.util.*;


public final class PathFinder {
    private PathFinder() {
    }

    private static final int[][] DIRECTIONS = {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };
    private static EntityType target;
    private static EntityMap map;
    private static Coordinates startPosition;
    public static List<Coordinates> getPath(EntityMap map, Coordinates startPosition, Creature creature) {
        setCreatureTarget(creature);
        PathFinder.map = map;
        PathFinder.startPosition = startPosition;
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startPosition);

        Map<Coordinates,Coordinates> parent = new HashMap<>();
        Set<Coordinates> visitedDirections = new HashSet<>();
        visitedDirections.add(startPosition);


        while (!queue.isEmpty()) {
            Coordinates cell = queue.poll();
            for (var dir : DIRECTIONS) {
                Coordinates nextCell = new Coordinates(cell.getCoordinateX() + dir[0], cell.getCoordinateY() + dir[1]);
                if (!mapHasCell(nextCell) || (!isCellFree(nextCell) && !isCellTarget(nextCell))) {
                    continue;
                } else if (visitedDirections.contains(nextCell)) {
                    continue;
                } else if (isCellFree(nextCell)) {
                    parent.put(nextCell,cell);
                    visitedDirections.add(nextCell);
                    queue.add(nextCell);
                    continue;

                } else if (isCellTarget(nextCell)) {

                    parent.put(nextCell,cell);
                    visitedDirections.add(nextCell);
                    return buildPath(parent,nextCell);
                }
                parent.put(nextCell,cell);
                visitedDirections.add(nextCell);
                queue.add(nextCell);
            }

        }
        return randomNextCell();
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

    private static boolean mapHasCell(Coordinates cell) {
        int border = map.getSize();
        int x = cell.getCoordinateX();
        int y = cell.getCoordinateY();
        if (x < 0 || y < 0) {
            return false;
        }
        return (x < border) && (y < border);
    }


    private static List<Coordinates> buildPath(Map<Coordinates, Coordinates> parent, Coordinates target) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates cur = target;

        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }

        Collections.reverse(path);
        return path;
    }
    private static List<Coordinates> randomNextCell(){
        Coordinates nextCell;
        Random random = new Random();
        int counter =0;
        while (true){
            if (counter > 30){return new ArrayList<>();}
            int[] dir = DIRECTIONS[random.nextInt(0,4)];
            nextCell = new Coordinates(startPosition.getCoordinateX() + dir[0], startPosition.getCoordinateY() + dir[1]);
            if (!mapHasCell(nextCell) || (!isCellTarget(nextCell) && !isCellFree(nextCell))) {
                counter++;
                continue;
            }
            return List.of(nextCell);
        }
    }
    private static boolean isCellFree(Coordinates cell){
        return map.getMap().get(cell) == null;
    }
    private static boolean isCellTarget(Coordinates cell){
        if (map.getMap().get(cell) == null) return false;
     return map.getMap().get(cell).getType().equals(target);
    }
}
