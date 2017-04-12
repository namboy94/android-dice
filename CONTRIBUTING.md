# Contributing to android-dice

## Copyright and Management

The original author, Hermann Krumrey, has the absolute authority on the management
of this project and may steer the development process as he sees fit.

Contributions will be attributed to the author of said code and the copyright will
remain the author's.

## Coding guidelines

**Style**

We feel that a unified coding style is important, which is why we require a linter to
be used. In this case **KtLint** is used. Code must pass KtLint's tests.

Additionally, the following coding conventions should be upkept:

* Indentation is done using 4 spaces.
* Indentation is important. Always indent as if you were writing python code.
* Line lengths are limited to 120 characters.
* Brackets are placed like this:

      if (condition) {
          ...
      }

**Documentation**

All classes, methods and class/instance variables should be described using KDoc comments.

Hard to understand parts of code within a method should always be commented
accordingly.

## Contributing

All active development is done on a [self-hosted Gitlab server](https://gitlab.namibsun.net).
To contribute, send an email to hermann@krumreyh.com to create an account. Once you have an
account, you may issue a merge or pull request.

Using the Gitlab issue tracker is preferred, but the issues on Github are also
taken into consideration.