package ooga.game;

import ooga.Entity;

import java.util.List;

/**
 * This is a rough draft of a Level that will work for now. More should likely be added later.
 * As Braeden doesn't have End Conditions fully fleshed out at this momment, this is just returning false, but
 * this will obviously change once he or the team decides how we are going to represent those.
 * TODO: fully flesh this out to be a working class
 */
public class OogaLevel implements Level{
    List<Entity> myEntities;

    public OogaLevel(List<Entity> Entities){
        myEntities = Entities;
    }
    @Override
    public List<Entity> getEntities() {
        return myEntities;
    }

    @Override
    public boolean checkEndCondition() {
        return false;
    }
}
