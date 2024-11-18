package testing.springscheduleconfig.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JobRunner implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(JobRunner.class);

    private final Job job;
    private boolean running;

    public JobRunner(Job job) {
        this.job = job;
    }
    public void run() {
        LOG.info("task.size: {}, taskMap: {}", job.getTaskMap().size(), job.getTaskMap());
        this.job.setRunning(true);
        int i = 0;

        for(Map.Entry<Integer, String> task: job.getTaskMap().entrySet()) {
            try {
                LOG.info("running {} task.id {} task.value: {}", i++, job.getId(), task.getKey(), job.getTaskMap().get(task.getKey()));
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOG.error("error in sleeping for 60 seconds");
            }
            LOG.info("task.id: {} task.value: '{}' is done",task.getKey(), task.getValue());
        }
        LOG.info("done running job, set running to false");
        this.job.setRunning(false);
    }
}
