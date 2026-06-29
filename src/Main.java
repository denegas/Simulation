import Controller.CreatureMove;
import Controller.EntityCreator;
import Controller.MapCreator;
import Controller.Simulation;
import Model.*;
import View.Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Main {
    public static void main(String[] args) {
     Simulation.initialize(10);
     Simulation.nextNTurns(5);
    }

}
