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

### Docs

#### JSON

```java
// Parse JSON
Object data = new JSONParser(jsonString).parse();
// Serialize JSON
String jsonString = new JSONSerializer(data).serialize();

// JSON-able Types: Map (JSON object), List (JSON array), String, Number, Boolean, null
```

#### KV Store

```java
// Create a KV store, SomeType is the type of the value which can be a JSON-able type (see above)
Object KVStore<SomeType> kv = new KVStore<>(dataDirectoryString);
// Set a value
kv.set("key", value); // value of type SomeType
// Get a value
SomeType value = kv.get("key"); // returns value of type SomeType, throws KVStoreException if key doesn't exist or data is corrupted/invalid
// Delete a value
kv.del("key");
```

#### HTTP Client

```java
// HTTP GET
String response = Request.get("http://example.com");
// HTTP POST
String response = Request.post("http://example.com", Map<String, String>); // Form data body
String response = Request.post("http://example.com", String, Helpers.HTTP.ContentType); // Other data body types
```

#### HTTP Server

```java
// WIP
```
