public enum EntityType {
 PREDATOR("🦁"),HERBIVORE("🦓"),TREE("🌳"),ROCK("🌑"),GRASS("🍀"),VOID("--");
 private final String entityView;
 EntityType(String view){
     this.entityView = view;
 }
 public String getEntityView(){
     return this.entityView;
 }
}
