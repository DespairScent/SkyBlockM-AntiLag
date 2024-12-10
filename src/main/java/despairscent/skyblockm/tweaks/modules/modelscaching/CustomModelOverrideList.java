package despairscent.skyblockm.tweaks.modules.modelscaching;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;
import java.util.TreeMap;

import static despairscent.skyblockm.tweaks.ModUtils.config;

public class CustomModelOverrideList extends ModelOverrideList {

    private static final Identifier TYPE_CUSTOM_MODEL = Identifier.of("minecraft", "custom_model_data");

    private final ModelOverrideList original;

    private final TreeMap<Integer, BakedModel> cache;

    private final BakedModel fallbackModel;

    public CustomModelOverrideList(Baker baker, JsonUnbakedModel parent, ModelOverrideList original) {
        super(baker, parent, Collections.emptyList());
        this.original = original;

        if (original.conditionTypes.length != 1 || !original.conditionTypes[0].equals(TYPE_CUSTOM_MODEL)) {
            this.cache = null;
            this.fallbackModel = null;
            return;
        }

        this.cache = new TreeMap<>();

        Integer modelIdPrev = null;
        // Список является перевернутым в отношении .json
        for (var override : original.overrides) {
            // Предмет автоматически попадает под условия, прерывая цепочку
            if (override.conditions.length == 0) {
                this.fallbackModel = override.model;
                return;
            }

            int modelId = MathHelper.ceil(override.conditions[0].threshold);
            // Игнорируем ID вне порядка
            if (modelIdPrev == null || modelId < modelIdPrev) {
                this.cache.put(modelId, override.model);
                modelIdPrev = modelId;
            }
        }

        this.fallbackModel = null;
    }

    public boolean hasCache() {
        return this.cache != null;
    }

    @Override
    public BakedModel apply(BakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity, int seed) {
        if (this.cache != null && config.modules.fpsOptimize && config.fpsOptimize.modelsCaching) {
            if (stack.hasNbt() && stack.getNbt().contains("CustomModelData", NbtElement.NUMBER_TYPE)) {
                var entry = this.cache.floorEntry(stack.getNbt().getInt("CustomModelData"));
                if (entry != null) {
                    if (entry.getValue() == null) {
                        return model;
                    }
                    return entry.getValue();
                }
            }
            if (this.fallbackModel != null) {
                return this.fallbackModel;
            }
            return model;
        }
        return this.original.apply(model, stack, world, entity, seed);
    }

}
