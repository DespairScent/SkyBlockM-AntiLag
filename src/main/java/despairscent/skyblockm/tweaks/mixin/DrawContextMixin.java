package despairscent.skyblockm.tweaks.mixin;

import despairscent.skyblockm.tweaks.ModUtils;
import despairscent.skyblockm.tweaks.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static despairscent.skyblockm.tweaks.ModUtils.config;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Final
    @Shadow
    private MinecraftClient client;

    @Final
    @Shadow
    private MatrixStack matrices;

    @Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"))
    private void drawItemInject(LivingEntity entity, World world, ItemStack itemStack, int x, int y, int seed, int z, CallbackInfo ci) {
        if (!config.modules.renderItemInside || !itemStack.hasNbt()) {
            return;
        }

        int modelId = ModUtils.getCustomModelId(itemStack);

        ItemStack itemInside;
        boolean drawOriginal;
        if (itemStack.getItem() == Items.PAPER && modelId == 7301) {
            if (!itemStack.getNbt().contains("ElectricStorage.RecipeResults") ||
                    !testRender(config.renderItemInside.esPattern)) {
                return;
            }
            drawOriginal = config.renderItemInside.esPattern.drawOriginal;
            itemInside = ItemStack.fromNbt(
                    itemStack.getNbt().getList("ElectricStorage.RecipeResults", NbtElement.COMPOUND_TYPE)
                            .getCompound(0));
        } else if (itemStack.getItem() == Items.BARRIER && modelId >= 1010 && modelId <= 1013) {
            if (!itemStack.getNbt().contains("ItemStack") ||
                    !testRender(config.renderItemInside.storage)) {
                return;
            }
            drawOriginal = config.renderItemInside.storage.drawOriginal;
            itemInside = ItemStack.fromNbt(itemStack.getNbt().getCompound("ItemStack"));
        } else {
            return;
        }

        BakedModel itemInsideModel = this.client.getItemRenderer().getModel(itemInside, world, entity, seed);

        if (itemInsideModel.isSideLit()) {
            DiffuseLighting.enableGuiDepthLighting();
        } else {
            DiffuseLighting.disableGuiDepthLighting();
        }

        this.client.getItemRenderer().renderItem(itemInside, ModelTransformationMode.GUI, false, this.matrices, ((DrawContext) (Object) this).getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, itemInsideModel);
        ((DrawContext) (Object) this).draw();

        if (drawOriginal) {
            this.matrices.translate(0.25f, -0.25f, 1f);
            this.matrices.scale(0.5f, 0.5f, 1f);

            if (this.client.getItemRenderer().getModel(itemStack, world, entity, seed).isSideLit()) {
                DiffuseLighting.enableGuiDepthLighting();
            } else {
                DiffuseLighting.disableGuiDepthLighting();
            }
        } else {
            this.matrices.scale(0, 0, 0);
        }
    }

    private static boolean testRender(Config.RenderItemInsideSub subConfig) {
        return subConfig.enabled &&
                (subConfig.renderAlways ||
                        (MinecraftClient.getInstance().currentScreen != null && Screen.hasShiftDown()));
    }

}
