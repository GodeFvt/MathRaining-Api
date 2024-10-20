package sit.project.mathrainingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //status ที่อยู่บน header response
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
