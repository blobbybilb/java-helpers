import helpers.JSON.JSONParser;
import helpers.JSON.JSONParserException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JSONParserException {
        String x = """
                [
                    [{"name": "Alice", "scores": [95, 88, 92]}, {"name": "Bob", "scores": [89, 91, 84]}],
                    [1, "hello", {"bool": true, "number": 42.0}],
                    [{"name": "Alice", "age": 30}, {"name": "Bob", "age": 35}],
                    {"people": [{"name": "Alice", "hobbies": ["reading", "hiking"]}, {"name": "Bob", "hobbies": ["swimming"]}]},
                    {"a": {"b": {"c": {"d": {"e": {"f": {"g": {"h": {"i": {"j": {"k": {"l": {"m": "n"}}}}}}}}}}}}},
                    [[1, 2], [3, 4], [5.0, [6, 7]]],
                    [{"name": "Bob", "data": [3, {"hobbies": ["hiking"]}]}],
                    {"a": [1, 2], "b": {"x": 10, "y": [3, 4]}, "c": [5, {"d": {"e": 6}}]},
                    [{"person": {"name": "Bob", "address": {"city": "Los Angeles", "zip": [90001]}}}],
                    {
                        "test1": "test1val",
                        "test1.5": "test1val2",
                        "test2": {
                            "test3": "test3val",
                            "test4": "test4val",
                            "test5": {
                                "test6": "test6val",
                                "test7": "test7val"
                            }}
                    },
                    [
                        "test1",
                        "test2",
                        "test3",
                        ["test", "sdfadf", ["test", "sdfadf"]],
                    ],
                    ["hi1", {}, [], {}, {"test1.5": ["hi", "hi"]}],
                    {"hi1": [{}, [], {}, {"test1.5": ["hi", "hi"]}]},
                    {"hi1": [{}, [], {}, {"test1.5": ["hi", "hi"]}]},
                    {"hi": ["a", []], "hi2": {"hi3": "hi4", "hi5": "hi6"}},
                    {"hi": [[]], "hi2": {}},
                    {"hi": {"sdf": {}}, "hi2": "hi"},
                    ["hi", [  ] ]  ,\s
                    "hi",
                ]""";
        JSONParser p = new JSONParser(x);
//        HashMap<String, Object> y = (HashMap<String, Object>) p.parse();
        System.out.println(((List<Object>) p.parse()).get(1));
    }
}