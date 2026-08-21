package view.renderer;

import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.entities.environment.Grass;
import model.entities.environment.Rock;
import model.entities.environment.Tree;
import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;

import java.util.Map;


public final class ConsoleRenderer implements Renderer {

    private static final String SPACE_BETWEEN_ENTITIES = " ";
    private static final String PREDATOR_VIEW = "🦁️ ";
    private static final String HERBIVORE_VIEW = "🦓️ ";
    private static final String GRASS_VIEW = "🍀️ ";
    private static final String ROCK_VIEW = "\uD83E\uDEA8️ ";
    private static final String TREE_VIEW = "\uD83C\uDF33️ ";
    private static final String VOID_VIEW = "⬛️ ";

    private static final Map<Class<? extends Entity>, String> entityView = Map.of(
            Predator.class, PREDATOR_VIEW,
            Herbivore.class, HERBIVORE_VIEW,
            Grass.class, GRASS_VIEW,
            Rock.class, ROCK_VIEW,
            Tree.class, TREE_VIEW
    );


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

                if (!isExists(entity)) {
                    printVoid();
                } else {
                    printEntity(entity);
                }
            }
            //next row
            System.out.println();
        }
    }

    private static boolean isExists(Entity entity) {
        return entity != null;
    }

    private static void printVoid() {
        System.out.print(VOID_VIEW + SPACE_BETWEEN_ENTITIES);
    }

    private static void printEntity(Entity entity) {
        System.out.print(getEntityView(entity) + SPACE_BETWEEN_ENTITIES);
    }

    private static String getEntityView(Entity entity) {
        return entityView.get(entity.getClass());
    }
}
