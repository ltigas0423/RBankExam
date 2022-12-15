package application.data;

import application.enums.DurationEnum;
import lombok.Data;

@Data
public class Duration {

    public Duration(Integer count, DurationEnum duration) {
        this.count = count;
        this.duration = duration;
    }

    private Integer count;

    private DurationEnum duration;

}
