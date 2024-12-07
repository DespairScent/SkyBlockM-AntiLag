package despairscent.skyblockm.tweaks.modules.compactgenome;

import java.util.HashMap;
import java.util.Map;

class GenomeSet {

    private final Map<GenomeType<?>, GenomePair<?>> map = new HashMap<>();

    boolean contains(GenomeType<?> type) {
        return this.map.containsKey(type);
    }

    <G, T extends GenomeType<G>> GenomePair<G> get(T type) {
        return (GenomePair<G>) this.map.get(type);
    }

    <G, T extends GenomeType<G>> void put(T type, G first, GenomeVariant firstVariant, G second, GenomeVariant secondVariant) {
        this.put(type, new GenomePair<>(first, firstVariant, second, secondVariant));
    }

    <G, T extends GenomeType<G>> void put(T type, GenomePair<G> pair) {
        this.map.put(type, pair);
    }

    boolean isEmpty() {
        return this.map.isEmpty();
    }

}
