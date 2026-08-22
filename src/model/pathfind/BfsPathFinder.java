package model.pathfind;

import model.entities.Entity;
import model.entitymap.Coordinates;
import model.entitymap.Directions;
import model.entitymap.EntityMap;
import model.util.CellUtils;
import model.util.EntityMapUtils;

import java.util.*;


public final class BfsPathFinder implements PathFinder {

    private static final Random RANDOM = new Random();
    private EntityMap entityMap;
    private Coordinates startPosition;

    public List<Coordinates> getPath(EntityMap map, Coordinates startPosition, Class<? extends Entity> target) {
        this.entityMap = map;
        this.startPosition = startPosition;


        Optional<List<Coordinates>> path = getShortestPathToTarget(target);
        if (path.isPresent()) {
            return path.get();
        }

        return randomNextCell();
    }

    private Optional<List<Coordinates>> getShortestPathToTarget(Class<? extends Entity> target) {
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startPosition);

        Map<Coordinates, Coordinates> parent = new HashMap<>();
        Set<Coordinates> visitedDirections = new HashSet<>();
        visitedDirections.add(startPosition);


        while (!queue.isEmpty()) {
            Coordinates cell = queue.poll();
            for (var dir : Directions.NEAR_DIRECTIONS) {
                Coordinates nextCell = new Coordinates(cell.getX() + dir.getX(), cell.getY() + dir.getY());

                if (!EntityMapUtils.hasMapCell(nextCell, entityMap) || (!CellUtils.isCellVoid(nextCell, entityMap) && !CellUtils.isCellTarget(nextCell, target, entityMap))) {
                    continue;
                }
                if (visitedDirections.contains(nextCell)) {
                    continue;
                }
                if (CellUtils.isCellVoid(nextCell, entityMap)) {
                    parent.put(nextCell, cell);
                    visitedDirections.add(nextCell);
                    queue.add(nextCell);
                    continue;

                }
                if (CellUtils.isCellTarget(nextCell, target, entityMap)) {

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

    private List<Coordinates> buildPath(Map<Coordinates, Coordinates> parent, Coordinates target) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates targetCell = target;

        while (targetCell != null) {
            path.add(targetCell);
            targetCell = parent.get(targetCell);
        }

        Collections.reverse(path);
        return path;
    }

    private List<Coordinates> randomNextCell() {

        Coordinates nextCell;
        Set<Coordinates> visitedDirections = new HashSet<>();

        while (visitedDirections.size() != Directions.NEAR_DIRECTIONS.length) {
            Coordinates dir = Directions.NEAR_DIRECTIONS[BfsPathFinder.RANDOM.nextInt(Directions.NEAR_DIRECTIONS.length)];
            visitedDirections.add(dir);

            nextCell = new Coordinates(startPosition.getX() + dir.getX(), startPosition.getY() + dir.getY());
            if (!EntityMapUtils.hasMapCell(nextCell, entityMap) || !CellUtils.isCellVoid(nextCell, entityMap)) {
                continue;
            }
            return List.of(nextCell);
        }
        return List.of();
    }
}
