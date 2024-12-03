package despairscent.skyblockm.tweaks.modules.compactgenome;

import java.util.HashMap;
import java.util.Map;

public class GenomeSet {

    private final Map<GenomeType<?>, GenomePair<?>> map = new HashMap<>();

    public boolean contains(GenomeType<?> type) {
        return this.map.containsKey(type);
    }

    public <V extends GenomeValue, T extends GenomeType<V>> GenomePair<V> get(T type) {
        return (GenomePair<V>) this.map.get(type);
    }

    public <V extends GenomeValue, T extends GenomeType<V>> void put(T type, V first, GenomeVariant firstVariant, V second, GenomeVariant secondVariant) {
        this.map.put(type, new GenomePair<>(first, firstVariant, second, secondVariant));
    }

    public <V extends GenomeValue, T extends GenomeType<V>> void put(T type, GenomePair<V> pair) {
        this.map.put(type, pair);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

}
