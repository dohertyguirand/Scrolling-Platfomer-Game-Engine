package ooga.game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import ooga.Entity;

import java.util.ArrayList;

/**
 * This is a rough draft of a Level that will work for now. More should likely be added later.
 * As Braeden doesn't have End Conditions fully fleshed out at this momment, this is just returning false, but
 * this will obviously change once he or the team decides how we are going to represent those.
 * TODO: fully flesh this out to be a working class
 */
public class OogaLevel implements Level{
    private ObservableList<Entity> myEntities;

    public OogaLevel(List<Entity> Entities){
//        myEntities = (ObservableList<EntityAPI>) Entities;
        myEntities = FXCollections.observableArrayList(Entities);
    }

    public OogaLevel() {
        this(new ArrayList<>());
    }

    @Override
    public ObservableList<Entity> getEntities() {
        return myEntities;
    }

    @Override
    public void removeEntity(Entity e) {
        myEntities.removeAll(List.of(e));
    }

    @Override
    public boolean checkEndCondition() {
        return false;
    }
}
