package spichka.skintotem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MojangApi {
    public static String getId(String nickname) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + nickname);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 204) {
                return null; // Player not found
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            reader.close();

            JsonObject json = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
            return json.get("id").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSkinBase64(String id) {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 204) {
                return null; // id not found
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            reader.close();

            JsonObject json = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
            JsonArray properties = json.getAsJsonArray("properties");

            for (JsonElement element : properties) {
                JsonObject property = element.getAsJsonObject();
                if ("textures".equals(property.get("name").getAsString())) {
                    return property.get("value").getAsString(); // base64
                }
            }

            return null; // textures not found

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage getSkin(URL skinUrl) {
        try {
            return ImageIO.read(skinUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
