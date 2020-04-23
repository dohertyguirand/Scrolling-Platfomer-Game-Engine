//package ooga.game.behaviors.noncollisioneffects;
//
//import ooga.Entity;
//import ooga.data.ImageEntity;
//import ooga.game.GameInternal;
//import ooga.game.behaviors.TimeDelayedEffect;
//
//import java.util.List;
//import java.util.Map;
//
//public class GetImageEffect extends TimeDelayedEffect {
//
//    String newImageFileName = "";
//
//    public GetImageEffect(List<String> args) throws IndexOutOfBoundsException {
//        newImageFileName = args.get(0);
//        if(args.size() > 1){
//            setTimeDelay(args.get(1));
//        }
//    }
//    @Override
////    protected ImageEntity doTimeDelayedEffectwithImage(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
////            ImageEntity imageEntity = (ImageEntity)subject;
////            return newImageFileName;
////
//////            String newImageFilePath = "file:data/games-library/";
//////            if(subject.getVariable(newImageFileName) != null){
//////                newImageFilePath += subject.getVariable(newImageFileName);
//////            } else{
//////                newImageFilePath += newImageFileName;
//////            }
//////            imageEntity.setImageLocation(newImageFilePath);
////    }
//}
