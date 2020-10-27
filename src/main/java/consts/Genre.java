package consts;

public enum Genre {
    Thriller, Fiction;

    public static boolean contains(String test) {

        for (Genre c : Genre.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}
