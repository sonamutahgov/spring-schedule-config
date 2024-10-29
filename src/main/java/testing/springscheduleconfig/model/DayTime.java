package testing.springscheduleconfig.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public final class DayTime {
    private DayOfWeek day;
   // @JsonSerialize(using = LocalDateTimeSerializer.class)
   // @JsonFormat(pattern = "HH:mm:s")
    private LocalTime time;

    public DayTime() {

    }
    public DayTime(LocalTime time, DayOfWeek day) {
        this.time = time;
        this.day = day;
    }
    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DayTime{" +
                "dayOfWeek=" + day +
                ", localDateTime=" + time +
                '}';
    }
}
