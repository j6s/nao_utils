# NAO Utils by team bier.git

## What is this?

This is a collection of small utilities to make Java development for 
the NAO robots by aldeberan more pleasurable.

The Java API for these robots is not particularly good and heavily relies
on magic strings and undefined Parameters (`Object` is specified as parameter
or as return value way to often).

These Utility classes aim to make working with the robots just a little
more fun by wrapping some APIs.

## Who are we?

We are students from DHBW Lörrach, that are somewhat annoyed by the 
existing APIs for the NAO.

## How to use

Currently there is no real package management solution to use these helper
classes and there probably never will be. Just add this repository as a 
submodule in `src/de/dhbw/wwi13b/shared`


## Utilities

- [SpeechRecognitionRouter](router/SpeechRecognitionRouter.java)
- [GrabUtils](util/GrabUtils.java)
- [JointUtil](util/JointUtil.java)
- [LandMarkTrackerUtil](util/LandMarkTrackerUtil.java)
- [PostureUtil](util/PostureUtil.java)
- [WalkUtil](util/WalkUtil.java)
- [RecognitionUtil](util/RecognitionUtil.java)
- [SonarUtil](util/SonarUtil.java)
- [SpeechUtil](util/SpeechUtil.java)

## Will this be maintained?

No.

This project was created in order to have a shared repository between
multiple groups in a group project in university. 
After the project is over this repository will be unmaintained

## License 

All code in this repository is released under [WTFPL v2](http://www.wtfpl.net/)