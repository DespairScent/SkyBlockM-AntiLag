package despairscent.skyblockm.tweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import despairscent.skyblockm.tweaks.ModUtils;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;

public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILENAME = "skyblockm-tweaks.json";

    public static final Config DEFAULT = new Config();

    public Modules modules = new Modules();
    public FpsOptimize fpsOptimize = new FpsOptimize();
    public MoreTooltipInfo moreTooltipInfo = new MoreTooltipInfo();
    public RenderItemInside renderItemInside = new RenderItemInside();
    public InputLagFix inputLagFix = new InputLagFix();

    public static class Modules {
        public boolean fpsOptimize = true;
        public boolean storageTargetingFix = true;
        public boolean moreTooltipInfo = true;
        public boolean renderItemInside = true;
        public boolean inputLagFix = true;
    }

    public static class FpsOptimize {
        public boolean modelsCaching = true;
        public boolean noArmorStandCramming = true;
        public boolean noSpectatorArmorStands = true;
    }

    public static class MoreTooltipInfo {
        public boolean storage = true;
        public boolean crystalMemory = true;
    }

    public static class RenderItemInside {
        public RenderItemInsideSub esPattern = new RenderItemInsideSub();
        public RenderItemInsideSub storage = new RenderItemInsideSub();
        public RenderItemInsideSub crystalMemory = new RenderItemInsideSub();
    }

    public static class RenderItemInsideSub {
        public boolean enabled = true;
        public boolean renderAlways = false;
        public boolean drawOriginal = true;
    }

    public static class InputLagFix {
        public boolean recipesSearch = true;
        public boolean esTerminalSearch = true;
    }

    public static Config load() {
        try (FileReader reader = new FileReader(FabricLoader.getInstance().getConfigDir().resolve(FILENAME).toFile())) {
            return GSON.fromJson(reader, Config.class);
        } catch (Exception e) {
            ModUtils.LOGGER.error("Load config error", e);
            return new Config();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(FabricLoader.getInstance().getConfigDir().resolve(FILENAME).toFile())) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            ModUtils.LOGGER.error("Save config error", e);
        }
    }

}
