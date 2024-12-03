package despairscent.skyblockm.tweaks.modules.compactgenome;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static despairscent.skyblockm.tweaks.ModUtils.config;
import static despairscent.skyblockm.tweaks.ModUtils.getLiteralNested;

public class CompactGenomeModule {

    private static final Map<GenomeType<?>, String> TYPE_TO_COMPACT_NAME;

    private static final Map<GenomeValue, String> VALUE_TO_COMPACT_NAME;

    // private static final Map<GenomeVariant, String> VARIANT_TO_COLOR;

    static {
        TYPE_TO_COMPACT_NAME = ImmutableMap.<GenomeType<?>, String>builder()
                .put(GenomeTypes.TEMPERATURE, "Биомы")
                .put(GenomeTypes.HUMIDITY, "Влажность")
                .put(GenomeTypes.FLOWERS, "Цветы")
                .put(GenomeTypes.SPEED, "Скорость")
                .put(GenomeTypes.LIFESPAN, "Жизнь")
                .put(GenomeTypes.FERTILITY, "Потомство")
                .put(GenomeTypes.NOCTURNAL, "В ночь")
                .put(GenomeTypes.FLYER, "В дождь")
                .put(GenomeTypes.EFFECT, "Эффект")
                .build();

        VALUE_TO_COMPACT_NAME = ImmutableMap.<GenomeValue, String>builder()
                .put(GenomeValues.Temperature.NORMAL, "обычные")
                .put(GenomeValues.Temperature.WARM, "тропики")
                .put(GenomeValues.Temperature.COLD, "тайга")
                .put(GenomeValues.Temperature.HELLISH, "ад")

                .put(GenomeValues.Humidity.NORMAL, "обычная")
                .put(GenomeValues.Humidity.DAMP, "высокая")
                .put(GenomeValues.Humidity.ARID, "сухая")

                .put(GenomeValues.Flowers.FLOWERS, "обычные")
                .put(GenomeValues.Flowers.CAVE, "пещерные")
                .put(GenomeValues.Flowers.JUNGLE, "тропики")
                .put(GenomeValues.Flowers.NETHER, "адские")

                .put(GenomeValues.Speed.SLOWEST, "1")
                .put(GenomeValues.Speed.SLOWER, "2")
                .put(GenomeValues.Speed.SLOW, "3")
                .put(GenomeValues.Speed.NORMAL, "4")
                .put(GenomeValues.Speed.FAST, "5")
                .put(GenomeValues.Speed.FASTER, "6")
                .put(GenomeValues.Speed.FASTEST, "7")

                // .put(GenomeValues.Speed.SLOWEST, "0.3")
                // .put(GenomeValues.Speed.SLOWER, "0.6")
                // .put(GenomeValues.Speed.SLOW, "0.8")
                // .put(GenomeValues.Speed.NORMAL, "1.0")
                // .put(GenomeValues.Speed.FAST, "1.2")
                // .put(GenomeValues.Speed.FASTER, "1.4")
                // .put(GenomeValues.Speed.FASTEST, "1.7")

                .put(GenomeValues.Lifespan.SHORTEST, "1")
                .put(GenomeValues.Lifespan.SHORTER, "2")
                .put(GenomeValues.Lifespan.SHORT, "3")
                .put(GenomeValues.Lifespan.NORMAL, "4")
                .put(GenomeValues.Lifespan.LONG, "5")
                .put(GenomeValues.Lifespan.LONGER, "6")
                .put(GenomeValues.Lifespan.LONGEST, "7")

                // .put(GenomeValues.Lifespan.SHORTEST, "0.25")
                // .put(GenomeValues.Lifespan.SHORTER, "0.50")
                // .put(GenomeValues.Lifespan.SHORT, "0.75")
                // .put(GenomeValues.Lifespan.NORMAL, "1.00")
                // .put(GenomeValues.Lifespan.LONG, "1.25")
                // .put(GenomeValues.Lifespan.LONGER, "1.50")
                // .put(GenomeValues.Lifespan.LONGEST, "1.75")

                .put(GenomeValues.Fertility.ONE, "1")
                .put(GenomeValues.Fertility.TWO, "2")
                .put(GenomeValues.Fertility.THREE, "3")

                .put(GenomeValues.Nocturnal.NO, "-")
                .put(GenomeValues.Nocturnal.YES, "+")

                .put(GenomeValues.Flyer.NO, "-")
                .put(GenomeValues.Flyer.YES, "+")

                .put(GenomeValues.Effect.NONE, "нет")
                .put(GenomeValues.Effect.POISON, "отрава")
                .put(GenomeValues.Effect.REGENERATION, "реген")
                .put(GenomeValues.Effect.EXPERIENCE, "опыт")
                .put(GenomeValues.Effect.FREEZE, "лёд")
                .put(GenomeValues.Effect.FLAME, "поджог")

                .build();

        // VARIANT_TO_COLOR = Maps.immutableEnumMap(ImmutableMap.<GenomeVariant, String>builder()
        //         .put(GenomeVariant.DOMINANT, Formatting.GREEN.toString())
        //         .put(GenomeVariant.RECESSIVE, Formatting.RED.toString())
        //         .build());
    }

    private static final GenomeType<?>[] FORMAT = new GenomeType[]{
        GenomeTypes.SPEED,
                GenomeTypes.LIFESPAN,
                GenomeTypes.FERTILITY,
                null,
                GenomeTypes.NOCTURNAL,
                GenomeTypes.FLYER,
                null,
                GenomeTypes.EFFECT,
                null,
                GenomeTypes.TEMPERATURE,
                GenomeTypes.HUMIDITY,
                GenomeTypes.FLOWERS
    };

    public static void init() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (!config.modules.compactGenome) {
                return;
            }
            try {
                int start = 0;
                for (; start < lines.size(); start++) {
                    if ("Изученные гены:".equals(getLiteralNested(lines.get(start), 0))) {
                        break;
                    }
                }
                if (start == lines.size()) {
                    return;
                }

                var genomeSet = new GenomeSet();

                int end = start + 1;
                while (end + 3 <= lines.size()) {
                    String name = getLiteralNested(lines.get(end), 0);
                    if (name == null || !name.startsWith("• ")) {
                        break;
                    }
                    name = name.substring(2);

                    GenomeType<?> genomeType = GenomeTypes.parse(name);
                    if (genomeType == null || !parseLines(genomeSet, genomeType, lines, end)) {
                        end = -1;
                        break;
                    }

                    end += 3;
                }

                if (end < start || genomeSet.isEmpty()) {
                    return;
                }
                lines.subList(start, end).clear();

                lines.addAll(start, generateLines(genomeSet, FORMAT));
            } catch (Exception e) {
            }
        });
    }

    private static <V extends GenomeValue> boolean parseLines(GenomeSet genomeSet, GenomeType<V> genomeType, List<Text> lines, int i) {
        V first = genomeType.parseValue(getLiteralNested(lines.get(i + 1), 1).trim());
        V second = genomeType.parseValue(getLiteralNested(lines.get(i + 2), 1).trim());
        if (first == null || second == null) {
            return false;
        }

        GenomeVariant firstVariant = GenomeVariant.parse(getLiteralNested(lines.get(i + 1), 3));
        GenomeVariant secondVariant = GenomeVariant.parse(getLiteralNested(lines.get(i + 2), 3));

        genomeSet.put(genomeType, first, firstVariant, second, secondVariant);
        return true;
    }

    private static List<Text> generateLines(GenomeSet genomeSet, GenomeType<?>[] format) {
        var lines = new ArrayList<Text>();
        Text empty = Text.empty();

        boolean writingSection = false;
        for (GenomeType<?> type : format) {
            if (type == null) {
                if (writingSection) {
                    lines.add(empty);
                    writingSection = false;
                }
                continue;
            }

            if (!genomeSet.contains(type)) {
                continue;
            }
            writingSection = true;

            GenomePair<?> pair = genomeSet.get(type);
            lines.add(Text.literal(Formatting.GOLD + compact(type) + ": " + Formatting.WHITE + compact(pair.first()) +
                    (pair.first() != pair.second() ? Formatting.GRAY + " (" + compact(pair.second()) + Formatting.RESET + Formatting.GRAY + ")" : "")));
        }

        if (!lines.isEmpty() && lines.get(lines.size() - 1) == empty) {
            return lines.subList(0, lines.size() - 1);
        }

        return lines;
    }

    private static String compact(GenomeType<?> type) {
        return TYPE_TO_COMPACT_NAME.getOrDefault(type, type.getName());
    }

    private static String compact(GenomeValue value) {
        return VALUE_TO_COMPACT_NAME.getOrDefault(value, value.getValue());
    }

}
