package models;

import java.util.Arrays;
import java.util.Collections;

public class IdentityResult {
    public boolean succeeded;
    public Iterable<String> errors;

    protected IdentityResult(boolean success) {
        this.succeeded = true;
        this.errors = Collections.emptyList();
    }

    protected IdentityResult(String... errors)
    {
        if (errors.length == 0) {
            // TODO: Move this message in a resource file.
            errors = new String[]{"An unknown failure has occurred."};
        }

        this.succeeded = false;
        this.errors = Arrays.asList(errors);
    }

    public static IdentityResult Success() {
        return new IdentityResult(true);
    }

    public static IdentityResult Failure(int statusCode, String... errors) {
        return new IdentityResult(errors);
    }
}
