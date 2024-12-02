package despairscent.skyblockm.tweaks;

import despairscent.skyblockm.tweaks.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ModUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger("skyblockm-tweaks");
    public static MinecraftClient client = MinecraftClient.getInstance();

    public static Config config;

    public static MutableText i18n(String key, Object... args) {
        return Text.translatable("skyblockm-tweaks." + key, args);
    }

    public static boolean testCustomScreen(Screen screen, String namespace, String code) {
        List<Text> siblings = screen.getTitle().getSiblings();
        if (siblings.isEmpty()) {
            return false;
        }
        Text child = siblings.get(0);
        return child.getContent() instanceof PlainTextContent plainText &&
                child.getStyle().getFont().toString().equals(namespace) &&
                plainText.string().equals(code);
    }

    public static int getCustomModelId(ItemStack itemStack) {
        CustomModelDataComponent valueHolder;
        if ((valueHolder = itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA)) != null) {
            return valueHolder.value();
        }
        return -1;
    }

}
