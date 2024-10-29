package testing.springscheduleconfig.compare;

import testing.springscheduleconfig.model.Task;

import java.util.Comparator;

public class GroupComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getGroup().compareTo(o2.getGroup());
    }
}
