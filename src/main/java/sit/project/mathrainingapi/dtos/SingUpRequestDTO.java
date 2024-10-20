package sit.project.mathrainingapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingUpRequestDTO {
    private String userName;
    private String password;
    private String email;
}
