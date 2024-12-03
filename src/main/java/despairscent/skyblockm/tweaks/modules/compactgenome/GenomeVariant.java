package despairscent.skyblockm.tweaks.modules.compactgenome;

import despairscent.skyblockm.tweaks.ModUtils;

import java.util.Map;

public enum GenomeVariant {
    DOMINANT("Доминантный"),
    RECESSIVE("Рецессивный");

    private static final Map<String, GenomeVariant> NAME_TO_VARIANT_MAP = ModUtils.generateConvertMap(values(), GenomeVariant::getName);

    private final String name;

    GenomeVariant(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static GenomeVariant parse(String name) {
        return NAME_TO_VARIANT_MAP.get(name);
    }
}
