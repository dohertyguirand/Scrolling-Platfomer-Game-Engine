package ooga.game;

import ooga.EntityAPI;

import java.util.ArrayList;
import java.util.List;

public class OogaLevel implements Level {

    List<EntityAPI> myEntities;

    public OogaLevel(List<EntityAPI> entities) {
        myEntities = entities;
    }

    public OogaLevel() {
        this(new ArrayList<>());
    }

    @Override
    public List<EntityAPI> getEntities() {
        return new ArrayList<>(myEntities);
    }

    @Override
    public boolean checkEndCondition() {
        return false;
    }
}
