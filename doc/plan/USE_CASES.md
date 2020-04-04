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