
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


import com.opencsv.CSVReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class WritingCSV implements Job {
    private static final Logger logger = LogManager.getLogger("Scheduler");
    /**
     * Sets up the processors used for the examples. There are 10 CSV columns, so 10 processors are defined. Empty
     * columns are read as null (hence the NotNull() for mandatory columns).
     *
     * @return the cell processors
     */



    private static void writeCsv(String[][] csvMatrix) throws IOException {


        CsvListWriter writer = new CsvListWriter(new FileWriter("./out1.csv"), CsvPreference.STANDARD_PREFERENCE);

        String[][] finalarray = new String[7][32];

        CSVReader reader = new CSVReader(new FileReader("./out.csv"));
        String [] nextLine;
        List<String[]> myEntries = reader.readAll();
        for (int i=0;i<myEntries.size();i++){
            finalarray[i]=myEntries.get(i);
            System.out.println(myEntries.get(i).toString());
        }
       // finalarray= myEntries.toArray(new String[7][32]);
        System.out.println("MY ENTRIES" + myEntries);

        DateFormat formatter = new SimpleDateFormat("dd");
        String today = formatter.format(Calendar.getInstance().getTime());
        Integer date = Integer.parseInt(today);
        finalarray[0][date]= Calendar.getInstance().getTime().toString();
        finalarray[1][date]="0";
        finalarray[2][date]="0";
        finalarray[3][date]="0";
        finalarray[4][date]="0";
        finalarray[5][date]="0";

        //finalarray[1][date+1] = Calendar.getInstance().getTime().toString() +"TESTING ";
        if (!WeightNew.collatedweights.isEmpty()) {
            System.out.println("ADDED HERE");
            for (int i = 0; i < WeightNew.collatedweights.size(); i++) {
                System.out.println("IN LOOP");
                logger.info("Added" + Integer.toString(WeightNew.collatedweights.get(i)));
                finalarray[i + 1][date] = Integer.toString(WeightNew.collatedweights.get(i));
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

    }


    public void execute(JobExecutionContext context) throws JobExecutionException {
        final String[][] csvMatrix = new String[31][31];
        try {
            writeCsv(csvMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Path source = Paths.get("./out1.csv");
        try {
            Files.move(source, source.resolveSibling("./out.csv"),REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}