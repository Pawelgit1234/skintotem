package spichka.skintotem.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import spichka.skintotem.SimpleTextureLoader;
import spichka.skintotem.SkinLoader;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {
    @Inject(method = "onTakeOutput", at = @At("HEAD"))
    private void onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity) {
            if (stack.getItem() == Items.TOTEM_OF_UNDYING &&
                !stack.getName().getString().equals(stack.getItem().getName().getString())) {
                SkinLoader.loadSkin(stack.getName().getString());
                SimpleTextureLoader.loadTextures();
            }
        }
    }
}
