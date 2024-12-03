package despairscent.skyblockm.tweaks.modules.compactgenome;

import java.util.HashMap;
import java.util.Map;

public class GenomeTypes {

    private static final Map<String, GenomeType<?>> NAME_TO_TYPE_MAP = new HashMap<>();

    public static final GenomeType<GenomeValues.Temperature> TEMPERATURE = register("Подходящая температура", GenomeValues.Temperature.values());
    public static final GenomeType<GenomeValues.Humidity> HUMIDITY = register("Подходящая влажность", GenomeValues.Humidity.values());
    public static final GenomeType<GenomeValues.Flowers> FLOWERS = register("Подходящие цветы", GenomeValues.Flowers.values());
    public static final GenomeType<GenomeValues.Speed> SPEED = register("Скорость работы", GenomeValues.Speed.values());
    public static final GenomeType<GenomeValues.Lifespan> LIFESPAN = register("Время жизни", GenomeValues.Lifespan.values());
    public static final GenomeType<GenomeValues.Fertility> FERTILITY = register("Плодовитость", GenomeValues.Fertility.values());
    public static final GenomeType<GenomeValues.Nocturnal> NOCTURNAL = register("Активность в ночное время", GenomeValues.Nocturnal.values());
    public static final GenomeType<GenomeValues.Flyer> FLYER = register("Активность во время дождя", GenomeValues.Flyer.values());
    public static final GenomeType<GenomeValues.Effect> EFFECT = register("Эффект", GenomeValues.Effect.values());

    private static <V extends GenomeValue> GenomeType<V> register(String name, V[] values) {
        var genomeType = new GenomeType<>(name, values);
        NAME_TO_TYPE_MAP.put(name, genomeType);
        return genomeType;
    }

    public static GenomeType<? extends GenomeValue> parse(String name) {
        return NAME_TO_TYPE_MAP.get(name);
    }

}
