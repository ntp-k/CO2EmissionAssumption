package co2emissionassumption;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ResourceFileHandler {
    public static String readLineFromResource(String filePath) {
        // String filePath = "testcase1/rules.json";
        String line = "";

        try {
            // Get the input stream of the file
            InputStream inputStream = ResourceFileHandler.class.getClassLoader().getResourceAsStream(filePath);
            assert inputStream != null;
            BufferedReader bufferedInputStream =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            line = bufferedInputStream.readLine();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
}
