
import java.util.HashMap;
import java.util.Map;

import helpers.Data.KVStore;
import helpers.Data.KVStoreException;

class KVStoreTest {
    public static void main(String[] args) {
        KVStoreTest kvStoreTest = new KVStoreTest("datadir");
        kvStoreTest.testSetAndGet();
        kvStoreTest.kv.del("testKey");
    }

    private final KVStore<Map<String, Integer>> kv;

    public KVStoreTest(String dataDir) {
        this.kv = new KVStore<>(dataDir);
    }

    public void testSetAndGet() {
        String key = "testKey";
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("value1", 42);
        dataMap.put("value2", 73);

        try {
            kv.set(key, dataMap);
            Map<String, Integer> retrievedMap = kv.get(key);
            assert dataMap.equals(retrievedMap) : "Test failed: set and get operations do not match.";
            System.out.println("Test passed: set and get operations are successful.");
        } catch (KVStoreException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }
}
