package ooga.game;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import ooga.Entity;

/**
 *
 */
public class OogaLevel implements Level{
    private final ObservableList<Entity> myEntities;
    private String myNextLevelId;
    private final LevelEndCondition myEndCondition;
    private String myId;

    public OogaLevel(List<Entity> entities, String id){
        myEntities = FXCollections.observableArrayList(entities);
        myNextLevelId = "UNDEFINED_NEXT_LEVEL";
        myNextLevelId = "1"; //TODO: REMOVE BECAUSE THIS IS A DEBUG LEVEL ID
        //TODO: Allow assigning of an end condition
        myEndCondition = new CollisionEndCondition(List.of("SmallMario","Flagpole"));
        myId = id;
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
    @Deprecated
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
    public String getLevelId() {
        return myId;
    }
}
