package application.data;

import lombok.Data;

import java.util.List;

@Data
public class ProjectPlan {

    private List<Task> tasks;

}
