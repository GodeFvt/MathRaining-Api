package sit.project.mathrainingapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDTO {
    private Integer first;
    private Integer prev;
    private Integer next;
    private Integer last;
    private Integer pages;
    private Integer items;
    private List<?> data;
}
