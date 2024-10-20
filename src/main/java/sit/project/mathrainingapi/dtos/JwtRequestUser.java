package sit.project.mathrainingapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtRequestUser {
    @NotBlank
    @Size(max = 50)
    private String userName;
    @Size(max = 14)
    @NotBlank
    private String password;
}