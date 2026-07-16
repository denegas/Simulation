package View;


import Model.Coordinates;
import Model.Entity;
import Model.EntityMap;
import Model.EntityType;

public final class Renderer {
    private static final String SPACE_BETWEEN_ENTITIES = " ";

    private Renderer() {
    }

    public static void render(EntityMap map) {
        renderOneMap(map);
        // space between two maps
        System.out.println();

    }

    private static void renderOneMap(EntityMap map) {

        int mapSize = map.size();
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                Entity entity = map.get(new Coordinates(x, y));

                if (isExist(entity)) {
                    System.out.print(EntityType.VOID.getEntityView() + SPACE_BETWEEN_ENTITIES);
                } else {
                    System.out.print(entity.getType().getEntityView() + SPACE_BETWEEN_ENTITIES);
                }
            }
            //next row
            System.out.println();
        }
    }

    private static boolean isExist(Entity entity) {
        return entity == null;
    }
}
