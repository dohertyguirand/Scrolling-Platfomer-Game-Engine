package ooga.view;

import ooga.data.Thumbnail;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenu extends ScrollMenu {
    List<Thumbnail> thumbnails;

    protected ProfileMenu(){
        super();
        //thumbnails = myDataReader.getThumbnails(); will be added back when data has profile thumbnails
        addDefaultThumbnail();
        addImages(thumbnails);
    }


    public void addProfileThumbnails(List<Thumbnail> newThumbnails){
       List<Thumbnail> list = new ArrayList<>();
        if(newThumbnails != null) list.addAll(newThumbnails);
        list.addAll(thumbnails);
        thumbnails = list;
        //did it like this was just in case the lists that are passed are immutable (ie List.of instead of new ArrayList)
    }
    private void addDefaultThumbnail(){
        if(thumbnails == null) thumbnails = new ArrayList<>();
        thumbnails.add(new Thumbnail("ooga/view/Resources/profilephotos/defaultphoto.jpg", "addNewProfile", "Add a new Profile"));
    }

}
