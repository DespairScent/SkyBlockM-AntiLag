package despairscent.skyblockm.antilag;

import com.mojang.brigadier.Command;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ModLoader implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("skyblockm-antilag");

    public static boolean ENABLED = true;

    public static Map<Item, Int2ObjectMap<BakedModel>> modelsCache = new HashMap<>();

    private static MinecraftClient client;

    @Override
    public void onInitializeClient() {
        client = MinecraftClient.getInstance();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("sbmantilag")
                    .executes(ctx -> {
                        doSwitch();
                        return Command.SINGLE_SUCCESS;
                    }));
        });

        KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyblockmantilag.switch",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                "key.skyblockmantilag.category"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (binding.wasPressed()) {
                doSwitch();
            }
        });
    }

    private static void doSwitch() {
        if (client.world == null || client.player == null) {
            return;
        }
        ENABLED = !ENABLED;
        if (ENABLED) {
            modelsCache.clear();
        }
        client.inGameHud.getChatHud().addMessage(Text.literal("SkyBlockM AntiLag: " + (ENABLED ? Formatting.GREEN + "enabled" : Formatting.RED + "disabled")));
    }
}
