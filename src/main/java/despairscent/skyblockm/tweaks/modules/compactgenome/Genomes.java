package despairscent.skyblockm.tweaks.modules.compactgenome;

class Genomes {

    static final GenomeType<Temperature> TEMPERATURE = define();
    static final GenomeType<Humidity> HUMIDITY = define();
    static final GenomeType<Flowers> FLOWERS = define();
    static final GenomeType<Speed> SPEED = define();
    static final GenomeType<Lifespan> LIFESPAN = define();
    static final GenomeType<Fertility> FERTILITY = define();
    static final GenomeType<Nocturnal> NOCTURNAL = define();
    static final GenomeType<Flyer> FLYER = define();
    static final GenomeType<Effect> EFFECT = define();

    private static <T> GenomeType<T> define() {
        return new GenomeType<>();
    }

    enum Temperature {
        NORMAL,
        WARM,
        COLD,
        HELLISH
    }

    enum Humidity {
        NORMAL,
        DAMP,
        ARID
    }

    enum Flowers {
        FLOWERS,
        JUNGLE,
        CAVE,
        NETHER
    }

    enum Speed {
        SLOWEST,
        SLOWER,
        SLOW,
        NORMAL,
        FAST,
        FASTER,
        FASTEST
    }

    enum Lifespan {
        SHORTEST,
        SHORTER,
        SHORT,
        NORMAL,
        LONG,
        LONGER,
        LONGEST
    }

    enum Fertility {
        ONE,
        TWO,
        THREE
    }

    enum Nocturnal {
        NO,
        YES
    }

    enum Flyer {
        NO,
        YES
    }

    enum Effect {
        NONE,
        POISON,
        REGENERATION,
        EXPERIENCE,
        FREEZE,
        FLAME
    }

}
