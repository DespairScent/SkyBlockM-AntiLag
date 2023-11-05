package despairscent.skyblockm.tweaks.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static despairscent.skyblockm.tweaks.ModUtils.config;
import static despairscent.skyblockm.tweaks.ModUtils.testCustomScreen;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    @Unique
    boolean recipeViewerStarted = false;

    @Inject(
            method = "onSlotUpdate",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onSlotUpdateInject(ScreenHandler handler, int slotId, ItemStack stack, CallbackInfo ci) {
        if (config.qol.recipesSearchInputLagFix &&
                testCustomScreen(((AnvilScreen) (Object) this).getTitle(), "recipeviewer:interfaces", "\u0002") &&
                slotId == 0 && !stack.isEmpty() && !stack.getName().getString().isEmpty()) {
            if (this.recipeViewerStarted) {
                ci.cancel();
            } else {
                this.recipeViewerStarted = true;
            }
        }
    }

}
