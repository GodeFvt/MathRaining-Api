package sit.project.mathrainingapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileConfig {
    private double effectVolume;
    private double musicVolume;
    private String[] showAchievementBadge;
}
