package sit.project.mathrainingapi.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sit.project.mathrainingapi.entities.Achievement;
import sit.project.mathrainingapi.entities.ProfileConfig;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersProfileResponseDTO {
    private String id;
    private String userName;
    private String descrition;
    private Integer rankingHighScore;
    private List<Achievement> completeAchievement;
    private ProfileConfig profileConfig;
    private List<Integer> totalScore;
    private List<Integer> totalPlayTime;
    private List<Integer> customLevel;
}
