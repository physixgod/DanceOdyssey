package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProgressDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int calories;
    private boolean goalAchieved;
    private int goal;

}
