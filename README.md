# bowling-game
Simple android bowling game, using a clean architecture approach, defined in two modules:

-Domain:
This is actually the app, a pure java module with no external framework dependencies.
It contains business entities and common app use-cases.
Unit testing is done agains the use-cases.
-App:
This is framework/platform details related, in this case an Android implementation, and adapters layer to
interact with use cases in the domain layer (mostly Presenters)
