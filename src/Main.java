import Helpers.JSON.JSONParser;
import Helpers.JSON.JSONParserException;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws JSONParserException {
        String x = "[\n" +
                "    [{\"name\": \"Alice\", \"scores\": [95, 88, 92]}, {\"name\": \"Bob\", \"scores\": [89, 91, 84]}],\n" +
                "    [1, \"hello\", {\"bool\": true, \"number\": 42.0}],\n" +
                "    [{\"name\": \"Alice\", \"age\": 30}, {\"name\": \"Bob\", \"age\": 35}],\n" +
                "    {\"people\": [{\"name\": \"Alice\", \"hobbies\": [\"reading\", \"hiking\"]}, {\"name\": \"Bob\", \"hobbies\": [\"swimming\"]}]},\n" +
                "    {\"a\": {\"b\": {\"c\": {\"d\": {\"e\": {\"f\": {\"g\": {\"h\": {\"i\": {\"j\": {\"k\": {\"l\": {\"m\": \"n\"}}}}}}}}}}}}},\n" +
                "    [[1, 2], [3, 4], [5.0, [6, 7]]],\n" +
                "    [{\"name\": \"Bob\", \"data\": [3, {\"hobbies\": [\"hiking\"]}]}],\n" +
                "    {\"a\": [1, 2], \"b\": {\"x\": 10, \"y\": [3, 4]}, \"c\": [5, {\"d\": {\"e\": 6}}]},\n" +
                "    [{\"person\": {\"name\": \"Bob\", \"address\": {\"city\": \"Los Angeles\", \"zip\": [90001]}}}],\n" +
                "    {\n" +
                "        \"test1\": \"test1val\",\n" +
                "        \"test1.5\": \"test1val2\",\n" +
                "        \"test2\": {\n" +
                "            \"test3\": \"test3val\",\n" +
                "            \"test4\": \"test4val\",\n" +
                "            \"test5\": {\n" +
                "                \"test6\": \"test6val\",\n" +
                "                \"test7\": \"test7val\"\n" +
                "            }}\n" +
                "    },\n" +
                "    [\n" +
                "        \"test1\",\n" +
                "        \"test2\",\n" +
                "        \"test3\",\n" +
                "        [\"test\", \"sdfadf\", [\"test\", \"sdfadf\"]],\n" +
                "    ],\n" +
                "    [\"hi1\", {}, [], {}, {\"test1.5\": [\"hi\", \"hi\"]}],\n" +
                "    {\"hi1\": [{}, [], {}, {\"test1.5\": [\"hi\", \"hi\"]}]},\n" +
                "    {\"hi1\": [{}, [], {}, {\"test1.5\": [\"hi\", \"hi\"]}]},\n" +
                "    {\"hi\": [\"a\", []], \"hi2\": {\"hi3\": \"hi4\", \"hi5\": \"hi6\"}},\n" +
                "    {\"hi\": [[]], \"hi2\": {}},\n" +
                "    {\"hi\": {\"sdf\": {}}, \"hi2\": \"hi\"},\n" +
                "    [\"hi\", [  ] ]  , \n" +
                "    \"hi\",\n" +
                "]";
        JSONParser p = new JSONParser(x);
//        HashMap<String, Object> y = (HashMap<String, Object>) p.parse();
        System.out.println(p.parse());
    }
}