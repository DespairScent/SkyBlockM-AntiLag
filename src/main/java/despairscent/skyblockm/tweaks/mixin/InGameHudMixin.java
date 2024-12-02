package despairscent.skyblockm.tweaks.mixin;

import despairscent.skyblockm.tweaks.ModUtils;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static despairscent.skyblockm.tweaks.ModUtils.config;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Redirect(
            method = "renderHeldItemTooltip",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getName()Lnet/minecraft/text/Text;")
    )
    private Text itemNameModifier1(ItemStack itemStack) {
        return getExtendedName(itemStack);
    }

    @Redirect(
            method = "tick()V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getName()Lnet/minecraft/text/Text;")
    )
    private Text itemNameModifier2(ItemStack itemStack) {
        return getExtendedName(itemStack);
    }

    private static Text getExtendedName(ItemStack itemStack) {
        if (!config.modules.moreTooltipInfo) {
            return itemStack.getName();
        }

        int modelId = ModUtils.getCustomModelId(itemStack);
        if (itemStack.getItem() == Items.BARRIER && modelId >= 1010 && modelId <= 1013) { // storage
            if (!config.moreTooltipInfo.storage) {
                return itemStack.getName();
            }

            LoreComponent lore = itemStack.get(DataComponentTypes.LORE);
            if (lore == null || lore.styledLines().size() != 4) {
                return itemStack.getName();
            }

            try {
                return Text.empty().append(itemStack.getName())
                        .append(Text.literal(" <").styled(style -> style.withColor(Formatting.WHITE).withItalic(false)))
                        .append(lore.styledLines().get(3))
                        .append(Text.literal(">").styled(style -> style.withColor(Formatting.WHITE).withItalic(false)));
            } catch (Exception e) {
            }
        } else if (itemStack.getItem() == Items.IRON_HORSE_ARMOR && modelId == 2001) { // memory crystal
            if (!config.moreTooltipInfo.crystalMemory) {
                return itemStack.getName();
            }

            LoreComponent lore = itemStack.get(DataComponentTypes.LORE);
            if (lore == null || lore.styledLines().size() != 4) {
                return itemStack.getName();
            }

            try {
                return Text.empty().append(itemStack.getName())
                        .append(Text.literal(" <").styled(style -> style.withColor(Formatting.WHITE).withItalic(false)))
                        .append(lore.styledLines().get(0).getSiblings().get(1))
                        .append(Text.literal(">").styled(style -> style.withColor(Formatting.WHITE).withItalic(false)));
            } catch (Exception e) {
            }
        }

        return itemStack.getName();
    }

}
