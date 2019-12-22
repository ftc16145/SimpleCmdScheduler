This project started with the new FRC code for the CommandScheduler.
This was recently refactored to the project: wpilibNewCommands in the allwpilib repository.

To make this code compatible with FTC the package name was changed and Command Classes removed that are specific to the FRC control system.  This leaves the basic scheduling and Command base classes to start from for FTC specific commands.

Combining this with Road Runner should make very sophisticated movement commands possible with little coding effort.
