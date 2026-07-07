package Controller;

import Model.Coordinates;
import Model.Entity;
import Model.EntityMap;

import java.util.Map;

public interface Action {
    public void execute(EntityMap map);
}
