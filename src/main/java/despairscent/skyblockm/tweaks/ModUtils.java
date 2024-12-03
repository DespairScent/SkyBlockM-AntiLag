package despairscent.skyblockm.tweaks;

import com.google.common.collect.ImmutableMap;
import despairscent.skyblockm.tweaks.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        if (itemStack.hasNbt() && itemStack.getNbt().getType("CustomModelData") == NbtElement.INT_TYPE) {
            return itemStack.getNbt().getInt("CustomModelData");
        }
        return -1;
    }

    public static String getLiteralNested(Text text, int... path) {
        for (int k : path) {
            if (k >= text.getSiblings().size()) {
                return null;
            }
            text = text.getSiblings().get(k);
        }
        if (text.getContent() instanceof PlainTextContent content) {
            return content.string();
        }
        return null;
    }

    public static <K, V> Map<K, V> generateConvertMap(V[] values, Function<V, K> keyGetter) {
        var builder = ImmutableMap.<K, V>builder();
        for (V value : values) {
            builder.put(keyGetter.apply(value), value);
        }
        return builder.build();
    }

}
