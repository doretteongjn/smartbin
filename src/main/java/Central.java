import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Central {

    private void run(Storage storage, BlobId blobId, Path downloadTo) throws IOException {
        com.google.cloud.storage.Blob blob = storage.get(blobId);
        if (blob == null) {
            System.out.println("No such object");
            return;
        }
        PrintStream writeTo = System.out;
        if (downloadTo != null) {
            writeTo = new PrintStream(new FileOutputStream(downloadTo.toFile()));
        }
        if (blob.getSize() < 1_000_000) {
            // Blob is small read all its content in one request
            byte[] content = blob.getContent();
            writeTo.write(content);
        } else {
            // When Blob size is big or unknown use the blob's channel reader.
            try (ReadChannel reader = blob.reader()) {
                WritableByteChannel channel = Channels.newChannel(writeTo);
                ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
                while (reader.read(bytes) > 0) {
                    bytes.flip();
                    channel.write(bytes);
                    bytes.clear();
                }
            }
        }
        if (downloadTo == null) {
            writeTo.println();
        } else {
            writeTo.close();
        }
    }


    public static void main(String[] args) throws Exception {
       Storage storage = StorageOptions.getDefaultInstance().getService();
        //The name for the new bucket
        String bucketName = "smartbintest1-1eafb.appspot.com";  // "my-new-bucket";

        //Creates the new bucket
        Bucket bucket = storage.create(BucketInfo.of(bucketName));
        Path path = Paths.get(args[0]);
        new Central().run(storage,BlobId.of(bucketName,"serviceAccountKey.json"),path);

        try
        {   // Fetch the service account key JSON file contents
            FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");
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
