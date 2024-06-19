package despairscent.skyblockm.tweaks.mixin;

import despairscent.skyblockm.tweaks.ModelOverrideListOptimized;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = JsonUnbakedModel.class)
public class ItemRenderOptimizeMixin {

    @Inject(
            method = "compileOverrides",
            at = @At("RETURN"),
            cancellable = true
    )
    private void compileOverridesInject(Baker baker, JsonUnbakedModel parent, CallbackInfoReturnable<ModelOverrideList> cir) {
        if (cir.getReturnValue() != ModelOverrideList.EMPTY) {
            cir.setReturnValue(new ModelOverrideListOptimized(baker, parent, cir.getReturnValue()));
        }
    }

}
