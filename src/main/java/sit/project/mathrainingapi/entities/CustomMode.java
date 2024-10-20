package sit.project.mathrainingapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMode {
    private String nameLevel;
    private Integer numberRange;
    private Integer lengthExpression;
    private String[] symbol;
    private Integer dropSpeed;
    private Integer health;
    private Integer endScore;
}
