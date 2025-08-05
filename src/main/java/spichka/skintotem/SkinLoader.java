package spichka.skintotem;

import java.net.URL;

import javax.imageio.ImageIO;

import net.minecraft.client.texture.NativeImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkinLoader {
    private static BufferedImage getSkin(String nickname) {
        SkinTotem.LOGGER.info("Nickname: " + nickname);

        String playerID = MojangApi.getId(nickname);
        if (playerID == null) {
            SkinTotem.LOGGER.warn("Player '" + nickname + "' not found");
            return null;
        }

        SkinTotem.LOGGER.info("ID: " + playerID);

        String skinBase64 = MojangApi.getSkinBase64(playerID);
        if (skinBase64 == null) {
            SkinTotem.LOGGER.warn("ID '" + playerID + "' not found");
            return null;
        }

        SkinTotem.LOGGER.info("Skin (Base64): " + skinBase64);

        URL skinUrl = SkinDecoder.getSkinUrl(skinBase64);
        SkinTotem.LOGGER.info("Skin url: " + skinUrl);

        BufferedImage skin = MojangApi.getSkin(skinUrl);
        if (skin == null) {
            SkinTotem.LOGGER.warn("Skin '" + skinUrl + "' not found");
            return null;
        }

        return skin;
    }

    private static void saveTotemImage(BufferedImage image, String nickname) {
        try {
            File output = new File("config/skintotem/" + nickname + ".png");
            output.getParentFile().mkdirs();
            ImageIO.write(image, "PNG", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NativeImage loadTotemImage(String texPath) {
        try {
            File file = new File("config/skintotem/" + texPath.substring(texPath.lastIndexOf("/") + 1) + ".png");
            BufferedImage buffered = ImageIO.read(file);
            NativeImage nativeImage = new NativeImage(buffered.getWidth(), buffered.getHeight(), true);
            for (int x = 0; x < buffered.getWidth(); x++) {
                for (int y = 0; y < buffered.getHeight(); y++) {
                    nativeImage.setColor(x, y, buffered.getRGB(x, y));
                }
            }
            return nativeImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void loadSkin(String nickname) {
        // TODO: do not generate if skintottem already exists
        BufferedImage skin = getSkin(nickname);
        if (skin == null) {
            // TODO: error in chat
            return;
        }
        
        BufferedImage totem = TotemGenerator.generateTotemFromSkin(skin);
        saveTotemImage(totem, nickname);

    }
}
