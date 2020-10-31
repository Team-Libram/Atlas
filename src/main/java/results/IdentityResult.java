package results;

import consts.StatusCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IdentityResult {
    public boolean succeeded;
    public StatusCode statusCode;
    public List<String> errors;
    public Object payload;

    protected IdentityResult() {
        this(null);
    }

    protected IdentityResult(Object payload) {
        this.succeeded = true;
        this.errors = Collections.emptyList();
        this.payload = payload;
    }

    protected IdentityResult(StatusCode statusCode, Object payload, String... errors) {
        if (errors.length == 0) {
            // TODO: Move this message in a resource file.
            errors = new String[]{"An unknown failure has occurred."};
        }

        this.succeeded = false;
        this.statusCode = statusCode;
        this.errors = Arrays.asList(errors);
        this.payload = payload;
    }

    public static IdentityResult Success() {
        return new IdentityResult();
    }

    public static IdentityResult Success(Object payload) {
        return new IdentityResult(payload);
    }

    public static IdentityResult Failure(Object payload, String... errors) {
        return new IdentityResult(StatusCode.UnexpectedError, payload, errors);
    }
    public static IdentityResult Failure(StatusCode statusCode, Object payload, String... errors) {
        return new IdentityResult(statusCode, payload, errors);
    }

    public static IdentityResult Failure(String... errors) {
        return new IdentityResult(StatusCode.UnexpectedError, null, errors);
    }

    public static IdentityResult Failure(StatusCode statusCode, String... errors) {
        return new IdentityResult(statusCode, null, errors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Succeeded: ").append(this.succeeded).append("\n");
        if (!this.succeeded) {
            sb.append("Status code:").append(this.statusCode).append("\n");
            sb.append("Payload:").append(this.payload).append("\n");
            sb.append("Errors: ").append("\n");
            for (String error : this.errors) {
                sb.append("\t").append(error).append("\n");
            }
        }

        return sb.toString();
    }
}
