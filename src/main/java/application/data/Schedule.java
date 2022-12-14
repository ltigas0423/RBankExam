package application.data;

import application.enums.DurationEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    public Schedule(Integer count, DurationEnum duration) {
        this.count = count;
        this.duration = duration;
    }

    private Integer count;

    private DurationEnum duration;

}
