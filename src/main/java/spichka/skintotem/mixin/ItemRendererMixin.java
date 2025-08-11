package spichka.skintotem.mixin;

import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import spichka.skintotem.SimpleTextureLoader;
import spichka.skintotem.SkinTotem;
import net.minecraft.client.texture.SpriteAtlasTexture;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    // @Inject(method = "getModel", at = @At("RETURN"), cancellable = true)
    // private void onGetModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
    //     BakedModel originalModel = cir.getReturnValue();

    //     if (stack != null && !stack.isEmpty()
    //         && stack.isOf(Items.TOTEM_OF_UNDYING)
    //         && stack.hasNbt()
    //         && stack.getNbt().contains("username")) {


    //     String username = stack.getNbt().getString("username");
    //     Identifier textureId = SimpleTextureLoader.getTexture(username);
    //     if (textureId == null) {
    //         SkinTotem.LOGGER.error("'" + username + "' not found in cache (getModel)");
    //         return;
    //     }

    //     Sprite sprite = MinecraftClient.getInstance()
    //     .getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
    //     .apply(textureId);

    //     if (sprite == null) {
    //         SkinTotem.LOGGER.error("Sprite for " + username + " is missing or not loaded!");
    //         return;
    //     }

    //     BakedModel customModel = new CustomTotemModel(originalModel, sprite);
    //     cir.setReturnValue(customModel);
    //     }
    // }

    @Shadow @Final
    private MinecraftClient client;

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void onRenderItem(ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack matrices,
                              VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (!stack.isOf(Items.TOTEM_OF_UNDYING)) return;
        if (!stack.hasNbt() || !stack.getNbt().contains("username")) return;

        String username = stack.getNbt().getString("username");
        Identifier texId = SimpleTextureLoader.loadDynamicTexture(username);

        if (texId == null) {
            SkinTotem.LOGGER.error("'" + username + "' not found in cache");
            return;
        }

        matrices.push();
        matrices.scale(1.0f, 1.0f, 1.0f);

        // Рисуем 2D как иконку с нашей текстурой
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(texId));
        ItemRenderer renderer = (ItemRenderer) (Object) this;
        ((ItemRendererAccessor) this).invokeRenderBakedItemModel(model, stack, light, overlay, matrices, consumer);


        matrices.pop();
        ci.cancel();
    }
}
