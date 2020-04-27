## Description

Summary of the feature's bug (without describing implementation details)

When adding a new profile, it's possible to cause a strange error by
creating a profile with teh same name as an existing profile. This error
shows up un a very non-user friendly way with an error message printed in the 
command line.

## Expected Behavior

Describe the behavior you expect

When adding a new profile, we want a user to be able to easily add a new 
profile and be warned if their parameters are invalid with a message displaying
what was wrong.

## Current Behavior

Describe the actual behavior

Currently, if the user tries to create a user with the same name as an
existing name, the XMLProfileDataReader tries to create a new directory 
with that name but it can't because a directory already exists with that 
name.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

Start Visualizer and click on the button on the right to add a new profile.
Set the name to an existing profile name and press enter and then press submit.
 
## Failure Logs

Include any relevant print statements or stack traces.

"Sorry could not create specified directory"

## Hypothesis for Fixing the Bug

Describe the test you think will verify this bug and the code fix you believe will solve this issue.

