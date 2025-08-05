package spichka.skintotem;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URL;
import java.util.Base64;

public class SkinDecoder {
    private static String decodeSkinJson(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }

    public static URL getSkinUrl(String base64) {
        try {
            String json = decodeSkinJson(base64);

            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonObject textures = root.getAsJsonObject("textures");
            JsonObject skin = textures.getAsJsonObject("SKIN");

            String url = skin.get("url").getAsString();
            return new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
