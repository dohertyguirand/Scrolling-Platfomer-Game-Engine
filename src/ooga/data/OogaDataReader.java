package ooga.data;

import ooga.Entity;
import ooga.OogaDataException;
import ooga.game.Game;
import ooga.game.Level;
import ooga.game.OogaLevel;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.BehaviorInstance;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.OogaVariableCondition;
import ooga.game.behaviors.VariableCondition;
import ooga.game.behaviors.comparators.VariableComparator;
import ooga.view.OogaProfile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Class.forName;

/**
 * info @ https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * TODO: create a better header
 */

public class OogaDataReader implements DataReader{

    //TODO: put all magic strings (especially xml related stuff) in resource file
    private static final Object PATH_TO_CLASSES = "ooga.game.behaviors.";
    private final String myLibraryFilePath;   //the path to the folder in which is held every folder for every game that will be displayed and run
    private static final String DEFAULT_LIBRARY_FILE = "data/games-library";
    private static final String DEFAULT_USERS_FILE = "data/users";
    private static final String EFFECTS_PROPERTIES_LOCATION = "ooga/data/resources/effects";
    private static final String ACTIONS_PROPERTIES_LOCATION = "ooga/data/resources/actions";
    private static final String COMPARATORS_PROPERTIES_LOCATION = "ooga/data/resources/comparators";
    private final ResourceBundle myEffectsResources = ResourceBundle.getBundle(EFFECTS_PROPERTIES_LOCATION);
    private final ResourceBundle myActionsResources = ResourceBundle.getBundle(ACTIONS_PROPERTIES_LOCATION);
    private final ResourceBundle myComparatorsResources = ResourceBundle.getBundle(COMPARATORS_PROPERTIES_LOCATION);

    public OogaDataReader(String givenFilePath){
        myLibraryFilePath = givenFilePath;
    }
    public OogaDataReader(){
        this(DEFAULT_LIBRARY_FILE);
    }

    @Override
    public List<Thumbnail> getThumbnails() throws OogaDataException {
        // TODO: when OogaDataReader is constructed, check that libraryFile is a directory and isn't empty and that the gameDirectories aren't empty
        ArrayList<Thumbnail> thumbnailList = new ArrayList<>();
        for (File gameFile : getAllXMLFiles(myLibraryFilePath)){
            // create a new document to parse
            File fXmlFile = new File(String.valueOf(gameFile));
            Document doc = getDocument(fXmlFile, "Could not parse document.");

            // find the required information in the document
            checkKeyExists(doc, "Name", "Game name missing");
            checkKeyExists(doc, "Description", "Game description missing");
            checkKeyExists(doc, "Thumbnail", "Game thumbnail missing");
            String gameTitle = doc.getElementsByTagName("Name").item(0).getTextContent();
            String gameDescription = doc.getElementsByTagName("Description").item(0).getTextContent();
            String gameThumbnailImageName = doc.getElementsByTagName("Thumbnail").item(0).getTextContent();

            String fullImagePath = "file:" + gameFile.getParentFile() + "/" + gameThumbnailImageName;
            Thumbnail newThumbnail = new Thumbnail(fullImagePath, gameTitle, gameDescription);
            thumbnailList.add(newThumbnail);
        }
        return thumbnailList;
    }

    //TODO: fix usages in other files to make private
    public List<List<String>> getBasicGameInfo(String givenGameName) throws OogaDataException {
        List<List<String>> basicGameInfo = List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        File gameFile = findGame(givenGameName);
        Document doc = getDocument(gameFile, "This error should not ever occur");
        String[] outerTagNames = new String[] {"Level", "Variable", "Variable"};
        String[] innerTagNames = new String[] {"ID", "Name", "StartValue"};
        for(int j=0; j<outerTagNames.length; j++){
            NodeList outerList = doc.getElementsByTagName(outerTagNames[j]);
            // add all elements to the corresponding list
            for (int i = 0; i < outerList.getLength(); i++) {
                Node currentNode = outerList.item(i);
                Element nodeAsElement = (Element) currentNode;
                checkKeyExists(nodeAsElement, innerTagNames[j], "Badly formatted basic game info");
                String newItem = nodeAsElement.getElementsByTagName(innerTagNames[j]).item(0).getTextContent();
                basicGameInfo.get(j).add(newItem);
            }
        }
        return basicGameInfo;
    }

    @Override
    public List<String> getLevelIDs(String gameName) throws OogaDataException {
        return getBasicGameInfo(gameName).get(0);
    }

    @Override
    public Map<String, String> getVariableMap(String gameName) throws OogaDataException {
        Map<String, String> varMap = new HashMap<>();
        List<List<String>> basicGameInfo = getBasicGameInfo(gameName);
        List<String> varList = basicGameInfo.get(1);
        List<String> varValues = basicGameInfo.get(2);
        for (int i=0; i<varList.size(); i++){
            if(i<varValues.size()){
                varMap.put(varList.get(i), varValues.get(i));
            }else{
                varMap.put(varList.get(i), "");
            }
        }
        return varMap;
    }

    @Override
    public List<String> getGameFilePaths() {
        ArrayList<String> FilePaths = new ArrayList<>();
        for(File f : getAllXMLFiles(myLibraryFilePath)){
            FilePaths.add(f.getPath());
        }
        return FilePaths;
    }

    /**
     * For Users and for Library, there is one directory holding several smaller directories, each
     * of which represents a game or user with its stored images and .xml files. This method returns a list
     * of those .xml files.
     * @return a list of xml files stored in subdirectories of the given file.
     * @param rootDirectory
     */
    private List<File> getAllXMLFiles(String rootDirectory){
        ArrayList<File> fileList = new ArrayList<>();
        File libraryFile = new File(rootDirectory);
        // loop through the library and find each game
        for (File gameDirectory : Objects.requireNonNull(libraryFile.listFiles())){
            if(!gameDirectory.isDirectory()) continue;
            // go through the game folder and find the game file
            for (File gameFile : Objects.requireNonNull(gameDirectory.listFiles())){
                // check if the file is a .xml
                String[] splitFile = gameFile.getName().split("\\.");
                String fileExtension = splitFile[splitFile.length-1];
                if(fileExtension.equals("xml")) fileList.add(gameFile);
            }
        }
        return fileList;
    }

    private File findGame(String givenGameName) throws OogaDataException {
        List<File> gameFiles = getAllXMLFiles(myLibraryFilePath);
        for(File f : gameFiles) {
            // check if this game file is the correct game file
            // create a new document to parse
            File fXmlFile = new File(String.valueOf(f));
            Document doc = getDocument(fXmlFile, "Could not parse document.");
            checkKeyExists(doc, "Name", "Game " + givenGameName + " missing name");
            String gameTitle = doc.getElementsByTagName("Name").item(0).getTextContent();
            if (gameTitle.equals(givenGameName)) return f;
        }
        throw new OogaDataException("Requested game name not found in Library");
    }


    @Override
    public Level loadNewLevel(String givenGameName, String givenLevelID) throws OogaDataException {
        File gameFile = findGame(givenGameName);
        Map<String, ImageEntityDefinition> entityMap = getImageEntityMap(givenGameName);
        Document doc = getDocument(gameFile, "This error should not ever occur");
        // in the xml create a list of all 'Level' nodes
        NodeList levelNodes = doc.getElementsByTagName("Level");
        // for each check the ID
        for (int i = 0; i < levelNodes.getLength(); i++) {
            Element level = (Element) levelNodes.item(i);
            String levelID = getLevelID(level);
            if(levelID.equals(givenLevelID)){
                ArrayList<Entity> initialEntities = getInitialEntities(entityMap, level);
                OogaLevel oogaLevel = new OogaLevel(initialEntities, givenLevelID);
                oogaLevel.setNextLevelID(getNextLevelID(level));
                return oogaLevel;
            }
        }
        throw new OogaDataException("No level listed for the given game with the requested ID");
    }

    /**
     * @param entityMap the map returned by getImageEntityMap
     * @param level the requested level as an element in the .xml
     * @return the list of Entity instances as described in the file
     * @throws OogaDataException if file is improperly formatted and does not give the required information about the entities
     */
    private ArrayList<Entity> getInitialEntities(Map<String, ImageEntityDefinition> entityMap, Element level) throws OogaDataException {
        String levelID = getLevelID(level);
        ArrayList<Entity> initialEntities = new ArrayList<>();
        //TODO: refactor the below loops into a single loop
        NodeList imageEntityNodes = level.getElementsByTagName("ImageEntityInstance");
        // for each, save a copy of the specified instance at the specified place
        for (int j = 0; j < imageEntityNodes.getLength(); j++) {
            Node currentEntity = imageEntityNodes.item(j);
            Element entityElement = (Element) currentEntity;
            checkKeyExists(entityElement, "Name", "Entity instance is missing name in level " + levelID);
            String entityName = entityElement.getElementsByTagName("Name").item(0).getTextContent();
            if(!entityMap.containsKey(entityName)) throw new OogaDataException("Unknown entity name: " + entityName);
            String[] parameterNames = new String[] {"XPos", "YPos"};
            List<Double> parameterValues = constructEntity(entityElement, entityName, parameterNames);
            int[] rowsColsAndGaps = getRowsColsAndGaps(entityElement);
            double xPos;
            double yPos = parameterValues.get(1);
            for(int row=0; row<rowsColsAndGaps[0]; row++){
                xPos = parameterValues.get(0);
                for(int col=0;col<rowsColsAndGaps[1];col++){
                    Entity entity = entityMap.get(entityName).makeInstanceAt(xPos,yPos);
//=======
//                checkKeyExists(level, "NextLevel", "Level " + levelID + " is missing NextLevel");
//                nextLevelID = level.getElementsByTagName("NextLevel").item(0).getTextContent();
//                //TODO: refactor the below loops into a single loop
//                NodeList imageEntityNodes = level.getElementsByTagName("ImageEntityInstance");
//                // for each, save a copy of the specified instance at the specified place
//                for (int j = 0; j < imageEntityNodes.getLength(); j++) {
//                    Node currentEntity = imageEntityNodes.item(j);
//                    Element entityElement = (Element) currentEntity;
//                    checkKeyExists(entityElement, "Name", "Entity instance is missing name in level " + levelID);
//                    String entityName = entityElement.getElementsByTagName("Name").item(0).getTextContent();
//                    if(!entityMap.containsKey(entityName)) throw new OogaDataException("Unknown entity name: " + entityName);
//                    String[] parameterNames = new String[] {"XPos", "YPos"};
//                    List<Double> parameterValues = constructEntity(entityElement, entityName, parameterNames);
//                    int[] rowsColsAndGaps = getRowsColsAndGaps(entityElement);
//                    ImageEntityDefinition imageEntityDefinition = entityMap.get(entityName);
//                    double xPos;
//                    double yPos = parameterValues.get(1);
//                    for(int row=0; row<rowsColsAndGaps[0]; row++){
//                        xPos = parameterValues.get(0);
//                        for(int col=0;col<rowsColsAndGaps[1];col++){
//                            Entity entity = entityMap.get(entityName).makeInstanceAt(xPos,yPos);
//                            entity.setPropertyVariableDependencies(getEntityVariableDependencies(entityElement));
//                            entity.setVariables(getEntityVariables(entityElement));
//                            entity.makeNonStationaryProperty(isStationary(entityElement, imageEntityDefinition.getStationary()));
//                            initialEntities.add(entity);
//                            xPos += imageEntityDefinition.getMyWidth()+rowsColsAndGaps[2];
//                        }
//                        yPos += imageEntityDefinition.getMyHeight()+rowsColsAndGaps[3];
//                    }
//                }
//                NodeList textEntityNodes = level.getElementsByTagName("TextEntityInstance");
//                for (int j = 0; j < textEntityNodes.getLength(); j++) {
//                    Node currentEntity = textEntityNodes.item(j);
//                    Element entityElement = (Element) currentEntity;
//                    checkKeyExists(entityElement, "Text", "Text entity instance did not specify text");
//                    checkKeyExists(entityElement, "Font", "Text entity instance did not specify font");
//                    String text = entityElement.getElementsByTagName("Text").item(0).getTextContent();
//                    String font = entityElement.getElementsByTagName("Font").item(0).getTextContent();
//                    String[] parameterNames = new String[] {"XPos", "YPos", "Width", "Height"};
//                    List<Double> parameterValues = constructEntity(entityElement, text, parameterNames);
//                    int index = 0;
//                    Entity entity = new TextEntity(text, font, parameterValues.get(index++), parameterValues.get(index++),
//                            parameterValues.get(index++),  parameterValues.get(index));
//>>>>>>> dev
                    entity.setPropertyVariableDependencies(getEntityVariableDependencies(entityElement));
                    entity.setVariables(getEntityVariables(entityElement));
                    entity.makeNonStationaryProperty(isStationary(entityElement, false));
                    initialEntities.add(entity);
                    xPos += entityMap.get(entityName).getMyWidth()+rowsColsAndGaps[2];
                }
                yPos += entityMap.get(entityName).getMyHeight()+rowsColsAndGaps[3];
            }
        }
        NodeList textEntityNodes = level.getElementsByTagName("TextEntityInstance");
        for (int j = 0; j < textEntityNodes.getLength(); j++) {
            Node currentEntity = textEntityNodes.item(j);
            Element entityElement = (Element) currentEntity;
            checkKeyExists(entityElement, "Text", "Text entity instance did not specify text");
            checkKeyExists(entityElement, "Font", "Text entity instance did not specify font");
            String text = entityElement.getElementsByTagName("Text").item(0).getTextContent();
            String font = entityElement.getElementsByTagName("Font").item(0).getTextContent();
            String[] parameterNames = new String[] {"XPos", "YPos", "Width", "Height"};
            List<Double> parameterValues = constructEntity(entityElement, text, parameterNames);
            int index = 0;
            Entity entity = new TextEntity(text, font, parameterValues.get(index++), parameterValues.get(index++),
                    parameterValues.get(index++),  parameterValues.get(index));
            entity.setPropertyVariableDependencies(getEntityVariableDependencies(entityElement));
            entity.setVariables(getEntityVariables(entityElement));
            //TODO:Verify this is correct vv
            entity.makeNonStationaryProperty(isStationary(entityElement,false));
            initialEntities.add(entity);
        }
        return initialEntities;
    }

    @Override
    public Level loadSavedLevel(String UserName, String Date) throws OogaDataException {
        for (File userFile : getAllXMLFiles(DEFAULT_USERS_FILE)){
            // create a new document to parse
            File fXmlFile = new File(String.valueOf(userFile));
            Document doc = getDocument(fXmlFile, "Could not parse document.");
            // get the name at teh top of the file
            checkKeyExists(doc, "Name", "User file missing username");
            String loadedName = doc.getElementsByTagName("Name").item(0).getTextContent();

            if(!loadedName.equals(UserName)) continue;

            // find where the save file is stored
            checkKeyExists(doc, "Date", "User file missing saves");
            for(int i=0; i<doc.getElementsByTagName("Date").getLength(); i++){
                String loadedDate = doc.getElementsByTagName("Date").item(i).getTextContent();
                if(!loadedDate.equals(Date)) continue;
                String loadFilePath = doc.getElementsByTagName("StateFilePath").item(i).getTextContent();
                return loadLevelAtPath(loadFilePath);
            }
            throw new OogaDataException("User has no save at the given date");
        }
        throw new OogaDataException("No user exists with that username");
    }

    /**
     * @param loadFilePath the path to a saved level
     * @return the loaded level based on the save file
     */
    private Level loadLevelAtPath(String loadFilePath) {

        return null;
    }

    private String getNextLevelID(Element level) throws OogaDataException {
        String levelID = getLevelID(level);
        checkKeyExists(level, "NextLevel", "Level " + levelID + " is missing NextLevel");
        return level.getElementsByTagName("NextLevel").item(0).getTextContent();
    }

    private String getLevelID(Element level) throws OogaDataException {
        checkKeyExists(level, "ID", "Level is missing an ID");
        return level.getElementsByTagName("ID").item(0).getTextContent();
    }

    private Map<String, String> getEntityVariables(Element entityElement) throws OogaDataException {
        Map<String, String> variableMap = new HashMap<>();
        NodeList nameNodes = entityElement.getElementsByTagName("VariableNames");
        NodeList valueNodes = entityElement.getElementsByTagName("VariableValues");
        if(valueNodes.getLength() > 0 && nameNodes.getLength() > 0) {
            String[] variableNames = nameNodes.item(0).getTextContent().split(" ");
            String[] variableValues = valueNodes.item(0).getTextContent().split(" ");
            if(variableNames.length != variableValues.length){
                throw new OogaDataException("Entity variable names and values lists must be same length");
            }
            for(int i=0; i<variableNames.length; i++){
                try {
                    variableMap.put(variableNames[i], variableValues[i]);
                } catch(NumberFormatException e){
                    throw new OogaDataException("Entity variables values must be numeric");
                }
            }
        }
        else if(valueNodes.getLength() > 0 || nameNodes.getLength() > 0){
            throw new OogaDataException("Entity cannot have only one of variable names, values");
        }
        return variableMap;
    }

    /**
     * gets the rows and columns fields of this entity, each defaults to 1 if not specified. Gaps default to 0.
     * @param entityElement element in the xml of this entity
     * @return array of rows, columns, x gap, y gap
     * @throws OogaDataException if either field is not parsable to an int
     */
    private int[] getRowsColsAndGaps(Element entityElement) throws OogaDataException {
        int[] rowsColsAndGap = new int[]{1, 1, 0, 0};
        String[] keys = new String[]{"Rows", "Columns", "XGap", "YGap"};
        for(int i=0; i<rowsColsAndGap.length; i++) {
            NodeList nodes = entityElement.getElementsByTagName(keys[i]);
            if (nodes.getLength() > 0) {
                try {
                    rowsColsAndGap[i] = Integer.parseInt(nodes.item(0).getTextContent());
                } catch(NumberFormatException e){
                    throw new OogaDataException("Row/columns/gap number incorrectly formatted");
                }
            }
        }
        return rowsColsAndGap;
    }

    private List<Double> constructEntity(Element entityElement, String entityName, String[] parameterNames) throws OogaDataException {
        List<Double> parameterValues = new ArrayList<>();
        for(String parameterName : parameterNames){
            try {
                parameterValues.add(Double.parseDouble(entityElement.getElementsByTagName(parameterName).item(0).getTextContent()));
            } catch (IndexOutOfBoundsException | NumberFormatException e){
                throw new OogaDataException("Badly formatted instance of " + entityName + " entity");
            }
        }
        return parameterValues;
    }

    private Map<String, String> getEntityVariableDependencies(Element entityElement) throws OogaDataException {
        Map<String, String> dependencyMap = new HashMap<>();
        NodeList dependencyList = entityElement.getElementsByTagName("PropertyVariableDependency");
        for(int i=0; i<dependencyList.getLength(); i++){
            Element dependencyElement = (Element)dependencyList.item(i);
            checkKeyExists(dependencyElement, "VariableName", "Property variable dependency variable name missing");
            checkKeyExists(dependencyElement, "PropertyName", "Property variable dependency property name missing");
            String variableName = dependencyElement.getElementsByTagName("VariableName").item(0).getTextContent();
            String propertyName = dependencyElement.getElementsByTagName("PropertyName").item(0).getTextContent();
            dependencyMap.put(variableName, propertyName);
        }
        return dependencyMap;
    }

    @Override
    public Map<String, ImageEntityDefinition> getImageEntityMap(String gameName) throws OogaDataException {
        Map<String, ImageEntityDefinition> retMap = new HashMap<>();
        File gameFile = findGame(gameName);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gameFile);
            // in the xml create a list of all 'Level' nodes
            NodeList entityNodes = doc.getElementsByTagName("ImageEntity");
            // add all entities to the map
            for (int i = 0; i < entityNodes.getLength(); i++) {
                // create a new entity
                Node currentEntity = entityNodes.item(i);
                // if the entity has an image, it is an imageEntity
                Element entityElement = (Element) currentEntity;
                // add the ImageEntity to the map
                checkKeyExists(entityElement, "Name", "Image entity definition missing name");
                String newName = entityElement.getElementsByTagName("Name").item(0).getTextContent();
                ImageEntityDefinition newIED = createImageEntityDefinition(entityElement, gameFile.getParentFile().getName());
                newIED.setVariables(getEntityVariables(entityElement));
                retMap.put(newName, newIED);
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("This error should not ever occur");
        }
        return retMap;
    }

    private void checkKeyExists(Element element, String key, String errorMessage) throws OogaDataException {
        if(element.getElementsByTagName(key).getLength() == 0)
            throw new OogaDataException(errorMessage);
    }

    private void checkKeyExists(Document document, String key, String errorMessage) throws OogaDataException {
        if(document.getElementsByTagName(key).getLength() == 0)
            throw new OogaDataException(errorMessage);
    }

    /**
     * Read the .xml file and create a new EntityDefinition as it describes
     * @param entityElement the Node describing the requested Entity
     * @param gameDirectory
     * @return
     */
    private ImageEntityDefinition createImageEntityDefinition(Element entityElement, String gameDirectory) throws OogaDataException {
        for(String key : new String[]{"Name", "Height", "Width", "Image"}){
            checkKeyExists(entityElement, key, "Entity missing " + key + " data");
        }
        String name = entityElement.getElementsByTagName("Name").item(0).getTextContent();
        double height = Double.parseDouble(entityElement.getElementsByTagName("Height").item(0).getTextContent());
        double width = Double.parseDouble(entityElement.getElementsByTagName("Width").item(0).getTextContent());
        String imagePath = "file:" + myLibraryFilePath + "/" + gameDirectory + "/" + entityElement.getElementsByTagName("Image").item(0).getTextContent();
        boolean stationary = isStationary(entityElement, false);

        List<ConditionalBehavior> behaviors = new ArrayList<>();
        NodeList nodeList = entityElement.getElementsByTagName("Behavior");
        for (int i=0; i<nodeList.getLength(); i++){
            Element behaviorElement = (Element) nodeList.item(i);
            Map<String, Boolean> inputConditions = new HashMap<>();
            Map<List<String>, String> requiredCollisionConditions = new HashMap<>();
            Map<List<String>, String> bannedCollisionConditions = new HashMap<>();
            addCollisionConditions(requiredCollisionConditions, behaviorElement.getElementsByTagName("RequiredCollisionCondition"), name);
            addCollisionConditions(bannedCollisionConditions, behaviorElement.getElementsByTagName("BannedCollisionCondition"), name);
            addOneParameterConditions(inputConditions, behaviorElement.getElementsByTagName("InputCondition"), "Key", "InputRequirement");
            List<VariableCondition> gameVariableConditions = getGameVariableConditions(behaviorElement.getElementsByTagName("GameVariableCondition"));
            Map<String,List<VariableCondition>> entityVarConditions = getEntityVariableConditions(behaviorElement.getElementsByTagName("EntityVariableCondition"));
            behaviors.add(new BehaviorInstance(gameVariableConditions,entityVarConditions,inputConditions,requiredCollisionConditions,bannedCollisionConditions,getActions(behaviorElement)));
        }

        ImageEntityDefinition imageEntityDefinition = new ImageEntityDefinition(name, height, width, imagePath, behaviors);
        imageEntityDefinition.setStationary(stationary);
        return imageEntityDefinition;
    }

    private boolean isStationary(Element entityElement, boolean defaultValue) {
        if(entityElement.getElementsByTagName("Stationary").getLength() > 0){
            return Boolean.parseBoolean(entityElement.getElementsByTagName("Stationary").item(0).getTextContent());
        }
        return defaultValue;
    }

    private List<VariableCondition> getGameVariableConditions(NodeList conditions)
        throws OogaDataException {
        List<VariableCondition> variableConditions = new ArrayList<>();
        for (int i = 0; i < conditions.getLength(); i ++) {
            Element variableConditionElement = (Element) conditions.item(i);
            String name = variableConditionElement.getElementsByTagName("VariableName").item(0).getTextContent();
            String requiredValue = variableConditionElement.getElementsByTagName("RequiredValue").item(0).getTextContent();
            VariableComparator comparator = getComparator(variableConditionElement);
            variableConditions.add(new OogaVariableCondition(name,comparator,requiredValue));
        }
        return variableConditions;
    }

    private void addCollisionConditions(Map<List<String>, String> collisionConditionsMap, NodeList collisionConditionNodes,
                                        String entityName) throws OogaDataException {
        for(int i=0; i<collisionConditionNodes.getLength(); i++){
            Element collisionConditionElement = (Element)collisionConditionNodes.item(i);
            String entity1Info;
            if(collisionConditionElement.getElementsByTagName("Entity1").getLength() == 0) entity1Info = entityName;
            else entity1Info = collisionConditionElement.getElementsByTagName("Entity1").item(0).getTextContent();
            checkKeyExists(collisionConditionElement, "Entity2", "Missing entity2 for collision condition in " + entityName + " entity");
            checkKeyExists(collisionConditionElement, "Direction", "Missing direction for collision condition in " + entityName + " entity");
            String entity2Info = collisionConditionElement.getElementsByTagName("Entity2").item(0).getTextContent();
            String direction = collisionConditionElement.getElementsByTagName("Direction").item(0).getTextContent();
            collisionConditionsMap.put(List.of(entity1Info, entity2Info), direction);
        }
    }

    private List<Action> getActions(Element behaviorElement) throws OogaDataException {
        List<Action> actions = new ArrayList<>();
        String[] actionTypes = new String[]{"CollisionDetermined", "IdDetermined", "NameDetermined", "VariableDetermined", "Independent"};
        for(String actionType: actionTypes) {
            NodeList actionNodes = behaviorElement.getElementsByTagName(actionType + "Action");
            for(int i=0; i<actionNodes.getLength(); i++){
                NodeList argsNodes = ((Element) actionNodes.item(i)).getElementsByTagName("Args");
                List<String> args = new ArrayList<>();
                if(argsNodes.getLength() > 0) args = Arrays.asList(argsNodes.item(0).getTextContent().split(" "));
                NodeList effectNodes = behaviorElement.getElementsByTagName("Effect");
                List<Effect> effects = new ArrayList<>();
                for (int j = 0; j < effectNodes.getLength(); j++) {
                    String[] effectStrings = effectNodes.item(j).getTextContent().split(" ");
                    Effect effect = makeBasicEffect(effectStrings);
                    effects.add(effect);
                }
                actions.add(makeAction(actionType, args, effects));
            }
        }
        return actions;
    }

    private Action makeAction(String actionType, List<String> args, List<Effect> effects) throws OogaDataException {
        String effectClassName = myActionsResources.getString(actionType + "Action");
        try {
            Class cls = forName(PATH_TO_CLASSES + effectClassName);
            Constructor cons = cls.getConstructor(List.class, List.class);
            return (Action)cons.newInstance(args, effects);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e){
            throw new OogaDataException(actionType + " Action listed in game file is not recognized.\n Action name: " + actionType);
        } catch(InvocationTargetException e){ // this should be OogaDataException but it won't work because reflection is used
            throw new OogaDataException(actionType + " Action argument list not formatted correctly");
        }
    }

    private Map<String,List<VariableCondition>> getEntityVariableConditions(NodeList variableConditionNodes) throws OogaDataException{
        Map<String,List<VariableCondition>> entityVariableConditions = new HashMap<>();
        for(int j=0; j<variableConditionNodes.getLength(); j++){
            Element variableConditionElement = (Element) variableConditionNodes.item(j);
            //checkKeyExists(variableConditionElement, "EntityNameOrID", "Missing entity name/id in entity variable condition");
            String entityInfo;
            if(variableConditionElement.getElementsByTagName("EntityNameOrID").getLength() == 0) entityInfo = BehaviorInstance.SELF_IDENTIFIER;
            else entityInfo = variableConditionElement.getElementsByTagName("EntityNameOrID").item(0).getTextContent();
            entityVariableConditions.putIfAbsent(entityInfo, new ArrayList<>());
            entityVariableConditions.get(entityInfo).add(getEntityVariableCondition(variableConditionElement));
        }
        return entityVariableConditions;
    }

    private VariableCondition getEntityVariableCondition(Element variableConditionElement) throws OogaDataException {
        checkKeyExists(variableConditionElement, "VariableName", "Missing variable name in variable condition");
        checkKeyExists(variableConditionElement, "RequiredValue", "Missing required value in variable condition");
        String name = variableConditionElement.getElementsByTagName("VariableName").item(0).getTextContent();
        String requiredValue = variableConditionElement.getElementsByTagName("RequiredValue").item(0).getTextContent();
        VariableComparator comparator = getComparator(variableConditionElement);
        return new OogaVariableCondition(name,comparator,requiredValue);
    }

    private VariableComparator getComparator(Element variableConditionElement)
        throws OogaDataException {
        String comparatorClassName = myComparatorsResources.getString("Equals");
        NodeList comparatorType = variableConditionElement.getElementsByTagName("Comparison");
        if (comparatorType.getLength() != 0) {
            comparatorClassName = myComparatorsResources.getString(comparatorType.item(0).getTextContent());
        }
        try {
            Class cls = forName(PATH_TO_CLASSES + comparatorClassName);
            Constructor cons = cls.getConstructor();
            return (VariableComparator)cons.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |InstantiationException e ) {
            throw new OogaDataException("Unknown comparator type " + comparatorClassName + " in variable condition.");
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void addOneParameterConditions(Map<String, Boolean> conditionMap, NodeList conditionNodes, String keyName, String valueName) {
        for(int j=0; j<conditionNodes.getLength(); j++){
            String name = ((Element)conditionNodes.item(j)).getElementsByTagName(keyName).item(0).getTextContent();
            String requirementBoolean = ((Element)conditionNodes.item(j)).getElementsByTagName(valueName).item(0).getTextContent();
            conditionMap.put(name, Boolean.parseBoolean(requirementBoolean));
        }
    }

    private Effect makeBasicEffect(String[] effect) throws OogaDataException {
        String effectName = effect[0];
        try {
            String effectClassName = myEffectsResources.getString(effectName);
            Class cls = forName(PATH_TO_CLASSES + effectClassName);
            Constructor cons = cls.getConstructor(List.class);
            return (Effect)cons.newInstance(Arrays.asList(effect).subList(1, effect.length));
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | MissingResourceException e) {
            throw new OogaDataException(effectName + " effect listed in game file is not recognized.");
        } catch(InvocationTargetException e){ // this should be OogaDataException but it won't work because reflection is used
            throw new OogaDataException(effectName + " effect argument list not formatted correctly");
        }
    }

    @Override
    public List<OogaProfile> getProfiles() throws OogaDataException {
        // TODO: when OogaDataReader is constructed, check that libraryFile is a directory and isn't empty and that the gameDirectories aren't empty
        ArrayList<OogaProfile> profileList = new ArrayList<>();
        for (File userFile : getAllXMLFiles(DEFAULT_USERS_FILE)){
            // create a new document to parse
            File fXmlFile = new File(String.valueOf(userFile));
            Document doc = getDocument(fXmlFile, "Could not parse document.");

            // find the required information in the document
            checkKeyExists(doc, "Name", "User file missing username");
            checkKeyExists(doc, "Image", "User file missing image");
            String userName = doc.getElementsByTagName("Name").item(0).getTextContent();
            String userImage = doc.getElementsByTagName("Image").item(0).getTextContent();

            String fullImagePath = "file:" + userFile.getParentFile() + "/" + userImage;
            OogaProfile newProfile = new OogaProfile();
            newProfile.setProfileName(userName);
            newProfile.setProfilePhoto(fullImagePath);


            profileList.add(newProfile);
        }
        return profileList;
    }

    private Document getDocument(File fXmlFile, String s) throws OogaDataException {
        Document doc;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new OogaDataException(s);
        }
        return doc;
    }

    @Override
    public void addNewProfile(OogaProfile newProfile) throws OogaDataException {
        //TODO: make sure profile doesn't already exist
        String newProfileName = newProfile.getProfileName();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            String directory = DEFAULT_USERS_FILE+"/"+newProfileName;
            String filepath = directory+"/"+newProfileName+".xml";
            File file = new File(directory);
            boolean bool = file.mkdir();
            if(bool){
                System.out.println("Directory created successfully");
            }else{
                System.out.println("Sorry could not create specified directory");
            }

            // root element
            Element root = document.createElement("User");
            document.appendChild(root);

            // name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(newProfileName));
            root.appendChild(nameElement);


            String newProfileImage = newProfile.getProfilePhotoPath();
            //copy image into the new user's folder
            Path src = Paths.get(newProfileImage);
            String imageName = newProfileImage.split("/")[newProfileImage.split("/").length-1];
            String copiedProfileImage = directory+"/"+imageName; //
            Path dest = Paths.get(copiedProfileImage);
            Files.copy(src, dest);

            //change the directory stored in the given Profile to point to this new copy of the image
            newProfile.setProfilePhoto(imageName);

            // Image element
            Element imageElement = document.createElement("Image");
            imageElement.appendChild(document.createTextNode(imageName));
            root.appendChild(imageElement);

            // Saves element
            // Saves are initially empty for new users
            Element saveStateElement = document.createElement("SavedGameStates");
            root.appendChild(saveStateElement);

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filepath));

            // If you use
//             StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException | IOException pce) {
            pce.printStackTrace();
        }
    }

    @Override
    public void saveGameState(String filePath) {

    }

    @Override
    public Game loadGameState(String filePath) {
        return null;
    }

    @Override
    public String getEntityImage(String entityName, String gameFile) {

        return null;
    }
}
