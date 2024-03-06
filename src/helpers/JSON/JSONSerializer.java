package helpers.JSON;

import java.util.List;
import java.util.Map;

public class JSONSerializer {
    private final Object jobj;
    private int jind;

    public JSONSerializer(Object jobj) {
        this.jobj = jobj;
    }

    private String strser(String x) {
        return "\"" + x + "\"";
    }

    private String numser(double x) {
        return Double.toString(x);
    }

    private String mapser(Map<String, Object> x) throws JSONSerializerException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Object> entry : x.entrySet()) {
            sb.append(strser(entry.getKey()));
            sb.append(":");
            sb.append(serialize(entry.getValue(), this.jind));
            sb.append(",");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");

        return sb.toString();
    }

    private String listser(List<Object> x) throws JSONSerializerException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object obj : x) {
            sb.append(serialize(obj, this.jind));
            sb.append(",");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String serialize(Object x, int format) throws JSONSerializerException {
        this.jind = format;
        if (x instanceof String) { // replace with switch?
            return strser((String) x);
        } else if (x instanceof Integer) {
            return numser((Integer) x);
        } else if (x instanceof Double) {
            return numser((Double) x);
        } else if (x instanceof Map) {
            for (Object key : ((Map) x).keySet()) {
                if (!(key instanceof String)) {
                    throw new JSONSerializerException("Non-string key in Map: " + key.toString());
                }
            }
            return mapser((Map<String, Object>) x);
        } else if (x instanceof List) {
            return listser((List<Object>) x);
        } else if (x instanceof Boolean) {
            return x.toString();
        } else if (x == null) {
            return "null";
        } else {
            throw new JSONSerializerException("Invalid Type: " + x.toString());
        }
    }

    public String serialize() throws JSONSerializerException {
        return this.serialize(this.jobj, 0);
    }

    public String serlializeIndented(int indent) throws JSONSerializerException {
        return this.serialize(this.jobj, indent);
    }

    // public static void main(String[] args) throws JSONParserException,
    // JSONSerializerException {
    // Object test = new JSONParser("""
    // [[{"hi": "hello", "nums": [95, 88, 92, null]}, {}], [],
    // [1, "test", {"bool": true, "decimal": 42.0}]]""").parse();

    // System.out.println(test);
    // System.out.println(new JSONParser(new
    // JSONSerializer(test).serialize()).parse());
    // System.out.println(new JSONSerializer(test).serialize());
    // }
}