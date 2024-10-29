package testing.springscheduleconfig.model;

public final class Task {
    private String name;
    private RunsOn runsOn;

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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", runsOn=" + runsOn +
                '}';
    }
}
