import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import testing.springscheduleconfig.compare.GroupComparator;
import testing.springscheduleconfig.compare.OrderComparator;
import testing.springscheduleconfig.controller.Job;
import testing.springscheduleconfig.model.Task;
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
import java.util.*;

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
            //taskConfig stores all tasks as an array

            assertThat(taskConfig.getTasks().length).isEqualTo(6);

            //getTaskGroup will 'group' the tasks by their 'group-name' and put them in 'order' for tasks in each group
            /*
            example:
            'group-a' : task-1 (order=1), task-2(order=2)
             'group-b' : task-3 (order=1)
             */
            Map<String, List<Task>> map = getTaskGroup(taskConfig);
            List<String> groupKeySet = new ArrayList<>(map.keySet());
            Collections.sort(groupKeySet);

            groupKeySet.forEach(group -> {
                LOG.info("group: {}", group);

                map.get(group).forEach(task -> {
                    LOG.info("task: {}", task);
                });
            });

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

    private Map<String, List<Task>> getTaskGroup(TaskConfig taskConfig) {
        List<Task> taskList = Arrays.asList(taskConfig.getTasks());
        taskList.sort(new GroupComparator());

        Map<String, List<Task>> map = new HashMap<>();

        taskList.forEach(task -> {
            if (map.containsKey(task.getGroup())) {
                List<Task> mapTaskList = map.get(task.getGroup());
                mapTaskList.add(task);
                mapTaskList.sort(new OrderComparator());
            }
            else {
                LOG.info("create a linked list node with for new group");
                List<Task> linkedList = new LinkedList<>();
                linkedList.add(task);

                map.put(task.getGroup(), linkedList);
            }
        });

        return map;
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

    @Test
    public void testTaskMap() {
        Job job = new Job();
        //Map<Integer, String> taskMap = new HashMap<>();
        HashMap<Integer, String> taskMap = new HashMap<>();
        taskMap.put(0, "Print Hello Job");
        job.setId("1");
        job.setTaskMap(taskMap);
        LOG.info("task.size: {}, taskMap: {}", job.getTaskMap().size(), job.getTaskMap());

        for(Map.Entry<Integer, String> task: job.getTaskMap().entrySet()) {
            try {
                LOG.info("running task.id {} task.value: {}", job.getId(), task.getKey(), job.getTaskMap().get(task.getKey()));
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOG.error("error in sleeping for 60 seconds");
            }
            LOG.info("task.id: {} task.value: '{}' is done",task.getKey(), task.getValue());
        }
        LOG.info("done running job, set running to false");

    }
}
