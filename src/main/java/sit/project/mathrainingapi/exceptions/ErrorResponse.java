package sit.project.mathrainingapi.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)// เอาเฉพาะ ตัวที่ไม่เป็น null ส่งไปเป็น JSON
public class ErrorResponse {
    private final String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    private final int status;
    private final String message;
    private final String instance;
    private String stackTrace;
    private List<ValidationError> errors;
    @Getter
    @Setter
    @RequiredArgsConstructor //มี constructor ที่ต้อง require  , final ต้อง require เพราะมันเปลี่ยนไม่ได้มีโอกาส set ค่าได้ครั้งเดียว
    public static class ValidationError {
        private final String field;
        private final String message;
    }
    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }   errors.add(new ValidationError(field, message));
    }
}
