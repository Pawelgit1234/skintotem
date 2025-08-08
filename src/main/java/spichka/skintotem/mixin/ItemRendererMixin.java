package spichka.skintotem.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
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
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import spichka.skintotem.SimpleTextureLoader;

// @Mixin(ItemRenderer.class)
// public abstract class ItemRendererMixin {
//     @Inject(method = "getModel", at = @At("RETURN"), cancellable = true)
//     private void onGetModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
//         BakedModel originalModel = cir.getReturnValue();
// 
//         if (stack != null && !stack.isEmpty() && stack.isOf(Items.TOTEM_OF_UNDYING) && stack.hasNbt() && stack.getNbt().contains("username")) {
//             CurrentItemStackProvider.set(stack);
//             CustomTotemModel customModel = new CustomTotemModel(originalModel);
//             cir.setReturnValue(customModel);
//         }
//     }
// }

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void onRenderItem(ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack matrices,
                              VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (stack.isOf(Items.TOTEM_OF_UNDYING) && stack.hasNbt() && stack.getNbt().contains("username")) {
            String username = stack.getNbt().getString("username");
            Identifier texture = SimpleTextureLoader.getTexture(username);
            RenderLayer layer = RenderLayer.getEntityCutout(texture);
            VertexConsumer consumer = vertexConsumers.getBuffer(layer);

            ((ItemRendererAccessor) MinecraftClient.getInstance().getItemRenderer())
                .invokeRenderBakedItemModel(model, stack, light, overlay, matrices, consumer);

            ci.cancel();
        }
    }
}
