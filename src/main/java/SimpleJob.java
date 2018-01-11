import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SimpleJob implements Job {
    private static final Logger logger = LogManager.getLogger("Scheduler");


    private static void writeCsv(String[][] csvMatrix) throws IOException {


        CsvListReader reader = new CsvListReader(new FileReader("out.csv"), CsvPreference.STANDARD_PREFERENCE);
        CsvListWriter writer = new CsvListWriter(new FileWriter("out1.csv"), CsvPreference.STANDARD_PREFERENCE);
        List<String> columns;
        System.out.println(reader.read());
        int k = 1;
        String[][] finalarray = new String[31][31];
        while ((columns = reader.read()) != null) {
            String[] newarray = columns.toArray(new String[0]);
            for (int i =0; i<newarray.length;i++){
                finalarray[k][i] = newarray[i];
                System.out.println(newarray[i]);
            };
            System.out.println(newarray);
            //System.out.println(finalarray);
            k ++;
        }
        DateFormat formatter = new SimpleDateFormat("dd");
        String today = formatter.format(Calendar.getInstance().getTime());
        Integer date = Integer.parseInt(today);
        finalarray[1][date] = Calendar.getInstance().getTime().toString();
        //finalarray[1][date+1] = Calendar.getInstance().getTime().toString() +"TESTING ";
        if (!WeightNew.collatedweights.isEmpty()) {
        System.out.println("ADDED HERE");
        for (int i = 0; i < WeightNew.collatedweights.size(); i++) {
                System.out.println("IN LOOP");
                logger.info("Added" + Integer.toString(WeightNew.collatedweights.get(i)));
                finalarray[i + 2][date] = Integer.toString(WeightNew.collatedweights.get(i));
            }
        }
        for (int j = 0; j < finalarray.length; j++) {
            writer.write(finalarray[j]);
        }
        reader.close();

        /*csvMatrix[1][1]= "testing";
        if (!weight.collatedweights.isEmpty()) {
            for (int i = 0; i < weight.collatedweights.size(); i++) {
                logger.info("Added" + Integer.toString(i));
                csvMatrix[i + 1][date] = Integer.toString(weight.collatedweights.get(i));
            }
        }
        for (int i = 0; i < csvMatrix.length; i++) {
            writer.write(csvMatrix[i]);
        }*/

        writer.close();
        k=0;
/*
        ICsvListWriter csvWriter = null;
        try {
            csvWriter = new CsvListWriter(new FileWriter("out.csv"),
                    CsvPreference.STANDARD_PREFERENCE);

            for (int i = 0; i < csvMatrix.length; i++) {
                csvWriter.write(csvMatrix[i]);
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO handle exception properly
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
            }
        }

    }*/
    }


    public void execute(JobExecutionContext context) throws JobExecutionException {
        final String[][] csvMatrix = new String[31][31];
        try {
            writeCsv(csvMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }

       Path source = Paths.get("C:\\Users\\dorette_ong\\IdeaProjects\\smartbin\\out1.csv");
        try {
            Files.move(source, source.resolveSibling("out.csv"),REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}