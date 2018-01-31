import java.io.*;

import java.util.*;
import java.io.File;
import java.util.List;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UploadJSON {
    File input = new File("out.csv");
    File output = new File("output.json");
    List<Object> readAll;
    Gson gson = new Gson();

    public void convertjson() throws IOException {


        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

        ObjectMapper mapper = new ObjectMapper();

        // Write JSON formated data to output.json file
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);

        // Write JSON formated data to stdout
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
    }


    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public void firebasejson() throws IOException, JSONException {
        String jsonstring = "";

        try {
            JSONParser parser = new JSONParser();
            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            org.json.simple.JSONArray data = (org.json.simple.JSONArray) parser.parse(new FileReader(output));//path to the JSON file.

            jsonstring= data.toJSONString();
            System.out.println("HEREDATA");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        convertjson();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Linux");

        JSONObject jsonobject = new JSONObject(jsonstring.replace("[","").replace("[",""));
        Map<String, Object> jsonMap = jsonToMap(jsonobject);
        ref.setValueAsync("1");
    }
}


       /*
       Gson gson = new Gson();
       JsonReader reader = new JsonReader(new FileReader(output));
       Map<String,Object> jsonMap = new Gson().fromJson(jsonstring, new TypeToken<HashMap<String,Object>>(){}.getType());
       ref.setValueAsync(jsonMap);
       System.out.println("JSONSTRING");

       */

