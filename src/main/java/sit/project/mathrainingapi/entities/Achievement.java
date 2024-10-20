package sit.project.mathrainingapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private String id;
    private String name;
    private String description;
    private Boolean isComplete;
}
