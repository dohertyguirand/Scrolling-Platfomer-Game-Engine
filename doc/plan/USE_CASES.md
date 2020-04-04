# Use Cases

### Names & NetIDs
Sam Thompson (stt13)
Chris Warren (ccw43)
Braeden Ward (bmw54)
Cary Shindell (css57)
Doherty Guirand (dg211)

### stt13 Use Cases

1. The user starts the program, and a list of game thumbnails appears onscreen.

    When the program starts, the Engine instantiates a DataReader, and it tells the DataReader what folder to look inside for thumbnails using ``getThumbnails``. The DataReader then returns a list of images, which are passed to the view, which uses internal methods to draw the images onscreen.
    
2. The program attempts to load a game, but the game doesn't have any levels defined.

    The main Engine calls ``loadGame`` on its owned DataReader using a specified filepath. The DataReader examines the game file and throws a ``DataReaderException`` describing that there are no defined levels. The Engine handles the exception by telling its View to display the error message text from the DataReader, and no game is loaded.
    
3. The user pauses a currently active game by pressing Escape.

    The View detects the input and notifies its list of ViewListeners that the Pause key has been pressed. The Engine uses ``TellEntities`` to tell the view to display its pause menu options. It also pauses its timeline, which causes it to stop executing ``doUpdateLoop`` and ``doCollisionLoop``.
    
4. In Super Mario Bros, small Mario touches a mushroom, which causes him to become big Mario.

    During the ``doCollisionLoop`` method, the Game identifies that the two entities are colliding, and it notifies them and gives them each the name of the other entity. Each responds, based on its list of mappings from entity names to behaviors. In response to touching a Mario entity, the mushroom is removed from the game. In response to touching a Mushroom, the Mario is removed, and a BigMario entity appears in its place.
    
5. In the pause menu, the user clicks the Exit button to quit without saving.

    The UI part of the View notifies all of its UserInputListeners that QuitButton has been pressed, activating the ``handleQuit()`` method on the Engine (which extends UserInputListener). The engine sets its current game to none, causing it to fall back to its main menu state (which is itself an object made up of entities, with its own properties, but is not a Game). The Engine binds information to the View about the main menu rather than about a game.
    
6. The user uses a 'Load Game' button in the main menu, and selects a valid game file for Doodle Jump from a USB drive.

    The user input component of the front end handles it the same way as when a thumbnail is clicked. The EventHandler for the file loading button is the same as for thumbnails, so the View notifies its UserInputListeners by calling its ``handleGameLoaded`` method with the filepath of the game. Engine tells its DataReader to ``loadGame``, sets its currently active game to the return value, and begins its Timeline.
    
7. While a game is paused, the user selects the 'Save Game' button, and the game state is saved to a file.

    A method in ``userInputListener`` implemented by Engine handles the event (as detected by the front end) by handing its currently active Game to its DataReader and calling ``saveGame``. It uses a filepath that is stored in a resource/properties file. The DataReader returns nothing, which means that the game state was valid and correctly saved, since no exception was thrown.
    
8. In Mario, Small Mario runs into a koopa, causing the number of lives to decrease, Mario to respawn at the start of the level, and the Camera to go to where Mario is.

    The Game's ``doCollisionLoop`` method identifies the collision and its type and tells the SmallMario and Koopa entities about each others' names and calls ``handleCollision``. The Koopa has no reaction, and small Mario reacts by changing position to the level's start. (TO BE RESOLVED: HOW DOES LEVEL-ORIENTED DATA GET MODIFIED BY ENTITY-ORIENTED EVENTS?)
    
### dg211 Use Cases

1. In Mario, a goomba moves forward.

    The Game's, ``doUpdateLoop`` method will update each entity by using its ``updateSelf`` method. 

2. The user clicks a thumbnail, and a game is launched.

    Each thumbnail is tied to a specific game in the DataReader. The selected thumbnail is sent to the DataReader and its ``loadGame`` method is called with the proper filename that contains the contents of the game.

3. The user pauses the game, then unpauses.
The View receives the input and notifies the ViewListeners that the Pause key has been pressed. The Engine uses ``TellEntities`` to tell the view to display its pause menu options. It also pauses its timeline, which causes it to stop executing ``doUpdateLoop`` and ``doCollisionLoop``. When the user unpauses, the View receives the input and notifies the ViewListeners that the Resume key has been pressed. The Engine uses``TellEntities`` to remove the pause menu and reveals the most recent updates. The timeline is played, and the ``doUpdateLoop`` and ``doCollisionLoop`` methods are told to start again. 
4. The user presses the 'up' key to jump.
The View receives the input and notifies the ViewListeners that the 'up' key has been pressed. Inside the Engine's ``doUpdateLoop`` each Entity will call ``reactToControls`` and ``updateSelf``.  The entities that are set to react to the 'up' key will react. For example, only Mario will jump and not the goombas. 
5. The user tries to run off the screen. For example, Mario uses two walls to jump out of the top of the screen. 
During each Entity's ``updateSelf`` method, the Entity will see that it has made it off of the screen and will force the Entity to return. So in this example, Mario will fall.
6. Both Watergirl and Fireboy touch fire. 
This is an example of two entities having different reactions to the same Entity. In the Engine's ``doCollisionLoop`` the Game will see that two entities are colliding, twice. For each collision, (Fireboy/fire and Watergirl/fire) it will notify both Entities and gives them each the name of the other Entity. Therefore, Fireboy will know that it's colliding with fire and fire will know it's colliding with Fireboy. The same will be true for the Watergirl/fire collision. Each Entity will then respond based on its prescribes reaction to its colliding Entity. In this case, the fire will not react to colliding with Fireboy nor Watergirl. Fireboy will not react to colliding with fire. Watergirl will react to colliding with fire because inside the Watergirl Entity there is a negative behavior mapped to fire. 
7. The leading character (for example Mario) takes two of the same powerup at once. 
Each Entity will have mappings that prescribe certain reactions to collisions with other Entities. When the character takes the first powerup. The old Entity will be removed and the powerup version of that Entity will take its place. The rules of each game will decide a second powerup will affect gameplay or not by changing the mapping that prescribes the reactions to collisions. For example, say Mario colliding with two flowers at once allows for him to be hit by an enemy twice before losing a life. Then when Mario collides with the first flower, the regular Mario Entity will be replaced by the Big Mario Entity. The Big Mario will have a prescribed reaction to mushrooms that will increase the number of times it can be hit by an enemy before it turns to regular Mario again. Therefore, when Big Mario collides with the second flower, Big Mario's enemy tolerance will have increased.  
8. A two-player game is selected.
The View will contain ViewListeners that will react to user input. The ViewListeners will include KeyListeners so that the View knows when keys such as 'space' or 'right' have been pressed. This means that each of the keys could be connected to a certain interaction in the game. Each Entity will contain a mapping that enumerates the keys to which it is supposed to react and what reaction is required. Therefore, one player can use the 'up', 'right', 'left', 'down' keys while the second player can use keys such as 'w',' a', 's', and 'd'. The keys that each player will react to will be enumerated in the data file that contains the contents of the game.