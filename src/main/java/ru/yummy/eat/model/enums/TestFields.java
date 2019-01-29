package ru.yummy.eat.model.enums;

public enum TestFields {

    HTML_RESULT("Получение HTML"),
    HTML_CLEAN("Очистка HTML"),
    HTML_TAG("Получение блока НТML"),
    PRODUCT_NAME("Имя Продукта"),
    PRODUCT_DESC("Описание"),
    PRODUCT_IMG("Изображение"),
    WSP_ONE("1) Вес, Размер, Цена"),
    WSP_TWO("2) Вес, Размер, Цена"),
    WSP_THREE("3) Вес, Размер, Цена"),
    WSP_FOUR("4) Вес, Размер, Цена");

    private final String value;

    TestFields(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TestFields fromValue(String v) {
        for (TestFields c : TestFields.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
