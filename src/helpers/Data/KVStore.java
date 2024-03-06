package helpers.Data;

import helpers.JSON.JSONParser;
import helpers.JSON.JSONParserException;
import helpers.JSON.JSONSerializer;
import helpers.JSON.JSONSerializerException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KVStore<ValueType> {
    public Path dataDir;

    public KVStore(String dataDir) {
        this.dataDir = Paths.get(dataDir);
        try {
            Files.createDirectories(this.dataDir);
        } catch (IOException e) {
            throw new KVStoreException("Error creating data directory");
        }
    }

    static public String removeStartingSlash(String x) {
        if (x != null && x.startsWith("/")) {
            x = x.substring(1);
        }
        return x;
    }

    public void set(String key, ValueType value) throws KVStoreException {
        try {
            Files.write(
                    this.dataDir.resolve(removeStartingSlash(key)),
                    new JSONSerializer(value).serialize().getBytes()
            );
        } catch (IOException e) {
            throw new KVStoreException("Error setting value for key " + key);
        } catch (JSONSerializerException e) {
            throw new KVStoreException("Error serializing value for key " + key);
        }
    }

    @SuppressWarnings("unchecked")
    public ValueType get(String key) throws KVStoreException {
        try {
            return (ValueType) new JSONParser(Files.readString(this.dataDir.resolve(removeStartingSlash(key)))).parse();
        } catch (IOException e) {
            throw new KVStoreException("Error getting value for key " + key);
        } catch (JSONParserException e) {
            throw new KVStoreException("Error parsing value for key " + key);
        } catch (ClassCastException e) {
            throw new KVStoreException("Invalid value type for key " + key);
        }
    }


    public void del(String key) throws KVStoreException {
        try {
            Files.deleteIfExists(this.dataDir.resolve(removeStartingSlash(key)));
        } catch (IOException e) {
            throw new KVStoreException("Error deleting value for key " + key);
        }
    }
}


