
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import helpers.JSON.JSONParser;
import helpers.JSON.JSONSerializer;

class JSONTest {
    static String testStr = "";

    public static void loadTestStrFromTestJSON() throws IOException {
        String filePath = "test.json";
        Path path = Path.of(filePath);
        testStr = Files.readString(path, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        loadTestStrFromTestJSON();

        Object data = new JSONParser(testStr).parse();
        String serialized = new JSONSerializer(data).serialize();
        Object parsed = new JSONParser(serialized).parse();
        String serialized2 = new JSONSerializer(parsed).serialize();
        System.out.println(data.equals(parsed));
        System.out.println(serialized.equals(serialized2));
    }
}
