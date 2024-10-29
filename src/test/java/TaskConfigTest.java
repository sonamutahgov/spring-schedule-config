import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import testing.springscheduleconfig.model.TaskConfig;
import testing.springscheduleconfig.model.DayTimes;
import testing.springscheduleconfig.model.RunsOn;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = TaskConfig.class)
public class TaskConfigTest {
    private static final Logger LOG = LoggerFactory.getLogger(TaskConfig.class);

    @Value("classpath:taskconfig.json")
    private Resource taskConfigJson;

    @Value("classpath:daytime.json")
    private Resource daytimeJson;

    @Value("classpath:runsOn.json")
    private Resource runsOnJson;

    // this test will load the taskconfig.json file and marshal
    @Test
    public void loadJson() {
        assertThat(taskConfigJson).isNotNull();

        try {
            TaskConfig taskConfig = getTaskConfig();
            LOG.info("taskConfig: {}", taskConfig);

            Arrays.stream(taskConfig.getTasks()).sequential().forEach(task -> {
                LOG.info("task: {}", task.getRunsOn().getTime().format(DateTimeFormatter.ofPattern("hh mm ss a")));
            });
        }
        catch (Exception e) {
            LOG.error("Exception occured", e);
            fail("loadJson failed");
        }
    }

    public TaskConfig getTaskConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(taskConfigJson.getFile(), TaskConfig.class);
    }


    // these are breakdown of the larger taskconfig.json file
    // this test was written to marshal smaller parts of the json file
    @Test
    public void loadDayTimeJson() {
        assertThat(daytimeJson).isNotNull();
        try {
            DayTimes dayTimes = marshal(daytimeJson.getFile());
            LOG.info("daytime: {}", dayTimes);
        }
        catch (Exception e) {
            LOG.error("Exception occured", e);
            fail("failed to load daytime json");
        }
    }

    public DayTimes marshal(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(file, DayTimes.class);
    }

    @Test
    public void parseLocalTIme() {
        final String time = "03:12:01";
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
        LOG.info("localTime: {}", localTime);
    }

    @Test
    public void loadRunsOnJson() {
        assertThat(daytimeJson).isNotNull();
        try {
            RunsOn runsOn = runsOnMarshal(runsOnJson.getFile());
            LOG.info("runsOn: {}", runsOn);
        }
        catch (Exception e) {
            LOG.error("Exception occured", e);
            fail("failed to load runsOn json");
        }
    }

    public RunsOn runsOnMarshal(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(file, RunsOn.class);
    }
}
