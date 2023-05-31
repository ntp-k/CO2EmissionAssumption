package co2emissionassumption;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co2emissionassumption.SayHi;
// import co2emissionassumption.JSONReadFromFile;
import co2emissionassumption.Rules;

public class App {
  public String getGreeting() {
    return "Hello World!";
}

  public static void main(String[] args) {
    
    System.out.println("Hello World");
    SayHi classObj = new SayHi();
    classObj.sayHi();

    int[] baseValues = {200, 180, 120, 150, 220};

    // JSONReadFromFile jsonReader = new JSONReadFromFile();
    // jsonReader.readJsonFile();

    String json = "[\n" +
                "    {\n" +
                "        \"start\": 0,\n" +
                "        \"end\": 380,\n" +
                "        \"percent\": 100\n" +
                "    },\n" +
                "    {\n" +
                "        \"start\": 380,\n" +
                "        \"end\": 600,\n" +
                "        \"percent\": 120\n" +
                "    },\n" +
                "    {\n" +
                "        \"start\": 600,\n" +
                "        \"end\": 700,\n" +
                "        \"percent\": 150\n" +
                "    }\n" +
                "]";

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(json);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                int start = Integer.parseInt(jsonObject.get("start").toString());
                int end = Integer.parseInt(jsonObject.get("end").toString());
                int percent = Integer.parseInt(jsonObject.get("percent").toString());

                Rules rules = new Rules(start, end, percent);

                // Do something with the Rules object
                System.out.println(rules.getStart() + " - " + rules.getEnd() + ": " + rules.getPercent());
            }
        } catch (ParseException e) {
          System.out.println("Eror parsing json");
            e.printStackTrace();
        }
  

  }
}