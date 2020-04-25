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

import javax.naming.Name;
import javax.print.Doc;
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
    //TODO: split the data reader into multiple classes
    private static final Object PATH_TO_CLASSES = "ooga.game.behaviors.";
    private final String myLibraryFilePath;   //the path to the folder in which is held every folder for every game that will be displayed and run
    private static final String DEFAULT_LIBRARY_FILE = "data/games-library";
    private static final String DEFAULT_USERS_FILE = "data/users";
    private static final String ENGLISH_PROPERTIES_LOCATION = "ooga/data/resources/english";
    private static final String EFFECTS_PROPERTIES_LOCATION = "ooga/data/resources/effects";
    private static final String ACTIONS_PROPERTIES_LOCATION = "ooga/data/resources/actions";
    private static final String COMPARATORS_PROPERTIES_LOCATION = "ooga/data/resources/comparators";
    private final ResourceBundle myEffectsResources = ResourceBundle.getBundle(EFFECTS_PROPERTIES_LOCATION);
    private final ResourceBundle myActionsResources = ResourceBundle.getBundle(ACTIONS_PROPERTIES_LOCATION);
    private final ResourceBundle myComparatorsResources = ResourceBundle.getBundle(COMPARATORS_PROPERTIES_LOCATION);
    private final ResourceBundle myDataResources = ResourceBundle.getBundle(ENGLISH_PROPERTIES_LOCATION);

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
            Document doc = getDocument(fXmlFile, myDataResources.getString("DocumentParseException"));

            // find the required information in the document
            checkKeyExists(doc, myDataResources.getString("GameNameTag"), myDataResources.getString("MissingGameException"));
            checkKeyExists(doc, myDataResources.getString("DescriptionTag"), myDataResources.getString("GameDescriptionException"));
            checkKeyExists(doc, myDataResources.getString("ThumbnailTag"), myDataResources.getString("ThumbnailException"));
            String gameTitle = doc.getElementsByTagName(myDataResources.getString("GameNameTag")).item(0).getTextContent();
            String gameDescription = doc.getElementsByTagName(myDataResources.getString("DescriptionTag")).item(0).getTextContent();
            String gameThumbnailImageName = doc.getElementsByTagName(myDataResources.getString("ThumbnailTag")).item(0).getTextContent();

            String fullImagePath = myDataResources.getString("PathPrefix") + gameFile.getParentFile() +
                    myDataResources.getString("Slash") + gameThumbnailImageName;
            Thumbnail newThumbnail = new Thumbnail(fullImagePath, gameTitle, gameDescription);
            thumbnailList.add(newThumbnail);
        }
        return thumbnailList;
    }

    //TODO: fix usages in other files to make private
    public List<List<String>> getBasicGameInfo(String givenGameName) throws OogaDataException {
        List<List<String>> basicGameInfo = List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        File gameFile = findGame(givenGameName);
        Document doc = getDocument(gameFile, "");
        String[] outerTagNames = new String[] {myDataResources.getString("LevelTag"),
                myDataResources.getString("GameVariableTag"), myDataResources.getString("GameVariableTag")};
        String[] innerTagNames = new String[] {myDataResources.getString("LevelIDTag"),
                myDataResources.getString("VariableNameTag"), myDataResources.getString("GameVariableStartValueTag")};
        for(int j=0; j<outerTagNames.length; j++){
            NodeList outerList = doc.getElementsByTagName(outerTagNames[j]);
            // add all elements to the corresponding list
            for (int i = 0; i < outerList.getLength(); i++) {
                Node currentNode = outerList.item(i);
                Element nodeAsElement = (Element) currentNode;
                checkKeyExists(nodeAsElement, innerTagNames[j], myDataResources.getString("GameInfoException"));
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
            Document doc = getDocument(fXmlFile, myDataResources.getString("DocumentParseException"));
            checkKeyExists(doc, myDataResources.getString("GameNameTag"), givenGameName + myDataResources.getString("MissingGameException"));
            String gameTitle = doc.getElementsByTagName(myDataResources.getString("GameNameTag")).item(0).getTextContent();
            if (gameTitle.equals(givenGameName)) return f;
        }
        throw new OogaDataException(myDataResources.getString("GameNotFoundException"));
    }

    @Override
    public Level loadNewLevel(String givenGameName, String givenLevelID) throws OogaDataException {
        File gameFile = findGame(givenGameName);
        Map<String, ImageEntityDefinition> entityMap = getImageEntityMap(givenGameName);
        Document doc = getDocument(gameFile, "");
        // in the xml create a list of all 'Level' nodes
        NodeList levelNodes = doc.getElementsByTagName(myDataResources.getString("LevelTag"));
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

    @Override
    public Level loadSavedLevel(String UserName, String Date) throws OogaDataException {
        String levelFilePath = getLevelFilePath(UserName, Date);
        return loadLevelAtPath(levelFilePath);
    }

    @Override
    public List<List<String>> getGameSaves(String userName, String gameName) throws OogaDataException {
        Document doc = getDocForUserName(userName);
        List<List<String>> gameSaveInfo = new ArrayList<>();
        // loop through all of the games saves in the user file and find the right games
        for(int i=0; i<doc.getElementsByTagName(myDataResources.getString("UserFileGameTag")).getLength(); i++){
            Element gameElement = (Element) doc.getElementsByTagName(myDataResources.getString("UserFileGameTag")).item(i);
            try {
                checkKeyExists(gameElement, myDataResources.getString("UserFileGameNameTag"), "");
            }catch (OogaDataException e){
                // meant to be empty
                // skip games that don't have names
                continue;
            }
            String foundGameName = gameElement.getElementsByTagName(myDataResources.getString("UserFileGameNameTag")).item(0).getTextContent();
            if(foundGameName.equals(gameName)){
                // add the Level ID and date to the list
                checkKeyExists(gameElement, myDataResources.getString("UserFileSaveDateTag"), myDataResources.getString("UserFileSaveMissingDates"));
                checkKeyExists(gameElement, myDataResources.getString("UserFileSaveFilePathTag"), myDataResources.getString("UserFileSaveMissingFilePaths"));

                String date =  gameElement.getElementsByTagName(myDataResources.getString("UserFileSaveDateTag")).item(0).getTextContent();

                // get the level ID from the level file
                String loadFilePath =  gameElement.getElementsByTagName(myDataResources.getString(myDataResources.getString("UserFileSaveFilePathTag"))).item(0).getTextContent();
                File levelFile = new File(loadFilePath);
                Document levelDoc = getDocument(levelFile, myDataResources.getString("DocumentParseException"));

                checkKeyExists(levelDoc, myDataResources.getString("SaveFileLevelTag"), myDataResources.getString("SaveFileMissingLevel"));
                Element savedLevelElement = (Element) levelDoc.getElementsByTagName(myDataResources.getString("SaveFileLevelTag")).item(0);

                checkKeyExists(savedLevelElement, myDataResources.getString("LevelIDTag"), myDataResources.getString("SaveFileLevelMissingID"));
                String id = savedLevelElement.getElementsByTagName(myDataResources.getString("LevelIDTag")).item(0).getTextContent();

                ArrayList<String> entry = new ArrayList<>(List.of(id, date));
                gameSaveInfo.add(entry);
            }
        }
        return gameSaveInfo;
    }

    /**
     * Looks through the user files and finds the path of the save file by the requested user at the requested date
     * @param UserName: Name of the user who made the save file
     * @param Date: The date this save file was made
     * @return The path to the requested save file
     * @throws OogaDataException If the user file can't be parsed, has no saves, has no usernames, or
     * if the requested username isn't listed in the file or doesn't have a save at the given time
     */
    private String getLevelFilePath(String UserName, String Date) throws OogaDataException {
        Document userDoc = getDocForUserName(UserName);// find where the save file is stored
        checkKeyExists(userDoc, myDataResources.getString("UserFileSaveDateTag"), myDataResources.getString("UserFileHasNoSaves"));
        for(int i=0; i<userDoc.getElementsByTagName(myDataResources.getString("UserFileSaveDateTag")).getLength(); i++){
            String loadedDate = userDoc.getElementsByTagName(myDataResources.getString("UserFileSaveDateTag")).item(i).getTextContent();
            if(!loadedDate.equals(Date)) continue;
            return userDoc.getElementsByTagName(myDataResources.getString("UserFileSaveFilePathTag")).item(i).getTextContent();
        }
        throw new OogaDataException("User has no save at the given date");
    }

    /**
     * @param UserName the name of the user whose document we need
     * @return The Document for the user with the given username
     * @throws OogaDataException if the document has no user with that username
     */
    private Document getDocForUserName(String UserName) throws OogaDataException {
        for (File userFile : getAllXMLFiles(DEFAULT_USERS_FILE)){
            try {
                // create a new document to parse
                File fXmlFile = new File(String.valueOf(userFile));
                Document doc = getDocument(fXmlFile, myDataResources.getString("DocumentParseException"));
                // get the name at the top of the file
                checkKeyExists(doc, "Name", "User file missing username");
                String loadedName = doc.getElementsByTagName("Name").item(0).getTextContent();

                if (loadedName.equals(UserName)) return doc;
            }catch (OogaDataException e){
                // this field is meant to be empty
                // right now we're just looking for a user,
                // it's not a problem if one of the other documents is improperly formatted
            }
        }
        throw new OogaDataException(myDataResources.getString("UserFolderMissingRequestedUsername"));
    }

    /**
     * @param loadFilePath the path to a saved level
     * @return the loaded level based on the save file
     */
    private Level loadLevelAtPath(String loadFilePath) throws OogaDataException {
        //get the name of the game
        File levelFile = new File(loadFilePath);
        Document doc = getDocument(levelFile, myDataResources.getString("DocumentParseException"));
        String gameName = doc.getElementsByTagName(myDataResources.getString("GameNameTag")).item(0).getTextContent();
        // get the image entity map of the main game
        Map<String, ImageEntityDefinition> imageEntityMap = getImageEntityMap(gameName);
        // get pointed to the level element
        checkKeyExists(doc, myDataResources.getString("SaveFileLevelTag"), myDataResources.getString("SaveFileMissingLevel"));
        Element savedLevelElement = (Element) doc.getElementsByTagName(myDataResources.getString("SaveFileLevelTag")).item(0);
        // get the initial Entities
        ArrayList<Entity> entities = getInitialEntities(imageEntityMap, savedLevelElement);
        // get the level's ID
        checkKeyExists(savedLevelElement, myDataResources.getString("LevelIDTag"), myDataResources.getString("SaveFileLevelMissingID"));
        String id = savedLevelElement.getElementsByTagName(myDataResources.getString("LevelIDTag")).item(0).getTextContent();
        // create the level from the gathered information
        return new OogaLevel(entities, id);
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
        NodeList imageEntityNodes = level.getElementsByTagName(myDataResources.getString("ImageEntityInstanceTag"));
        // for each image entity, save a copy of the specified instance at the specified place
        for (int j = 0; j < imageEntityNodes.getLength(); j++) {
            Node currentEntity = imageEntityNodes.item(j);
            Element entityElement = (Element) currentEntity;
            // check for a name
            checkKeyExists(entityElement, myDataResources.getString("EntityNameTag"), String.format(myDataResources.getString("EntityNameException"), levelID));
            String entityName = entityElement.getElementsByTagName(myDataResources.getString("EntityNameTag")).item(0).getTextContent();
            // check if name is valid
            if (!entityMap.containsKey(entityName))
                throw new OogaDataException(myDataResources.getString("UnknownEntityException") + entityName);
            // create copy of that entity at that position
            String[] parameterNames = new String[]{myDataResources.getString("XPosTag"), myDataResources.getString("YPosTag")};
            List<Double> parameterValues = constructEntity(entityElement, entityName, parameterNames);
            // account for tiling the same entity at many locations
            int[] rowsColsAndGaps = getRowsColsAndGaps(entityElement);
            ImageEntityDefinition imageEntityDefinition = entityMap.get(entityName);
            double xPos;
            double yPos = parameterValues.get(1);
            for (int row = 0; row < rowsColsAndGaps[0]; row++) {
                xPos = parameterValues.get(0);
                for (int col = 0; col < rowsColsAndGaps[1]; col++) {
                    Entity entity = imageEntityDefinition.makeInstanceAt(xPos, yPos);
                    entity.setPropertyVariableDependencies(getEntityVariableDependencies(entityElement));
                    entity.setVariables(getEntityVariables(entityElement));
                    entity.makeNonStationaryProperty(isStationary(entityElement, imageEntityDefinition.getStationary()));
                    initialEntities.add(entity);
                    xPos += imageEntityDefinition.getMyWidth() + rowsColsAndGaps[2];
                }
                yPos += imageEntityDefinition.getMyHeight() + rowsColsAndGaps[3];
            }
        }

        // add each specified text entity
        NodeList textEntityNodes = level.getElementsByTagName("TextEntityInstance");
        for (int j = 0; j < textEntityNodes.getLength(); j++) {
            Node currentEntity = textEntityNodes.item(j);
            Element entityElement = (Element) currentEntity;
            checkKeyExists(entityElement, myDataResources.getString("TextTag"), "Text entity instance did not specify text");
            checkKeyExists(entityElement, myDataResources.getString("FontTag"), "Text entity instance did not specify font");
            String text = entityElement.getElementsByTagName(myDataResources.getString("TextTag")).item(0).getTextContent();
            String font = entityElement.getElementsByTagName(myDataResources.getString("FontTag")).item(0).getTextContent();
            String[] parameterNames = new String[]{myDataResources.getString("XPosTag"),
                    myDataResources.getString("YPosTag"), myDataResources.getString("WidthTag"),
                    myDataResources.getString("HeightTag")};
            List<Double> parameterValues = constructEntity(entityElement, text, parameterNames);
            int index = 0;
            Entity entity = new TextEntity(text, font, parameterValues.get(index++), parameterValues.get(index++),
                    parameterValues.get(index++), parameterValues.get(index));
            entity.setPropertyVariableDependencies(getEntityVariableDependencies(entityElement));
            entity.setVariables(getEntityVariables(entityElement));
            //TODO:Verify this is correct vv
            entity.makeNonStationaryProperty(isStationary(entityElement,false));
            initialEntities.add(entity);
        }
        return initialEntities;
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
        NodeList nameNodes = entityElement.getElementsByTagName(myDataResources.getString("VariableNamesTag"));
        NodeList valueNodes = entityElement.getElementsByTagName(myDataResources.getString("VariableValuesTag"));
        if(valueNodes.getLength() > 0 && nameNodes.getLength() > 0) {
            String[] variableNames = nameNodes.item(0).getTextContent().split(" ");
            String[] variableValues = valueNodes.item(0).getTextContent().split(" ");
            if(variableNames.length != variableValues.length){
                throw new OogaDataException(myDataResources.getString("EntityVariablesLengthException"));
            }
            for(int i=0; i<variableNames.length; i++){
                variableMap.put(variableNames[i], variableValues[i]);
            }
        }
        else if(valueNodes.getLength() > 0 || nameNodes.getLength() > 0){
            throw new OogaDataException(myDataResources.getString("EntityVariablesOneMissingException"));
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
        String[] keys = new String[]{myDataResources.getString("RowsTag"),
                myDataResources.getString("ColumnsTag"), myDataResources.getString("XGapTag"),
                myDataResources.getString("YGapTag")};
        for(int i=0; i<rowsColsAndGap.length; i++) {
            NodeList nodes = entityElement.getElementsByTagName(keys[i]);
            if (nodes.getLength() > 0) {
                try {
                    rowsColsAndGap[i] = Integer.parseInt(nodes.item(0).getTextContent());
                } catch(NumberFormatException e){
                    throw new OogaDataException(myDataResources.getString("RowColException"));
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
                throw new OogaDataException(String.format(myDataResources.getString("EntityFormatException"), entityName));
            } catch (NullPointerException e) {
                throw new OogaDataException(String.format(myDataResources.getString("EntityMissingParamException"), entityName, parameterName));
            }
        }
        return parameterValues;
    }

    private Map<String, String> getEntityVariableDependencies(Element entityElement) throws OogaDataException {
        Map<String, String> dependencyMap = new HashMap<>();
        NodeList dependencyList = entityElement.getElementsByTagName(myDataResources.getString("PropertyVariableDependencyTag"));
        for(int i=0; i<dependencyList.getLength(); i++){
            Element dependencyElement = (Element)dependencyList.item(i);
            checkKeyExists(dependencyElement, myDataResources.getString("VariableNameTag2"), myDataResources.getString("PVDVariableMissingException"));
            checkKeyExists(dependencyElement, myDataResources.getString("PropertyNameTag"), myDataResources.getString("PVDPropertyMissingException"));
            String variableName = dependencyElement.getElementsByTagName(myDataResources.getString("VariableNameTag2")).item(0).getTextContent();
            String propertyName = dependencyElement.getElementsByTagName(myDataResources.getString("PropertyNameTag")).item(0).getTextContent();
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
            NodeList entityNodes = doc.getElementsByTagName(myDataResources.getString("ImageEntityTag"));
            // add all entities to the map
            for (int i = 0; i < entityNodes.getLength(); i++) {
                // create a new entity
                Node currentEntity = entityNodes.item(i);
                // if the entity has an image, it is an imageEntity
                Element entityElement = (Element) currentEntity;
                // add the ImageEntity to the map
                checkKeyExists(entityElement, myDataResources.getString("EntityNameTag"), myDataResources.getString("IEDMissingNameException"));
                String newName = entityElement.getElementsByTagName(myDataResources.getString("EntityNameTag")).item(0).getTextContent();
                ImageEntityDefinition newIED = createImageEntityDefinition(entityElement, gameFile.getParentFile().getName());
                newIED.setVariables(getEntityVariables(entityElement));
                retMap.put(newName, newIED);
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("");
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
        for(String key : new String[]{myDataResources.getString("EntityNameTag"),
                myDataResources.getString("HeightTag"), myDataResources.getString("WidthTag"),
                myDataResources.getString("ImageTag")}){
            checkKeyExists(entityElement, key, String.format(myDataResources.getString("EntityMissingDataException"), key));
        }
        String name = entityElement.getElementsByTagName(myDataResources.getString("EntityNameTag")).item(0).getTextContent();
        double height = Double.parseDouble(entityElement.getElementsByTagName(myDataResources.getString("HeightTag")).item(0).getTextContent());
        double width = Double.parseDouble(entityElement.getElementsByTagName(myDataResources.getString("WidthTag")).item(0).getTextContent());
        String imagePath = myDataResources.getString("PathPrefix") + myLibraryFilePath +
                myDataResources.getString("Slash") + gameDirectory + myDataResources.getString("Slash") +
                entityElement.getElementsByTagName(myDataResources.getString("ImageTag")).item(0).getTextContent();
        boolean stationary = isStationary(entityElement, false);

        List<ConditionalBehavior> behaviors = new ArrayList<>();
        NodeList nodeList = entityElement.getElementsByTagName(myDataResources.getString("BehaviorTag"));
        for (int i=0; i<nodeList.getLength(); i++){
            Element behaviorElement = (Element) nodeList.item(i);
            Map<List<String>, String> requiredCollisionConditions = new HashMap<>();
            Map<List<String>, String> bannedCollisionConditions = new HashMap<>();
            addCollisionConditions(requiredCollisionConditions, behaviorElement.getElementsByTagName(myDataResources.getString("RequiredCollisionConditionTag")), name);
            addCollisionConditions(bannedCollisionConditions, behaviorElement.getElementsByTagName(myDataResources.getString("BannedCollisionConditionTag")), name);
//            addOneParameterConditions(inputConditions, behaviorElement.getElementsByTagName(myDataResources.getString("InputConditionTag")),
//                    myDataResources.getString("KeyTag"), myDataResources.getString("InputRequirementTag"));
            Map<String, List<String>> inputConditions = getInputConditions(behaviorElement.getElementsByTagName(myDataResources.getString("InputConditionTag")),
                    myDataResources.getString("KeyTag"), myDataResources.getString("InputRequirementTag"));
            List<VariableCondition> gameVariableConditions = getGameVariableConditions(behaviorElement.getElementsByTagName(
                    myDataResources.getString("GameVariableConditionTag")));
            Map<String,List<VariableCondition>> entityVarConditions = getEntityVariableConditions(behaviorElement.getElementsByTagName(
                    myDataResources.getString("EntityVariableConditionTag")));
            behaviors.add(new BehaviorInstance(gameVariableConditions,entityVarConditions,inputConditions,
                    requiredCollisionConditions,bannedCollisionConditions,getActions(behaviorElement)));
        }

        ImageEntityDefinition imageEntityDefinition = new ImageEntityDefinition(name, height, width, imagePath, behaviors);
        imageEntityDefinition.setStationary(stationary);
        return imageEntityDefinition;
    }

    private Map<String, List<String>> getInputConditions(NodeList conditionNodes, String keyName, String valueName)
        throws OogaDataException {
        Map<String,List<String>> conditionMap = new HashMap<>();
        for(int j=0; j<conditionNodes.getLength(); j++){
            String name = ((Element)conditionNodes.item(j)).getElementsByTagName(keyName).item(0).getTextContent();
            Element requirementElement = (Element)(conditionNodes.item(j));
            conditionMap.putIfAbsent(name,new ArrayList<>());
            NodeList requirementList = requirementElement.getElementsByTagName(valueName);
            //TODO: REMOVE THIS HARD CODED STRING ASAP
            if (requirementList.getLength() == 0) {
                String requirement = ("KeyAny");
                conditionMap.get(name).add(requirement);
            }
            else {
                for (int k = 0; k < requirementList.getLength(); k ++) {
                    String requirement = requirementList.item(k).getTextContent();
                    try {
                        conditionMap.get(name).add(myDataResources.getString(requirement));
                    } catch (Exception e) {
                        throw new OogaDataException(String.format(myDataResources.getString("InvalidInputRequirementException"),requirement,name));
                    }
                }
            }
        }
        return conditionMap;
    }

    private boolean isStationary(Element entityElement, boolean defaultValue) {
        if(entityElement.getElementsByTagName(myDataResources.getString("StationaryTag")).getLength() > 0){
            return Boolean.parseBoolean(entityElement.getElementsByTagName(myDataResources.getString("StationaryTag")).item(0).getTextContent());
        }
        return defaultValue;
    }

    private List<VariableCondition> getGameVariableConditions(NodeList conditions)
        throws OogaDataException {
        List<VariableCondition> variableConditions = new ArrayList<>();
        for (int i = 0; i < conditions.getLength(); i ++) {
            Element variableConditionElement = (Element) conditions.item(i);
            String name = variableConditionElement.getElementsByTagName(myDataResources.getString("VariableNameTag2")).item(0).getTextContent();
            String requiredValue = variableConditionElement.getElementsByTagName(myDataResources.getString("RequiredValueTag")).item(0).getTextContent();
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
            if(collisionConditionElement.getElementsByTagName(myDataResources.getString("Entity1Tag")).getLength() == 0) entity1Info = entityName;
            else entity1Info = collisionConditionElement.getElementsByTagName(myDataResources.getString("Entity1Tag")).item(0).getTextContent();
            checkKeyExists(collisionConditionElement, myDataResources.getString("Entity2Tag"), myDataResources.getString("MissingEntity2Exception") + entityName);
            checkKeyExists(collisionConditionElement, myDataResources.getString("DirectionTag"), myDataResources.getString("MissingDirectionException") + entityName );
            String entity2Info = collisionConditionElement.getElementsByTagName(myDataResources.getString("Entity2Tag")).item(0).getTextContent();
            String direction = collisionConditionElement.getElementsByTagName(myDataResources.getString("DirectionTag")).item(0).getTextContent();
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
            Class<?> cls = forName(PATH_TO_CLASSES + effectClassName);
            Constructor<?> cons = cls.getConstructor(List.class, List.class);
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
            Class<?> cls = forName(PATH_TO_CLASSES + comparatorClassName);
            Constructor<?> cons = cls.getConstructor();
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
            Class<?> cls = forName(PATH_TO_CLASSES + effectClassName);
            Constructor<?> cons = cls.getConstructor(List.class);
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

    private Document getDocument(File fXmlFile, String errorMessage) throws OogaDataException {
        Document doc;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new OogaDataException(errorMessage);
        }
        return doc;
    }

    @Override
    public void addNewProfile(OogaProfile newProfile) {
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
