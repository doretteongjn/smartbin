import com.opencsv.CSVWriter;
import com.google.firebase.database.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class weight {
   private static final Logger logger = LogManager.getLogger("Bin1");
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<Integer> weightlist = new ArrayList();
    public static List<Integer> collatedweights  = new ArrayList<>();
    int sum = 0;
    int averageweight=0;
    int previousweight=0;


    public void weightcheck(){


        DatabaseReference ref = database.getReference("Bin1");
            ref.child("Weight").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){


                    logger.info("Bin1");
                    logger.info("str = " + dataSnapshot.toString());
                    logger.info("TESTING");
                    int currentweight = Integer.parseInt(dataSnapshot.getValue().toString());

                    logger.info("integer is " + currentweight);
                    weightlist.add(currentweight);
                    if (weightlist.size()>20){
                        weightlist.remove(0);
                    }
                    logger.info("weightlist" + Arrays.asList(weightlist));
                    for (int i:weightlist){
                        sum+=i;
                    }
                    averageweight = sum/weightlist.size();
                    logger.info("sum " + sum);
                    logger.info("average weight is " + averageweight);
                    if (averageweight>currentweight && currentweight<200){
                        collatedweights.add(previousweight);
                    }
                    logger.info("collated weights " + Arrays.asList(collatedweights));
                    sum=0;
                    previousweight=currentweight;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    logger.info("The read failed: " + databaseError.getCode());
                }
            });
            try{
                TimeUnit.MINUTES.sleep(1000000000);
            }
            catch(Exception e){
                e.printStackTrace();
            }


    }
}

