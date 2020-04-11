# API Changes

## Data
- Added method ``getPauseButtonImage`` in DataReader to fetch an Image object representing
the pause button
- 4/7/20 Removed ``getPauseButtonImage`` in DataReader because view is going to do that on its own
- 4/7/20 Changed ``getGameFiles`` to ``getGameFilePaths`` in DataReader to make the purpose more explicit
- 4/7/20 Changed ``getThumbnails`` in DataReader to return a list of Thumbnails instead of Strings
- 4/7/20 Added ``getBasicGameInfo`` in DataReader which takes a game's name and give it basic info. More description in the API.
- DataReader getThumbnails no longer takes String arg
- 4/8/20 Changed ``loadGame`` to ``loadLevel`` in DataReader according to a change we made in a meeting to how 
DataReader loads and reports information. Changed its description and return type accordingly.
- 4/8/20 Changed ``loadLevel`` so that it takes a Game name and a level ID instead of a file path.
- 4/9/20 Added ``getEntityMap`` so that the Game can ask for the map of entity definitions needed to
create new Entity instances.

## Game API
- getEntities now returns an observable list instead of list
- added method ``makeUserInputListener`` to create a listener that will react to inputs
- note that a lot of the methods in UserInputListener are unnecessary, like ReactToGameSelect,
since the game will always know what game it is running and is not responsible for choosing game
- added method ``reactToPauseButton``
- UserInputListener method probably shouldn't take a String input, as the Game should be
responsible for determining where to save
- now has a doGameStep method that view calls during its step method


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
- Added ``getHeight`` and ``getWidth`` so that collision detection can do its job 
under the assumption of square collisions.
- Added ``Entity`` abstract class methods to ``EntityAPI``, so that we can hopefully unite the two.
The motivation was that View relies on Entities, but it asks the Game interface to get entities
as a list, and I don't want Game to rely on implementation details of EntityAPI (even though Entity
is abstract). The ultimate solution is to make a distinction between the 'front-facing' part of Entity
and the rest.
- Added ``getName`` so that collisions can use the name of the entity.
- Added ``getVelocity`` so that the Entity knows it 
- 4/10/2020: Removed ``moveByVelocity`` since it's better to assume that velocity is
applied internally each frame.
### Level
- Added ``removeEntity`` so that the game can remove destroyed entities.
The alternative would be internal 'garbage collection' inside level, but that would also require
at least one new method.

### ControlsBehavior
- Modified ``reactToControls()`` to take in the subject entity as a parameter.


### MovementBehavior
- Added ``setTarget`` method as a way to resolve an issue where an Entity needed to know 
movement behavior and a movement behavior needed to know what entity it was modifying.
- Removed ``setTarget`` 
- Added a second parameter "Entity subject"  in ``doMovementUpdate`` method as another solution to how the MovementBehavior knows which Entity it
is updating 

### CollisionBehavior
- 4/9/2020: Modified ``handleCollision`` to take an entity as a paramter, so that
it can have an effect on whatever entity calls it. Possibly could change to EntityInternal.
Also could change to take two entities, since two are involved in a collision.