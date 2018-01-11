import com.opencsv.CSVWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OpenCSVWriter implements Job {
    private static final Logger logger = LogManager.getLogger("Scheduler");
    private static final String STRING_ARRAY_SAMPLE = "C:\\Users\\dorette_ong\\Desktop\\string-array-sample.csv";
    //weight weightcsv = new weight();

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
        logger.info("Executed");
        csvWriter.writeNext(new String[]{new SimpleDateFormat("dd").format(Calendar.getInstance().getTime())});
        logger.info("Header Recorded");
        if (!weight.collatedweights.isEmpty()) {
            for (Integer i : weight.collatedweights) {
                logger.info("Added" +Integer.toString(i));
                csvWriter.writeNext(new String[]{Integer.toString(i)});
               // csvWriter.writeNext(new String[]{"\n"});
            }
        }
        logger.info("Weight Recorded");
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

}