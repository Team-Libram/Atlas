package models;

import consts.StatusCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IdentityResult {
    public boolean succeeded;
    public StatusCode statusCode;
    public List<String> errors;

    protected IdentityResult() {
        this.succeeded = true;
        this.errors = Collections.emptyList();
    }

    protected IdentityResult(StatusCode statusCode, String... errors) {
        if (errors.length == 0) {
            // TODO: Move this message in a resource file.
            errors = new String[]{"An unknown failure has occurred."};
        }

        this.succeeded = false;
        this.statusCode = statusCode;
        this.errors = Arrays.asList(errors);
    }

    public static IdentityResult Success() {
        return new IdentityResult();
    }

    public static IdentityResult Failure(StatusCode statusCode, String... errors) {
        return new IdentityResult(statusCode, errors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Succeeded: ").append(this.succeeded).append("\n");
        if (!this.succeeded) {
            sb.append("Status code:").append(this.statusCode).append("\n");
            sb.append("Errors: ").append("\n");
            for (String error : this.errors) {
                sb.append("\t").append(error).append("\n");
            }
        }

        return sb.toString();
    }
}
