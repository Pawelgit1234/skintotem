package spichka.skintotem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SimpleTextureLoader {
    private static final HashMap<String, Identifier> textures = new HashMap<>();

    public static Identifier getTexture(String username) {
        Identifier texture = textures.get(username);
        if (texture == null) {
            // TODO: throw error in chat
            SkinTotem.LOGGER.info("'"+ username + "' not found in cache");
            return new Identifier("minecraft", "item/totem_of_undying");
        }
        return texture;
    }

    public static void loadTextures() {
        File folder = new File("config/skintotem/");
        if (!folder.exists() || !folder.isDirectory()) return;

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        if (files == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        TextureManager textureManager = client.getTextureManager();

        for (File file : files) {
            String fileName = file.getName();
            String username = fileName.substring(0, fileName.length() - 4); // deletes .png

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                if (bufferedImage == null) continue;

                NativeImage nativeImage = fromBufferedImage(bufferedImage);
                NativeImageBackedTexture texture = new NativeImageBackedTexture(nativeImage);

                Identifier textureId = new Identifier(SkinTotem.MOD_ID, "custom/" + username);
                textureManager.registerTexture(textureId, texture);

                textures.put(username, textureId);
                SkinTotem.LOGGER.info("Textures were cached");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static NativeImage fromBufferedImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        NativeImage nativeImage = new NativeImage(width, height, true);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nativeImage.setColor(x, y, bufferedImage.getRGB(x, y));
            }
        }
        return nativeImage;
    }
}
