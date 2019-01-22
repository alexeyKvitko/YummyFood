package ru.yummy.eat.model.enums;

public enum TestStatus {

    PASSED("1"),
    ERROR("0"),
    NOT_PROCESSED("-1");

    private final String value;

    TestStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TestStatus fromValue(String v) {
        for (TestStatus c : TestStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
