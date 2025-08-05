package spichka.skintotem;

import java.awt.image.BufferedImage;

public class TotemGenerator {
    public static BufferedImage generateTotemFromSkin(BufferedImage skin) {
        BufferedImage totem = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int rgb = skin.getRGB(x, y); 
                totem.setRGB(x, y, rgb);     
            }
        }
        return totem;
    }
}
