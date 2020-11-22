package consts;

public enum UserType {
    Reader,
    Operator,
    Administrator;

    public static boolean contains(String test) {

        for (UserType c : UserType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}
