# API Changes

## Game API
### Entity
- Added ``move`` method so that entities can move in their movement behavior.
- Added ``getPosition`` method so that a JUnit test could check whether ``updateSelf`` was working for
behaviors that change the entity's position.
- Added ``setCollisionBehaviors`` method so that an entity can know its behavior when colliding
with another entity, and have that behavior swapped out at runtime.
- Added ``destroySelf`` so that behaviors can cause entities to be removed from the level.
- Added ``setPosition`` so that behaviors with access to the entity can teleport it around the level.
### MovementBehavior
- Added ``setTarget`` method as a way to resolve an issue where an Entity needed to know 
movement behavior and a movement behavior needed to know what entity it was modifying.