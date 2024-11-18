package testing.springscheduleconfig.controller;

import com.asahaf.javacron.InvalidExpressionException;
import com.asahaf.javacron.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class MvcController {

    private static final Logger LOG = LoggerFactory.getLogger(MvcController.class);

    private Map<String, Job> jobMap = new HashMap<String, Job>();
    private final TaskScheduler taskScheduler;

    public MvcController(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;

        for(int i = 0; i < 10; i++) {
            Job job = new Job(i+"", "my-job-"+i, "Every second", "Every minute", "Every hour", "Every day of month", "Every month", "Every day", "US/Mountain", List.of("Print Hello Job"));
            jobMap.put(i+"", job);
        }
    }
    @GetMapping
    public String getIndexPage() {
        LOG.info("return index page");
        return "index";
    }

    @GetMapping("jobs")
    public Mono<String> getJobs(Model model) {
        LOG.info("return scheduled jobs");
        final String PATH = "jobs";

        model.addAttribute("jobs", jobMap.values());
        return Mono.just(PATH);
    }


    @GetMapping("addjob")
    public Mono<String> getAddJobPage(Model model) {
        LOG.info("return addjob page 4");
        final String PATH = "addjob";

        Job job = new Job();
        model.addAttribute("job", job);
        return Mono.just(PATH);
    }

    @GetMapping("/{id}")
    public Mono<String> getJobPage(@PathVariable("id") String id, Model model) {
        LOG.info("get job by id: {}", id);

        final String PATH = "addjob";

        if (jobMap.containsKey(id)) {
            Job job = jobMap.get(id);
            LOG.info("found job: {}", job);

            model.addAttribute("job",job);
        }
        else {
            model.addAttribute("job", new Job());
        }
        return Mono.just(PATH);
    }

    @PostMapping("/update")
    public Mono<String> updateJob(@RequestBody @ModelAttribute("job") Job job, Model model) {
        LOG.info("update job");

        final String PATH = "addjob";
        if (job.getId() == null || job.getId().isEmpty()) {
            job.setId(jobMap.size()+1+"");
        }

        jobMap.put(job.getId(), job);

        scheduleJob(job);

        model.addAttribute("job", job);
        return Mono.just(PATH);
    }

    private final Runnable myTask= () -> {
        LOG.info("task-1 running at time {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh mm:ss a")));
    };

    private void scheduleJob(Job job) {
        LOG.info("scheduling job");

        final String cronExpression = job.getCronExpression();
        LOG.info("job.cronExpression: {}", cronExpression);

        CronTrigger cronTrigger = new CronTrigger(cronExpression);

        taskScheduler.schedule(job, cronTrigger);

    }


    private void jobRunning(Job job) {

    }
}
