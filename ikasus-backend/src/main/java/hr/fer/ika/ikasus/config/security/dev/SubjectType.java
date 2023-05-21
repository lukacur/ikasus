package hr.fer.ika.ikasus.config.security.dev;

/**
 * @author Luka Ćurić
 */
public enum SubjectType {
    EMPLOYEE, MANAGER, CUSTOMER;

    public int getTypeId() {
        return switch (this) {
            case EMPLOYEE -> 0;
            case MANAGER -> 1;
            case CUSTOMER -> 3;
        };
    }

    public static SubjectType getType(int id) {
        return switch (id) {
            case 0 -> EMPLOYEE;
            case 1 -> MANAGER;
            case 3 -> CUSTOMER;
            default -> throw new IllegalArgumentException("Unbindable id");
        };
    }
}
