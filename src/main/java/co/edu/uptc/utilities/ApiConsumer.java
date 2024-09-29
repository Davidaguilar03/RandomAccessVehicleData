package co.edu.uptc.utilities;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class ApiConsumer {
    private PropertiesService propertiesService = new PropertiesService();
    public void consumeApi() throws URISyntaxException {
        String apiUrl = propertiesService.getKeyValue("ApiUrlPath"); 
        try {
          
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
   
            try (FileWriter file = new FileWriter(propertiesService.getKeyValue("DataPath"))) {
                file.write(jsonResponse.toString(4)); 
            } catch (IOException e) {
                System.out.println("Error saving the data: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.out.println("Error saving the data: " + e.getMessage());
        }
    }


}
