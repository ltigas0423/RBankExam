package application;

import application.data.ProjectPlan;
import application.data.Duration;
import application.data.Task;
import application.enums.DurationEnum;
import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.stream.Collectors;

public class RBankApp {

    private LocalDateTime lastDateFinished = LocalDateTime.now();

    public static void main(String[] args) {
        RBankApp rBankApp = new RBankApp();
        rBankApp.start();
    }


    public void start() {
        var projectPlan = generateTestData();

        generateSchedule(projectPlan);
    }

    public void generateSchedule(ProjectPlan projectPlan) {
        //SORT STREAM BY PRIORITY/ID
        var tasks = projectPlan.getTasks()
                .stream()
                .sorted((o1, o2) -> {
                    if(o1.getId()==o2.getId())
                        return 0;
                    else if(o1.getId()>o2.getId())
                        return 1;
                    else
                        return -1;
                })
                .collect(Collectors.toList());

        tasks.forEach(this::generateSchedule);

    }

    public void generateSchedule(Task task)  {
        if(task.canStart() && !task.getIsCompleted()) {
            LocalDateTime dateFinished = generateEndingHours(task.getSchedule(), lastDateFinished);
            System.out.println("Starting task: " + task.getId() +
                    "\nDuration: "  + task.getSchedule().getCount() + " " + task.getSchedule().getDuration() +
                    "\nDate Started: " + lastDateFinished.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) +
                    "\nDate Ending: " + dateFinished.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            task.setIsCompleted(Boolean.TRUE);
            lastDateFinished = dateFinished;
            return;
        }
        task.getTaskList().forEach(nextTask ->
                generateSchedule(nextTask));
        if(!task.getIsCompleted())
            generateSchedule(task);
    }

    public LocalDateTime generateEndingHours(Duration schedule, LocalDateTime startingDate) {
        var duration = schedule.getDuration();
        switch (duration) {
            case MINUTES:
                startingDate = startingDate.plusMinutes(schedule.getCount());
                break;
            case HOURS:
                startingDate = startingDate.plusHours(schedule.getCount());
                break;
            case DAYS:
                startingDate = startingDate.plusDays(schedule.getCount());
                break;
            case WEEKS:
                startingDate = startingDate.plusWeeks(schedule.getCount());
                break;
            case MONTHS:
                startingDate = startingDate.plusMonths(schedule.getCount());
                break;
        }
        return startingDate;
    }

    public ProjectPlan generateTestData() {
        var projectPlan = new ProjectPlan();
//              ProjectPlan
//        /\          /         \           \
//   task1  task2   task3         task6     task12
//                   /  \          /   \        /
//                 task4 task5   task7 task8  task11
//                                      /
//                                    task9
//                                      /
//                                     task10
//  Sequence
//      task1,  task2, task4, task5, task3, task7, task10, task9, task8, task6, task11, task12
//
        var task = new Task();
        task.setId(1L);
        task.setSchedule(new Duration(2, DurationEnum.DAYS));

        var task2 = new Task();
        task2.setId(2L);
        task2.setSchedule(new Duration(2, DurationEnum.HOURS));

        var task3 = new Task();
        task3.setId(3L);
        task3.setSchedule(new Duration(2, DurationEnum.DAYS));

        var task4 = new Task();
        task4.setId(4L);
        task4.setSchedule(new Duration(2, DurationEnum.WEEKS));

        var task5 = new Task();
        task5.setId(5L);
        task5.setSchedule(new Duration(2, DurationEnum.DAYS));

        task3.setTaskList(Lists.newArrayList(task4, task5));

        var task6 = new Task();
        task6.setId(6l);
        task6.setSchedule(new Duration(2, DurationEnum.MONTHS));

        var task7 = new Task();
        task7.setId(7l);
        task7.setSchedule(new Duration(2, DurationEnum.MINUTES));

        var task8= new Task();
        task8.setId(8l);
        task8.setSchedule(new Duration(2, DurationEnum.DAYS));

        task6.setTaskList(Lists.newArrayList(task7,task8));

        var task9 = new Task();
        task9.setId(9l);
        task9.setSchedule(new Duration(2, DurationEnum.DAYS));

        task8.setTaskList(Lists.newArrayList(task9));

        var task10 = new Task();
        task10.setId(10l);
        task10.setSchedule(new Duration(2, DurationEnum.DAYS));

        task9.setTaskList(Lists.newArrayList(task10));

        var task11 =  new Task();
        task11.setId(11L);
        task11.setSchedule(new Duration(5, DurationEnum.DAYS));

        var task12 = new Task();
        task12.setId(12L);
        task12.setSchedule(new Duration(3, DurationEnum.DAYS));

        task12.setTaskList(Lists.newArrayList(task11));

        projectPlan.setTasks(Lists.newArrayList(task, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11,  task12));

        return projectPlan;
    }


}
