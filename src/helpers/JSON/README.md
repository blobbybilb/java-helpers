## JSON Parser

A simple JSON parser, written over lunch in python, then (after a bit of refactoring and making edge cases work)
ported to Java.

This is the first time I've written a parser, and I haven't really looked at how parsers are written.
It doesn't have a separate lexer/tokenizer/etc., it just parses the thing. It also doesn't do validation.

I haven't tried to make it fast/efficient, but I also haven't done obviously slow things. For example, I've used recursion for nested collections, but not for places where it needs to go through every character in a string.

Currently, it's one class, 144 LoC Java and 117 LoC Python (following PEP8), including comments and without trying to make it short.

Also, it handles trailing commas. Or at least it should. Of course, there are no guaratees it will always work,
but I've tested it on some examples with weird whitespace/commas/nested collection combinations, and it seems to work.

#### Overview of how it works

I'll say again, I haven't written a parser or interpreter or anything like that before, so this is probably weird and not great.

- it starts with a string (`jstr`)
- it runs the parser function
  - it decides what data type it is (string, number, boolean, null, array, object)
  - for non-collection types it returns the value and deletes that from jstr
  - for collections, it loops through, recursing on each element/value, and returns it, and deletes that from jstr
- throughout the process, it keeps deleting from jstr, removing whitespace and parsed characters/values, and each method called just operates on the start of the remaining string
