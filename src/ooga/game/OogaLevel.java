package ooga.game;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import ooga.Entity;

/**
 * This is a rough draft of a Level that will work for now. More should likely be added later.
 * As Braeden doesn't have End Conditions fully fleshed out at this momment, this is just returning false, but
 * this will obviously change once he or the team decides how we are going to represent those.
 * TODO: fully flesh this out to be a working class
 */
public class OogaLevel implements Level{
    private ObservableList<Entity> myEntities;
    private String myNextLevelId;
    private LevelEndCondition myEndCondition;
    private String myId;

    public OogaLevel(List<Entity> entities, String id){
//        myEntities = (ObservableList<EntityAPI>) Entities;
        myEntities = FXCollections.observableArrayList(entities);
        myNextLevelId = "UNDEFINED_NEXT_LEVEL";
        myNextLevelId = "1"; //TODO: REMOVE BECAUSE THIS IS A DEBUG LEVEL ID
        //TODO: Allow assigning of an end condition
        myEndCondition = new CollisionEndCondition(List.of("SmallMario","Flagpole"));
        myId = id;
    }

    public OogaLevel(List<Entity> entities, LevelEndCondition condition){
//        myEntities = (ObservableList<EntityAPI>) Entities;
        myEntities = FXCollections.observableArrayList(entities);
        myNextLevelId = "UNDEFINED_NEXT_LEVEL";
        myNextLevelId = "1"; //TODO: REMOVE BECAUSE THIS IS A DEBUG LEVEL ID
        //TODO: Allow assigning of an end condition
        myEndCondition = new CollisionEndCondition(List.of("SmallMario","Flagpole"));
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
    public void addEntity(Entity e) {
        if (!myEntities.contains(e)) {
            myEntities.add(e);
        }
    }

    @Override
    public void addEntities(Collection<Entity> e) {
        for (Entity entity : e) {
            addEntity(entity);
        }
    }

    @Override
    public boolean checkEndCondition() {
        return myEndCondition.isLevelDone();
    }

    @Override
    public String nextLevelID() {
        return myNextLevelId;
    }

    @Override
    public void setNextLevelID(String nextID) {
        myNextLevelId = nextID;
    }

    @Override
    public void registerCollision(String firstEntity, String secondEntity) {
        myEndCondition.registerCollision(firstEntity,secondEntity);
    }

    @Override
    public String getLevelId() {
        return myId;
    }
}
