package spichka.skintotem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SkinTotemClient implements ClientModInitializer {
    private boolean initialized = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!initialized) {
                SimpleTextureLoader.loadTextures();
                initialized = true;
            }
        });
    }
}
