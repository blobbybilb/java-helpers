package helpers.HTTP;

public enum ContentType {
    JSON("application/json"),
    FORM("application/x-www-form-urlencoded"),
    RAW("text/plain");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
