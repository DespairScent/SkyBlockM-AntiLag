package despairscent.skyblockm.tweaks.modules.compactgenome;

enum GenomeVariant {
    DOMINANT,
    RECESSIVE;

    static GenomeVariant parse(String name) {
        if (name == null) {
            return null;
        }
        return switch (name) {
            case "Доминантный" -> DOMINANT;
            case "Рецессивный" -> RECESSIVE;
            default -> null;
        };
    }
}
