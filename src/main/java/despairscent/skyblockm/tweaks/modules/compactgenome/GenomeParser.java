package despairscent.skyblockm.tweaks.modules.compactgenome;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

record GenomeParser<T>(GenomeType<T> type, Function<String, T> valueParser) {

    private final static Map<String, GenomeParser<?>> typeParsers = new HashMap<>();

    static void register(String typeStr, GenomeParser<?> typeParser) {
        typeParsers.put(typeStr, typeParser);
    }

    static GenomeParser<?> get(String typeStr) {
        return typeParsers.get(typeStr);
    }
    
    static {
        register("Подходящая температура", new GenomeParser<>(Genomes.TEMPERATURE, s -> switch (s) {
            case "Лес, луг, болото" -> Genomes.Temperature.NORMAL;
            case "Джунгли, саванна" -> Genomes.Temperature.WARM;
            case "Тайга" -> Genomes.Temperature.COLD;
            case "Адские биомы" -> Genomes.Temperature.HELLISH;
            default -> null;
        }));

        register("Подходящая влажность", new GenomeParser<>(Genomes.HUMIDITY, s -> switch (s) {
            case "Лес, луг, тайга" -> Genomes.Humidity.NORMAL;
            case "Джунгли, болото" -> Genomes.Humidity.ARID;
            case "Пустыня, меза, саванна, ад" -> Genomes.Humidity.DAMP;
            default -> null;
        }));

        register("Подходящие цветы", new GenomeParser<>(Genomes.FLOWERS, s -> switch (s) {
            case "Обычные цветы" -> Genomes.Flowers.FLOWERS;
            case "Лианы, какао-бобы, папоротники" -> Genomes.Flowers.JUNGLE;
            case "Спороцвет, бросянки" -> Genomes.Flowers.CAVE;
            case "Адские грибы" -> Genomes.Flowers.NETHER;
            default -> null;
        }));

        register("Скорость работы", new GenomeParser<>(Genomes.SPEED, s -> switch (s) {
            case "Самая медленная" -> Genomes.Speed.SLOWEST;
            case "Более медленная" -> Genomes.Speed.SLOWER;
            case "Медленная" -> Genomes.Speed.SLOW;
            case "Нормальная" -> Genomes.Speed.NORMAL;
            case "Быстрая" -> Genomes.Speed.FAST;
            case "Более быстрая" -> Genomes.Speed.FASTER;
            case "Наибыстрейшая" -> Genomes.Speed.FASTEST;
            default -> null;
        }));

        register("Время жизни", new GenomeParser<>(Genomes.LIFESPAN, s -> switch (s) {
            case "Наикратчайшее" -> Genomes.Lifespan.SHORTEST;
            case "Более короткое" -> Genomes.Lifespan.SHORTER;
            case "Короткое" -> Genomes.Lifespan.SHORT;
            case "Нормальное" -> Genomes.Lifespan.NORMAL;
            case "Длинное" -> Genomes.Lifespan.LONG;
            case "Более длинное" -> Genomes.Lifespan.LONGER;
            case "Самое длинное" -> Genomes.Lifespan.LONGEST;
            default -> null;
        }));

        register("Плодовитость", new GenomeParser<>(Genomes.FERTILITY, s -> switch (s) {
            case "1 трутень" -> Genomes.Fertility.ONE;
            case "2 трутня" -> Genomes.Fertility.TWO;
            case "3 трутня" -> Genomes.Fertility.THREE;
            default -> null;
        }));

        register("Активность в ночное время", new GenomeParser<>(Genomes.NOCTURNAL, s -> switch (s) {
            case "Нет" -> Genomes.Nocturnal.NO;
            case "Да" -> Genomes.Nocturnal.YES;
            default -> null;
        }));

        register("Активность во время дождя", new GenomeParser<>(Genomes.FLYER, s -> switch (s) {
            case "Нет" -> Genomes.Flyer.NO;
            case "Да" -> Genomes.Flyer.YES;
            default -> null;
        }));

        register("Эффект", new GenomeParser<>(Genomes.EFFECT, s -> switch (s) {
            case "Нет" -> Genomes.Effect.NONE;
            case "Отравляет сущностей рядом" -> Genomes.Effect.POISON;
            case "Регенерирует игроков рядом" -> Genomes.Effect.REGENERATION;
            case "Даёт опыт игрокам рядом" -> Genomes.Effect.EXPERIENCE;
            case "Превращает воду рядом в лёд" -> Genomes.Effect.FREEZE;
            case "Поджигает сущностей рядом" -> Genomes.Effect.FLAME;
            default -> null;
        }));
    }

}
