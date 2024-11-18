package testing.springscheduleconfig.controller;

public class Task {
    private int order;
    private String name;
    public Task(int order, String name) {
        this.order = order;
        this.name = name;
    }

    public Task() {}
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {

    }

    @Override
    public String toString() {
        return "Task{" +
                "order=" + order +
                ", name='" + name + '\'' +
                '}';
    }
}
