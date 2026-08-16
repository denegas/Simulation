package view.renderer;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;
import model.entities.EntityType;

public final class ConsoleRenderer implements Renderer {
    private static final String SPACE_BETWEEN_ENTITIES = " ";

    @Override
    public void render(EntityMap map) {
        renderOneMap(map);
        // space between two maps
        System.out.println();

    }

    private static void renderOneMap(EntityMap entityMap) {

        int mapWidth = entityMap.getWidth();
        int mapHeight = entityMap.getHeight();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Entity entity = entityMap.get(new Coordinates(x, y));

                if (isExist(entity)) {
                    printVoid();
                } else {
                    printEntity(entity);
                }
            }
            //next row
            System.out.println();
        }
    }

    private static boolean isExist(Entity entity) {
        return entity == null;
    }

    private static void printVoid() {
        System.out.print(EntityType.VOID.getEntityView() + SPACE_BETWEEN_ENTITIES);
    }

    private static void printEntity(Entity entity) {
        System.out.print(entity.getType().getEntityView() + SPACE_BETWEEN_ENTITIES);
    }
}
