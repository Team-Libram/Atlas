package models;

import consts.StatusCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AtlasResult {
    public boolean succeeded;
    public StatusCode statusCode;
    public List<String> errors;

    protected AtlasResult() {
        this.succeeded = true;
        this.errors = Collections.emptyList();
    }

    protected AtlasResult(StatusCode statusCode, String... errors)
    {
        if (errors.length == 0) {
            // TODO: Move this message in a resource file.
            errors = new String[]{"An unknown failure has occurred."};
        }

        this.succeeded = false;
        this.statusCode = statusCode;
        this.errors = Arrays.asList(errors);
    }

    public static AtlasResult Success() {
        return new AtlasResult();
    }

    public static AtlasResult Failure(StatusCode statusCode, String... errors) {
        return new AtlasResult(statusCode, errors);
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
