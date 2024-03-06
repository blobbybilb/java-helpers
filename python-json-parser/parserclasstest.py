from parserclass import JSONParser

x = [
    """
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
    }
    """,
    """
        [
            "test1",
            "test2",
            "test3",
            ["test", "sdfadf", ["test", "sdfadf"]],
        ]
    """,

"""["hi1", {}, [], {}, {"test1.5": ["hi", "hi"]   }]""",
"""{"hi1":[ {}, [], {}, {"test1.5": ["hi", "hi"]}]}""",
"""{"hi1":[ {}, [], {}, {"test1.5": ["hi", "hi"]}]}""",
# """ { "hi1"   :    [   {}, [] , {  },   {"test1.5" :[  "hi"  ,    "hi"   ] }   ] }"""
"""{"hi": ["a", []], "hi2": {"hi3": "hi4", "hi5": "hi6"}}"""
]

y = [
    {'test1': 'test1val', 'test1.5': 'test1val2', 'test2': {'test3': 'test3val', 'test4': 'test4val', 'test5': {'test6': 'test6val', 'test7': 'test7val'}}},
    ['test1', 'test2', 'test3', ['test', 'sdfadf', ['test', 'sdfadf']]],
    ['hi1', {}, [], {}, {'test1.5': ['hi', 'hi']}],
    {'hi1': [{}, [], {}, {'test1.5': ['hi', 'hi']}]}]

for json_str in x:
    print(JSONParser(json_str).parse() in y)

print(JSONParser(x[-1]).parse())
