package despairscent.skyblockm.tweaks.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static despairscent.skyblockm.tweaks.ModUtils.config;

@Mixin(Entity.class)
public abstract class ItemFrameUpdateMixin {

    @Inject(
            method = "onDataTrackerUpdate",
            at = @At("HEAD")
    )
    private void reloadInject(List<DataTracker.SerializedEntry<?>> dataEntries, CallbackInfo ci) {
        if (config.qol.storageFramesFix &&
                (Entity) (Object) this instanceof ItemFrameEntity itemFrame &&
                itemFrame.getHorizontalFacing() == Direction.DOWN && !itemFrame.isInvisible()) {
            ItemStack itemStack = itemFrame.getHeldItemStack();
            // is number
            if (itemStack.getItem().equals(Items.NAME_TAG) &&
                    itemStack.hasNbt() && itemStack.getNbt().contains("CustomModelData")) {
                itemFrame.setInvisible(true);
            }
        }
    }

}
