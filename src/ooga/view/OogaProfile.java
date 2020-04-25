package ooga.view;

import ooga.Profile;

import java.util.HashMap;
import java.util.Map;

public class OogaProfile implements Profile {
    protected String myProfilePhotoPath;
    protected String myName;
    protected Map<String, Integer> myHighestScores;
    protected String myDefaultImagePath;

    public OogaProfile(String profileName, String imagePath, Map<String, Integer> stats){
        this(profileName,imagePath);
        myHighestScores = stats;
    }

    public OogaProfile(String profilename, String imagePath){
        myName = profilename;
        myProfilePhotoPath = imagePath;
        myHighestScores = new HashMap<>();
    }

    @Override
    public void setProfilePhoto(String photoPath){
        myProfilePhotoPath = photoPath;
    }
    @Override
    public void setProfileName(String name) {
        myName = name;
    }

    @Override
    public void setStats(Map<String,Integer> stats) {
        myHighestScores = stats;
    }

    @Override
    public Map<String,Integer> getStats() {
        return myHighestScores;
    }

    @Override
    public void updateStats(String gameName, Integer highScore) {
        myHighestScores.putIfAbsent(gameName,0);
        myHighestScores.put(gameName,highScore);
    }
    @Override
    public int getGameStats(String gameName) {
        return myHighestScores.get(gameName);
    }

    @Override
    public String getProfileName() {
        return myName;
    }

    @Override
    public  String getProfilePhotoPath(){
        return myProfilePhotoPath;
    }

    @Override
    public String getDefaultImagePath(){return myDefaultImagePath;}


}
