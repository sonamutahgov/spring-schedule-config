package testing.springscheduleconfig.model;

import java.time.Month;
import java.util.Arrays;

public final class DateOfMonth {
    private Month[] month;
    private int[] monthDay;

    public DateOfMonth() {

    }

    public Month[] getMonth() {
        return month;
    }

    public void setMonth(Month[] month) {
        this.month = month;
    }

    public int[] getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int[] monthDay) {
        this.monthDay = monthDay;
    }

    @Override
    public String toString() {
        return "DateOfMonth{" +
                "month=" + Arrays.toString(month) +
                ", monthDay=" + Arrays.toString(monthDay) +
                '}';
    }
}
