
import com.google.firebase.database.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static java.lang.Thread.sleep;

public class WeightNew {
    private static final Logger logger = LogManager.getLogger("Bin1");
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
     List<Integer> weightlist = new ArrayList();
    public static List<Integer> collatedweights = new ArrayList<>();
    int sum = 0;
    int sumend = 0;
    int averageweight = 0;
    int currentweight = 0;

    public List<Integer> getcollatedweights(){
        return collatedweights;
    }


    public void weightcheck() {

        DatabaseReference ref = database.getReference("Bin1");
        ref.child("Weight").addValueEventListener(new ValueEventListener() {

            //
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                logger.info("Bin1");
                logger.info("str = " + dataSnapshot.toString());
                logger.info("TESTING");
                currentweight = Integer.parseInt(dataSnapshot.getValue().toString());

                logger.info("integer is " + currentweight);
                weightlist.add(currentweight);

                if (weightlist.size() > 20) {
                    weightlist.remove(0);
                }
                logger.info("weightlist" + Arrays.asList(weightlist));
                sum = 0;
                sumend = 0;
                //int[] weights = weightlist.stream().mapToInt(i -> i).toArray();
                //int sum = IntStream.of(weights).sum();
                for (int i : weightlist) {
                    sum += i;
                }
                averageweight = sum / weightlist.size();
                logger.info("sum " + sum);

                for (int j = 10; j < weightlist.size(); j++) {
                    sumend += weightlist.get(j);
                }
                System.out.println("SUMEND " + sumend);
                logger.info("average weight is " + averageweight);
                if (averageweight > currentweight && (sumend / 10) < 200) {
                    collatedweights.add((sum - sumend) / 10);
                    weightlist.clear();
                }
                logger.info("collated weights " + Arrays.asList(collatedweights));

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.info("The read failed: " + databaseError.getCode());
            }
        });

        try {
            sleep(4000);
            weightlist.add(currentweight);
            if (weightlist.size() > 20) {
                weightlist.remove(0);
            }

           // TimeUnit.MINUTES.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


