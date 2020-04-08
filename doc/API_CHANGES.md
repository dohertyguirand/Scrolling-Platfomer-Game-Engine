# API Changes

## Game API
=======
## Data
- Added method ``getPauseButtonImage`` in DataReader to fetch an Image object representing
the pause button

## Game API
- getEntities now returns an observable list instead of list
- added method ``makeUserInputListener`` to create a listener that will react to inputs
- note that a lot of the methods in UserInputListener are unnecessary, like ReactToGameSelect,
since the game will always know what game it is running and is not responsible for choosing game
- added method ``reactToPauseButton``
- UserInputListener method probably shouldn't take a String input, as the Game should be
responsible for determining where to save


### Entity
- Added ``move`` method so that entities can move in their movement behavior.
- Added ``getPosition`` method so that a JUnit test could check whether ``updateSelf`` was working for
behaviors that change the entity's position.
- Added ``setCollisionBehaviors`` method so that an entity can know its behavior when colliding
with another entity, and have that behavior swapped out at runtime.
- Added ``destroySelf`` so that behaviors can cause entities to be removed from the level.
- Added ``setPosition`` so that behaviors with access to the entity can teleport it around the level.
- Added several properties including activeInView which determines whether the entity will be
displayed


### MovementBehavior
- Added ``setTarget`` method as a way to resolve an issue where an Entity needed to know 
movement behavior and a movement behavior needed to know what entity it was modifying.
- Removed ``setTarget`` 
- Added a second parameter "Entity subject"  in ``doMovementUpdate`` method as another solution to how the MovementBehavior knows which Entity it
is updating 