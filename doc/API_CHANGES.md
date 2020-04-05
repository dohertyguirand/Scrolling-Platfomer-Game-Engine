# API Changes

## Game API
### Entity
- Added ``move`` method so that entities can move in their movement behavior.
- Added ``getPosition`` method so that a JUnit test could check whether ``updateSelf`` was working for
behaviors that change the entity's position.

### MovementBehavior
- Added ``setTarget`` method as a way to resolve an issue where an Entity needed to know 
movement behavior and a movement behavior needed to know what entity it was modifying.