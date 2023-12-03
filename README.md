# blobbybilb/java-helpers

**Goal:** Fix Java

(jk, that's probably impossible)

**Realistic Goal:** Make java a little less annoying to make stuff with.

### Actually though, what is this?

A set of Java classes that help with commonly used functionality,
so you don't have to reimplement it everytime (or use Maven/Gradle in a small project).
You can use this as a single jar file that you just copy into your project directory,
because it's intended for projects where the extra complexity/annoyingness of
java build systems/package management is not worth it.

### What does it have?

- A custom JSON parser [(more info)](https://github.com/blobbybilb/java-helpers/tree/master/src/Helpers/JSON#readme)
- And a JSON serializer too
- HTTP client (single line HTTP POST/GET)
- HTTP server (a little WIP but should work)
- A key-value store