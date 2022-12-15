package application.data;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Task {

    private Long id;

    private List<Task> taskList = Lists.newArrayList();

    private Duration schedule;

    private Boolean isCompleted = Boolean.FALSE;

    public Boolean canStart() {
        if(taskList.isEmpty())
            return true;
        return taskList.stream().noneMatch(task -> !task.isCompleted);
    }

}
