
package despairscent.skyblockm.tweaks.modules.compactgenome;

public record GenomePair<T extends GenomeValue>(
        T first, GenomeVariant firstVariant,
        T second, GenomeVariant secondVariant
) { }
