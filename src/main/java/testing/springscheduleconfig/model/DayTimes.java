package testing.springscheduleconfig.model;

import java.util.Arrays;

public class DayTimes {
    private DayTime[] dayTimes;

    public DayTime[] getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(DayTime[] dayTimes) {
        this.dayTimes = dayTimes;
    }

    @Override
    public String toString() {
        return "DayTimes{" +
                "dayTimes=" + Arrays.toString(dayTimes) +
                '}';
    }
}
