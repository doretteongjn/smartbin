
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import java.io.*;


public class Central {


    public static void main(String[] args) throws Exception {

        try
        {
            //FileInputStream serviceAccount = new FileInputStream("C:\\Users\\dorette_ong\\IdeaProjects\\smartbin\\src\\main\\resources\\serviceAccountKey.json");
            InputStream serviceAccount = Central.class.getResourceAsStream("serviceAccountKey.json");
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
        UploadJSON convertingjson = new UploadJSON();
        convertingjson.firebasejson();

        while(true) {
            newweight.weightcheck();
        }


    }
}
