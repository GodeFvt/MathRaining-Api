package sit.project.mathrainingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private final List<ErrorResponse.ValidationError> errors = new ArrayList<>();

    public ValidationException(String message) {
        super(message);
    }

    public void addValidationError(String field, String message) {
        this.errors.add(new ErrorResponse.ValidationError(field, message));
    }

    public List<ErrorResponse.ValidationError> getErrors() {
        return errors;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
