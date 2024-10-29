package testing.springscheduleconfig.json;

import java.time.LocalTime;
import java.util.Arrays;

public final class RunsOn {
    private LocalTime time;
    private DayTime[] dayTimes;
    private DateOfMonth dateOfMonth;
    private FixedRate fixedRate; //for periodic intervals

    public RunsOn() {

    }

    public RunsOn(LocalTime time, DayTime[] dayTimes, DateOfMonth dateOfMonth) {
        this.time = time;
        this.dayTimes = dayTimes;
        this.dateOfMonth = dateOfMonth;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public DayTime[] getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(DayTime[] dayTimes) {
        this.dayTimes = dayTimes;
    }

    public DateOfMonth getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(DateOfMonth dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

    public FixedRate getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(FixedRate fixedRate) {
        this.fixedRate = fixedRate;
    }

    @Override
    public String toString() {
        return "RunsOn{" +
                "time=" + time +
                ", dayTimes=" + Arrays.toString(dayTimes) +
                ", dateOfMonth=" + dateOfMonth +
                ", fixedRate=" + fixedRate +
                '}';
    }
}
