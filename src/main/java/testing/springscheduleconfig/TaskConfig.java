package testing.springscheduleconfig;

import testing.springscheduleconfig.json.Task;

import java.util.Arrays;

public class TaskConfig {
    private Task[] tasks;

    public TaskConfig(Task[] tasks) {
        this.tasks = tasks;
    }

    public TaskConfig() {}

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }
    public Task[] getTasks() {
        return this.tasks;
    }

    @Override
    public String toString() {
        return "TaskConfig{" +
                "tasks=" + Arrays.toString(tasks) +
                '}';
    }
}

