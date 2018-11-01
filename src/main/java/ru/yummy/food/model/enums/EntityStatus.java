package ru.yummy.food.model.enums;

public enum EntityStatus {

        NEW("НОВОЕ"),
        DELETE("УДАЛИТЬ"),
        UPDATED("ОБНОВЛЕНО");

        private final String value;

        EntityStatus(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static EntityStatus fromValue(String v) {
            for (EntityStatus c : EntityStatus.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

}
