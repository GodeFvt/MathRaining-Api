package sit.project.mathrainingapi.entities;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usersProfile")
public class UserProfile {
    @Id
    @Column(name = "_id")
    private String id;
    private String userName;
    private String descrition;
    private Integer rankingHighScore;
    private List<Achievement> completeAchievement;
    private ProfileConfig profileConfig;
    private List<Integer> totalScore;
    private List<Integer> totalTimePlay;
    private List<Integer> customLevel;
}
