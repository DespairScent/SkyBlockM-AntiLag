package despairscent.skyblockm.tweaks.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static despairscent.skyblockm.tweaks.ModUtils.config;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Redirect(method = "isInvisibleTo",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z")
	)
	private boolean isInvisibleToInject(PlayerEntity instance) {
		if (config.general.optimize && config.optimize.spectatorArmorStands &&
				((Object)this) instanceof ArmorStandEntity) {
			return false;
		}
		return instance.isSpectator();
	}

	@Inject(
			method = "onDataTrackerUpdate",
			at = @At("HEAD")
	)
	private void onDataTrackerUpdateInject(List<DataTracker.SerializedEntry<?>> dataEntries, CallbackInfo ci) {
		if (config.qol.storageFramesFix &&
				(Entity) (Object) this instanceof ItemFrameEntity itemFrame &&
				itemFrame.getHorizontalFacing() == Direction.DOWN && !itemFrame.isInvisible() &&
				!(itemFrame.getYaw() == 0 && itemFrame.getPitch() == 90)) {
			itemFrame.setInvisible(true);
		}
	}

	@Inject(
			method = "updateTrackedPositionAndAngles",
			at = @At("HEAD")
	)
	private void updateTrackedPositionAndAnglesInject(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate, CallbackInfo ci) {
		if (config.qol.storageFramesFix &&
				(Entity) (Object) this instanceof ItemFrameEntity itemFrame &&
				itemFrame.getHorizontalFacing() == Direction.DOWN && !itemFrame.isInvisible() &&
				!(yaw == 0 && pitch == 90)) {
			itemFrame.setInvisible(true);
		}
	}

}
