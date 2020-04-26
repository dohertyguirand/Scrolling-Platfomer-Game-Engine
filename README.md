final
====

This project implements a player for multiple related games.

Names: Cary Shindell, Sam Thompson, Doherty Guirand, Chris Warren, Braeden Ward


### Timeline

Start Date: 

Finish Date: 4/24/2020

Hours Spent: 

css57 - 85

### Primary Roles

css57 - setting up basic view/visualizer functionality (communication w back end and
updating display), updating and refactoring data reader, conditional behaviors/effects
and collision in back end, fireboy and watergirl game.

### Resources Used
To evaluate string expressions like "5+5" we used an expression parser we got from
stack overflow. The link is given in ExpressionEvaluator interface.

### Running the Program

Main class: Visualizer. This is the class that extends Application, and communicates with
both data and game.


Data files needed: 

Features implemented:

Dark Mode

Profiles

High Scores

Multiple Games at Once

Saving and Loading levels

...

### Files Used to Test Project and Expected Error Handling

files used to test the project: 

The project should be able to read any xml data files without showing red (assuming
files have the minimum of correctly closed tags). If the actual contents of the data
file that we parse is badly formatted or something is missing, an error message will
pop up in the UI. 

If an error occurs while the game is running, we do not expect the game to crash.
The program will display an error pop up window, and when that is closed it will
attempt to continue to run the program (things could start to get messy in back end
depending on the error). The same error will probably be thrown again, and if it is,
the error handler will check the type of error that was thrown and only display another
error pop up if the class of the error is different from the previously thrown error.
This is to avoid overloading the UI with error pop ups.

### Notes/Assumptions

Assumptions or Simplifications:

We assumed that for dark mode we just needed to change the overall colors, rather than
having a distinct image for each game entity.

We assumed that data files would be in xml format but set up the data reader apis to make
it easy to add data readers for other file types.

Interesting data files:

Known Bugs:

Extra credit: You can choose which game you want to play and get a preview of each from
the selection screen.


### Impressions

Some of the extension features were interesting but it seems like it might be better to
have game-specific extensions because we thought it was much better to improve our design
and make our back end more flexible. For example, rather than adding a social center,
which is cool but doesn't add much to design, we decided to implement a camera because
it's a key feature of platformers. So it might make sense to specify some extensions
based on the type of game?
