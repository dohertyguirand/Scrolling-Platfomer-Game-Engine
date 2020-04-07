package ooga.game;

import ooga.Entity;

import java.util.ArrayList;
import java.util.List;

public class OogaLevel implements Level {

    List<Entity> myEntities;

    public OogaLevel(List<Entity> entities) {
        myEntities = entities;
    }

    public OogaLevel() {
        this(new ArrayList<>());
    }

    @Override
    public List<Entity> getEntities() {
        return new ArrayList<>(myEntities);
    }

    @Override
    public boolean checkEndCondition() {
        return false;
    }
}
