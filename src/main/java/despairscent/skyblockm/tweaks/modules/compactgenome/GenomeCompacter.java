package despairscent.skyblockm.tweaks.modules.compactgenome;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class GenomeCompacter {

    private static final Map<GenomeType<?>, String> compactTypes = new HashMap<>();

    private static final Map<GenomeType<?>, Map<Object, String>> compactValues = new HashMap<>();
    
    static void register(GenomeType<?> type, String compactName) {
        compactTypes.put(type, compactName);
    }
    
    static <G> void register(GenomeType<G> type, G value, String compactName) {
        compactValues.computeIfAbsent(type, t -> new HashMap<>()).put(value, compactName);
    }

    static String get(GenomeType<?> type) {
        return compactTypes.getOrDefault(type, String.valueOf(type));
    }

    static String get(GenomeType<?> type, Object value) {
        return compactValues.getOrDefault(type, Collections.emptyMap()).getOrDefault(value, String.valueOf(value));
    }

    static {
        register(Genomes.TEMPERATURE, "Биомы");
        register(Genomes.HUMIDITY, "Влажность");
        register(Genomes.FLOWERS, "Цветы");
        register(Genomes.SPEED, "Скорость");
        register(Genomes.LIFESPAN, "Жизнь");
        register(Genomes.FERTILITY, "Потомство");
        register(Genomes.NOCTURNAL, "В ночь");
        register(Genomes.FLYER, "В дождь");
        register(Genomes.EFFECT, "Эффект");

        register(Genomes.TEMPERATURE, Genomes.Temperature.NORMAL, "обычные");
        register(Genomes.TEMPERATURE, Genomes.Temperature.WARM, "тропики");
        register(Genomes.TEMPERATURE, Genomes.Temperature.COLD, "тайга");
        register(Genomes.TEMPERATURE, Genomes.Temperature.HELLISH, "ад");

        register(Genomes.HUMIDITY, Genomes.Humidity.NORMAL, "обычная");
        register(Genomes.HUMIDITY, Genomes.Humidity.DAMP, "высокая");
        register(Genomes.HUMIDITY, Genomes.Humidity.ARID, "сухая");

        register(Genomes.FLOWERS, Genomes.Flowers.FLOWERS, "обычные");
        register(Genomes.FLOWERS, Genomes.Flowers.CAVE, "пещерные");
        register(Genomes.FLOWERS, Genomes.Flowers.JUNGLE, "тропики");
        register(Genomes.FLOWERS, Genomes.Flowers.NETHER, "адские");

        register(Genomes.SPEED, Genomes.Speed.SLOWEST, "1");
        register(Genomes.SPEED, Genomes.Speed.SLOWER, "2");
        register(Genomes.SPEED, Genomes.Speed.SLOW, "3");
        register(Genomes.SPEED, Genomes.Speed.NORMAL, "4");
        register(Genomes.SPEED, Genomes.Speed.FAST, "5");
        register(Genomes.SPEED, Genomes.Speed.FASTER, "6");
        register(Genomes.SPEED, Genomes.Speed.FASTEST, "7");

        register(Genomes.LIFESPAN, Genomes.Lifespan.SHORTEST, "1");
        register(Genomes.LIFESPAN, Genomes.Lifespan.SHORTER, "2");
        register(Genomes.LIFESPAN, Genomes.Lifespan.SHORT, "3");
        register(Genomes.LIFESPAN, Genomes.Lifespan.NORMAL, "4");
        register(Genomes.LIFESPAN, Genomes.Lifespan.LONG, "5");
        register(Genomes.LIFESPAN, Genomes.Lifespan.LONGER, "6");
        register(Genomes.LIFESPAN, Genomes.Lifespan.LONGEST, "7");

        register(Genomes.FERTILITY, Genomes.Fertility.ONE, "1");
        register(Genomes.FERTILITY, Genomes.Fertility.TWO, "2");
        register(Genomes.FERTILITY, Genomes.Fertility.THREE, "3");

        register(Genomes.NOCTURNAL, Genomes.Nocturnal.NO, "-");
        register(Genomes.NOCTURNAL, Genomes.Nocturnal.YES, "+");

        register(Genomes.FLYER, Genomes.Flyer.NO, "-");
        register(Genomes.FLYER, Genomes.Flyer.YES, "+");

        register(Genomes.EFFECT, Genomes.Effect.NONE, "нет");
        register(Genomes.EFFECT, Genomes.Effect.POISON, "отрава");
        register(Genomes.EFFECT, Genomes.Effect.REGENERATION, "реген");
        register(Genomes.EFFECT, Genomes.Effect.EXPERIENCE, "опыт");
        register(Genomes.EFFECT, Genomes.Effect.FREEZE, "лёд");
        register(Genomes.EFFECT, Genomes.Effect.FLAME, "поджог");
    }

}
