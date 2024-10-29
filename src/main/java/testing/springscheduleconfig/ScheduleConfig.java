package testing.springscheduleconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ScheduleConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleConfig.class);

    @Bean
    public TaskScheduler taskScheduler() {
        LOG.info("creating threadpool task scheduler");
        return new ThreadPoolTaskScheduler();
    }
}
