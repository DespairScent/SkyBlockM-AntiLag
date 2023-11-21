package despairscent.skyblockm.tweaks;

import despairscent.skyblockm.tweaks.config.Config;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger("skyblockm-tweaks");
    public static MinecraftClient client = MinecraftClient.getInstance();

    public static Config config;

    public static Map<Item, Int2ObjectMap<BakedModel>> modelsCache = new HashMap<>();

    public static MutableText translatableSelf(String key, Object... args) {
        return Text.translatable("skyblockm-tweaks." + key, args);
    }

    public static boolean testCustomScreen(Screen screen, String namespace, String code) {
        List<Text> siblings = screen.getTitle().getSiblings();
        if (siblings.isEmpty()) {
            return false;
        }
        Text child = siblings.get(0);
        return child.getContent() instanceof LiteralTextContent literal &&
                child.getStyle().getFont().toString().equals(namespace) &&
                literal.string().equals(code);
    }

}
