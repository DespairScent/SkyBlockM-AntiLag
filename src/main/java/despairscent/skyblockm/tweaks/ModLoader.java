package despairscent.skyblockm.tweaks;

import despairscent.skyblockm.tweaks.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static despairscent.skyblockm.tweaks.ModUtils.*;

public class ModLoader implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        config = Config.load();
        config.save();

        KeyBinding keybindingSwitchOptimize = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "skyblockm-tweaks.keys.optimize",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                "skyblockm-tweaks.keys"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (keybindingSwitchOptimize.wasPressed()) {
                doSwitch();
            }
        });
    }

    private static void doSwitch() {
        if (client.world == null || client.player == null) {
            return;
        }
        config.modules.fpsOptimize = !config.modules.fpsOptimize;
        client.inGameHud.getChatHud().addMessage(i18n("message.optimizeSwitch").append(
                config.modules.fpsOptimize ?
                        Text.literal("on").styled(style -> style.withColor(Formatting.GREEN)) :
                        Text.literal("off").styled(style -> style.withColor(Formatting.RED))));
    }
}
