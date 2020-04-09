package ooga.game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import ooga.EntityAPI;
import ooga.data.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a rough draft of a Level that will work for now. More should likely be added later.
 * As Braeden doesn't have End Conditions fully fleshed out at this momment, this is just returning false, but
 * this will obviously change once he or the team decides how we are going to represent those.
 * TODO: fully flesh this out to be a working class
 */
public class OogaLevel implements Level{
    private ObservableList<EntityAPI> myEntities;

    public OogaLevel(List<EntityAPI> Entities){
//        myEntities = (ObservableList<EntityAPI>) Entities;
        myEntities = FXCollections.observableArrayList(Entities);
    }

    public OogaLevel() {
        this(new ArrayList<>());
    }

    @Override
    public ObservableList<EntityAPI> getEntities() {
        return myEntities;
    }

    @Override
    public void removeEntity(EntityAPI e) {
        myEntities.removeAll(List.of(e));
    }

    @Override
    public boolean checkEndCondition() {
        return false;
    }
}
