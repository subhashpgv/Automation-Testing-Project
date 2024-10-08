package o2technologies_utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



/**
 * Utility class to load and parse JSON input data from a specified file into a list of key-value maps.
 */

public class InputValues {
    private List<Map<String, String>> inputValuesList;

    public InputValues(String fileName) {
        inputValuesList = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Map<String, String> inputValues = new HashMap<>();
                    jsonObject.keys().forEachRemaining(key -> inputValues.put(key, jsonObject.getString(key)));
                    inputValuesList.add(inputValues);
                    
                }
            } else {
                System.err.println("Resource not found: " + fileName);
            }
           // System.out.println(inputValuesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getInputValuesList() {
        return inputValuesList;
    }
}
