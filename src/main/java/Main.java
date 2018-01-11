import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) throws Exception {
        try
        {   // Fetch the service account key JSON file contents
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\dorette_ong\\Desktop\\School_2017\\serviceAccountKey.json");
           // Map<String, Object> auth = new HashMap<String, Object>();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://smartbintest1-1eafb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        WeightNew newweight = new WeightNew();
        ScheduleWrite scheduleWrite = new ScheduleWrite();
        scheduleWrite.schedulerwrite();

        while(true) {
            newweight.weightcheck();
        }


    }
}
