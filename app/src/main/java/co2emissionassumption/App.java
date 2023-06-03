package co2emissionassumption;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co2emissionassumption.ResourceFileHandler;
import co2emissionassumption.Rules;

public class App {

  public static int[] parseIntArray(String input) {
    String cleanInput = input.replace("\n", "").replace("[", "").replace("]", "");

    // Split into individual elements
    String[] elements = cleanInput.split(",");

    // Create an int array and parse each element
    int[] intArray = new int[elements.length];
    for (int i = 0; i < elements.length; i++) {
      intArray[i] = Integer.parseInt(elements[i].trim());
    }

    return intArray;
  }

  public static ArrayList<Rules> parseRulesArray(String jsonString) {
    ArrayList<Rules> rules = new ArrayList<>();
    try {
      JSONParser parser = new JSONParser();
      JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

      int index = 1;
      for (Object obj : jsonArray) {
        JSONObject jsonObject = (JSONObject) obj;

        int start = Integer.parseInt(jsonObject.get("start").toString());
        int end = Integer.parseInt(jsonObject.get("end").toString());
        float percent = Float.parseFloat(jsonObject.get("percent").toString()) / 100;

        Rules rule = new Rules(start, end, percent);
        rules.add(rule);

        System.out.printf("Rule %d :\t%d\tto\t%d\tpercent: %.0f\n", index++, rule.getStart(),
            rule.getEnd(), rule.getPercent() * 100);
      }

    } catch (ParseException e) {
      System.out.println("Error parsing json");
      e.printStackTrace();
    }
    return rules;
  }

  public static int[] calculate(int[] baseValues, ArrayList<Rules> rules) {
    int[] anticipateValues = new int[baseValues.length];
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

      anticipateValues[vi] = (int) anticipateValue;
      vi++;
    }
    return anticipateValues;
  }

  public static void main(String[] args) {
    String separator = File.separator;
    String testcaseFolder = "testcase";
    String rulesFilePath = testcaseFolder + separator + "rules.json";
    String baseValuesFilePath = testcaseFolder + separator + "baseValues.json";
    String anticipatedValuesFilePath = testcaseFolder + separator + "anticipatedValue.json";
    ResourceFileHandler fileHandler = new ResourceFileHandler();

    // get rules
    String rulesString = fileHandler.readLineFromResource(rulesFilePath);
    ArrayList<Rules> rules = parseRulesArray(rulesString);

    // get base vaules
    String baseVaulesString = fileHandler.readLineFromResource(baseValuesFilePath);
    int[] baseValues = parseIntArray(baseVaulesString);
    System.out.println("\n\n       base values : " + Arrays.toString(baseValues));

    // calculate anticipated vaules
    int[] anticipateValues = calculate(baseValues, rules);
    System.out.println("anticipated values : " + Arrays.toString(anticipateValues));
  }
}
