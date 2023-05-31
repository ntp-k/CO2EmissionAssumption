package co2emissionassumption;

import java.util.ArrayList;
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

    String json = "[\n" + "    {\n" + "        \"start\": 0,\n" + "        \"end\": 380,\n"
        + "        \"percent\": 100\n" + "    },\n" + "    {\n" + "        \"start\": 380,\n"
        + "        \"end\": 600,\n" + "        \"percent\": 120\n" + "    },\n" + "    {\n"
        + "        \"start\": 600,\n" + "        \"end\": -1,\n" + "        \"percent\": 150\n"
        + "    }\n" + "]";

    
    ArrayList<Rules> rules = new ArrayList<>();
    try {
      JSONParser parser = new JSONParser();
      JSONArray jsonArray = (JSONArray) parser.parse(json);

      for (Object obj : jsonArray) {
        JSONObject jsonObject = (JSONObject) obj;

        int start = Integer.parseInt(jsonObject.get("start").toString());
        int end = Integer.parseInt(jsonObject.get("end").toString());
        float percent = Float.parseFloat(jsonObject.get("percent").toString())/100;

        Rules rule = new Rules(start, end, percent);
        rules.add(rule);
        
        // Do something with the Rules object
        System.out.println(rule.getStart() + " - " + rule.getEnd() + ": " + rule.getPercent());
      }



    } catch (ParseException e) {
      System.out.println("Eror parsing json");
      e.printStackTrace();
    }
    float[] anticipateValues = new float[baseValues.length];

    int cumulative = 0;
    int vi = 0; // value index
    int ri = 0; // rule index

    while (vi < baseValues.length) {
      int anticipateValue = 0;
      cumulative += baseValues[vi];

      if (rules.get(ri).getEnd() > -1) {
        if (cumulative < rules.get(ri).getEnd()) {
          anticipateValue = (int) (baseValues[vi] * rules.get(ri).getPercent());
        } else if (cumulative > rules.get(ri).getEnd()) {
          int under = rules.get(ri).getEnd() - (cumulative - baseValues[vi]);
          anticipateValue = (int) (under * rules.get(ri).getPercent());
          int over = cumulative - rules.get(ri).getEnd();
          ri++;
          anticipateValue += (int) (over * rules.get(ri).getPercent());
        } else { // equal
          anticipateValue = (int) (baseValues[vi] * rules.get(ri).getPercent());
          ri++;
        }
      } else {
        anticipateValue = (int) (baseValues[vi] * rules.get(ri).getPercent());
      }

      anticipateValues[vi] = anticipateValue;
      vi++;
    }

    for (float value : anticipateValues) {
      System.out.println(value);

    }
  }
}
