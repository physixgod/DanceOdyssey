package devnatic.danceodyssey.DAO.Entities;

import lombok.Data;

import java.util.List;
@Data
public class CategoryCreationRequest {
    private String categoryName;
    private List<String> subcategoryNames;

}
