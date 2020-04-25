package ooga.game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import ooga.Entity;

/**
 *
 */
public class OogaLevel implements Level{
    private final ObservableList<EntityInternal> myEntities;
    private String myNextLevelId;
    private final String myId;

    public OogaLevel(List<EntityInternal> entities, String id){
        myEntities = FXCollections.observableArrayList(entities);
        myId = id;
    }

    @Override
    public ObservableList<EntityInternal> getEntities() {
        return myEntities;
    }

    @Override
    public void removeEntity(Entity e) {
        myEntities.removeAll(List.of(e));
    }

    @Override
    public void addEntity(EntityInternal e) {
        if (!myEntities.contains(e)) {
            myEntities.add(e);
        }
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
