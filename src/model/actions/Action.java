package model.actions;

import model.entitymap.EntityMap;

public interface Action {
    void execute(EntityMap map);
}
