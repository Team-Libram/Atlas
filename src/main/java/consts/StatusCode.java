package consts;

public enum StatusCode {
    UnknownError,
    InvalidPasswordError,
    InvalidAccountError,
    InvalidModel, NoSuchUserError;

    public static boolean contains(String test) {

        for (StatusCode c : StatusCode.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}