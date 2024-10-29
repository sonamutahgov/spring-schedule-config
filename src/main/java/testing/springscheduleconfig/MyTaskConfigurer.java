package testing.springscheduleconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import testing.springscheduleconfig.model.TaskConfig;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
@EnableScheduling
public class MyTaskConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(MyTaskConfigurer.class);

    private TaskScheduler taskScheduler;

    @Value("classpath:taskconfig.json")
    private Resource taskConfigFile;

    private final Runnable myTask1;
    private final Runnable myTask2;

    @Autowired
    public MyTaskConfigurer(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;

        myTask1 = () -> {
            LOG.info("task-1 running at time {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh mm:ss a")));
        };

        myTask2 = () -> {
            LOG.info("task-2 running at time {}",  LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh mm:ss a")));
        };
    }

    @PostConstruct
    public void scheduleTasks() {
        LOG.info("scheduling tasks");
        try {
            TaskConfig taskConfig = getTaskConfig();

            int firstTaskSeconds = taskConfig.getTasks()[0].getRunsOn().getFixedRate().getSeconds();
            LOG.info("task-1 runs every {} seconds", firstTaskSeconds);
            taskScheduler.scheduleAtFixedRate(myTask1,  Duration.ofSeconds(firstTaskSeconds));

            int secondTaskSeconds = taskConfig.getTasks()[1].getRunsOn().getFixedRate().getSeconds();
            LOG.info("task-2 runs every {} seconds", secondTaskSeconds);
            taskScheduler.scheduleAtFixedRate(myTask2,  Duration.ofSeconds(secondTaskSeconds));

        } catch (IOException e) {
            LOG.error("failed to get taskConfig object", e);
        }

    }

    @Bean
    public TaskConfig getTasConfig() {
        return loadJson();
    }

    private TaskConfig loadJson() {
        try {
            TaskConfig taskConfig = getTaskConfig();
            LOG.info("taskConfig: {}", taskConfig);

            Arrays.stream(taskConfig.getTasks()).sequential().forEach(task -> {
                LOG.info("task: {}", task.getRunsOn().getTime().format(DateTimeFormatter.ofPattern("hh mm ss a")));
            });
            return taskConfig;
        }
        catch (Exception e) {
            LOG.error("Exception occured", e);
        }
        return null;
    }

    public TaskConfig getTaskConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        LOG.info("read file into TaskConfig object");

        return objectMapper.readValue(taskConfigFile.getFile(), TaskConfig.class);
    }

}
