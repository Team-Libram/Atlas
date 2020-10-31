package consts;

public enum StatusCode {
    UnexpectedError,
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