package testing.springscheduleconfig.controller;

import com.asahaf.javacron.InvalidExpressionException;
import com.asahaf.javacron.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Job {
    private String id;
    private String name;
    private String second;
    private String minute;
    private String hour;
    private String dayOfMonth;
    private String month;
    private String dayOfWeek;
    private String timeZone;
    private Map<Integer, String> taskMap = new HashMap<>();
    private String cronExpression;
    private String nextRun;
    private boolean running;

    public Job() {

    }
    public Job(String id, String name, String second, String minute, String hour, String dayOfMonth, String month, String dayOfWeek, String timeZone,
               List<String> userTasks) {
        this.id = id;
        this.name = name;

        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.timeZone = timeZone;

        for(int i = 0; i < userTasks.size(); i++) {
            this.taskMap.put(i, userTasks.get(i));
        }
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private static final Logger LOG = LoggerFactory.getLogger(Job.class);

    public String getCronExpression()  {
        Schedule sched = null; //"0 0 1,5,23 * * *");
        try {
            final String cron = getCron();
            sched = Schedule.create(cron);
            String expression = sched.getExpression();
          //  LOG.info("expression: {}", expression);
            return expression;
        } catch (InvalidExpressionException e) {
            LOG.error("failed to create cron expression", e);
            return "failed to create cron expression";
        }
    }

    SimpleDateFormat dateFormatter = new SimpleDateFormat("y-MM-dd HH:mm:ss");

    public String getNextRun() {
        try {
            Schedule sched = Schedule.create(getCron());
            Date nextDateRun = sched.next();
          //  LOG.info("nextDateRun: {}", nextDateRun);
            return dateFormatter.format(nextDateRun);
        } catch (InvalidExpressionException e) {

            LOG.error("failed to get next run time", e);
        }
        return "";
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCron() {
        String cronExpression = "";

        toString();
        try {
            if (second == null || second.isEmpty()) {
                cronExpression = "* ";
            }
            else if (second.equalsIgnoreCase("Every second")) {
                cronExpression = "* ";
            }
            else if (second.equals("*")) {
                cronExpression = "* ";
            }
            else {
                cronExpression = second + " ";
            }
          //  LOG.info("second cronExpression: {}", cronExpression);

            if (minute == null || minute.isEmpty()) {
                cronExpression += "* ";
            }
            else if (minute.equalsIgnoreCase("Every minute")) {
                cronExpression += "* ";
            }
            else if (minute.equals("*")) {
                cronExpression += "* ";
            }
            else {
                cronExpression += minute + " ";
            }

            if (hour == null) {
                cronExpression += " ";
            }
            else if (hour.equalsIgnoreCase("Every hour")) {
                cronExpression += "* ";
            } else if (hour.equals("*")) {
                cronExpression += "* ";
            } else {
                cronExpression += hour + " ";
            }

            if (dayOfMonth == null) {
                cronExpression += " ";
            }
           else if (dayOfMonth.equalsIgnoreCase("Every day of month")) {
                cronExpression += "* ";
            } else if (dayOfMonth.equals("*")) {
                cronExpression += "* ";
            } else {
                cronExpression += dayOfMonth + " ";
            }

            if (month == null) {
                cronExpression += " ";
            }
            else if (month.equalsIgnoreCase("Every month")) {
                cronExpression += "* ";
            } else if (month.equals("*")) {
                cronExpression += "* ";
            } else {
                int monthNumber = monthStringToNumber(month);
                cronExpression += monthNumber + " ";
            }

            if (dayOfWeek == null) {
                cronExpression += " ";
            }
            else if (dayOfWeek.equalsIgnoreCase("Every Day")) {
                cronExpression += "* ";
            } else if (dayOfWeek.equals("*")) {
                cronExpression += "* ";
            } else {
                DayOfWeek dayOfWeek1 = DayOfWeek.valueOf(dayOfWeek.toUpperCase());

                cronExpression += (dayOfWeek1.getValue() -1)+ " ";
            }

        //    LOG.info("getCron() cronExpression: {}", cronExpression);
            return cronExpression;
        }
        catch (Exception e) {
            LOG.error("failed to parse cron expression", e);
            return "";
        }
    }

    public static int monthStringToNumber(String monthStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
            Month month = Month.from(formatter.parse(monthStr));
            return month.getValue();
        } catch (DateTimeParseException e) {
            LOG.error("Invalid month abbreviation: " + monthStr);
            return -1; // Or throw an exception
        }
    }

    public Map<Integer, String> getTaskMap() {
        return taskMap;
    }

    public void setTaskMap(Map<Integer, String> taskMap) {
        this.taskMap = taskMap;
    }

    public void setTasks(List<String> tasks) {
        LOG.info("set tasks in map.size: {}, map: {}", tasks.size(), taskMap);
        taskMap.clear();

        for(int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) != null && !tasks.get(i).isEmpty() && !tasks.get(i).equals("None")) {
                taskMap.put(i, tasks.get(i));
            }
        }
        LOG.info("added task back into map.size: {} and map: {}", tasks.size(), taskMap);
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", minute='" + minute + '\'' +
                ", hour='" + hour + '\'' +
                ", dayOfMonth='" + dayOfMonth + '\'' +
                ", month='" + month + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", tasks='" + taskMap + '\'' +
                '}';
    }

}
