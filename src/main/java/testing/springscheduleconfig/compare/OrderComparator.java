package testing.springscheduleconfig.compare;

import testing.springscheduleconfig.model.Task;

import java.util.Comparator;

public class OrderComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getOrder() - o2.getOrder();
    }
}
