import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateHtmlOutput {
    private static final Logger LOG = LoggerFactory.getLogger(GenerateHtmlOutput.class);

    @Test
    public void generateMinuteOption() {
        for(int i = 0; i <= 59; i++) {
            System.out.println("<option value=\""+i+"\">"+ i +"</option>");
        }
    }

    @Test
    public void generateHourOption() {
        for(int i = 0; i <= 23; i++) {
            System.out.println("<option value=\""+i+"\">"+ i +"</option>");
        }
    }

    @Test
    public void generateDaysInMonthOption() {
        for(int i = 1; i <= 31; i++) {
            System.out.println("<option value=\""+i+"\">"+ i +"</option>");
        }
    }
}
