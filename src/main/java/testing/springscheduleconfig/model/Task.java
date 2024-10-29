package testing.springscheduleconfig.model;

import java.util.Objects;

public final class Task implements Comparable<Task> {
    private String name;
    private RunsOn runsOn;
    private String group;
    private int order;

    public Task(String name, RunsOn runsOn) {
        this.name = name;
        this.runsOn = runsOn;
    }

    public Task() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RunsOn getRunsOn() {
        return runsOn;
    }

    public void setRunsOn(RunsOn runsOn) {
        this.runsOn = runsOn;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", runsOn=" + runsOn +
                ", group='" + group + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return order == task.order && Objects.equals(group, task.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, order);
    }

    @Override
    public int compareTo(Task o) {
        return o.getOrder() - this.getOrder();
    }
}
