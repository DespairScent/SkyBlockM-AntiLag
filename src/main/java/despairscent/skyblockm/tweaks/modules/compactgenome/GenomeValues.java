package despairscent.skyblockm.tweaks.modules.compactgenome;

public class GenomeValues {

    public enum Temperature implements GenomeValue {
        NORMAL("Лес, луг, болото"),
        WARM("Джунгли, саванна"),
        COLD("Тайга"),
        HELLISH("Адские биомы");

        final String value;

        Temperature(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Humidity implements GenomeValue {
        NORMAL("Лес, луг, тайга"),
        DAMP("Джунгли, болото"),
        ARID("Пустыня, меза, саванна, ад");

        final String value;

        Humidity(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Flowers implements GenomeValue {
        FLOWERS("Обычные цветы"),
        JUNGLE("Лианы, какао-бобы, папоротники"),
        CAVE("Спороцвет, бросянки"),
        NETHER("Адские грибы");

        final String value;

        Flowers(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Speed implements GenomeValue {
        SLOWEST("Самая медленная"),
        SLOWER("Более медленная"),
        SLOW("Медленная"),
        NORMAL("Нормальная"),
        FAST("Быстрая"),
        FASTER("Более быстрая"),
        FASTEST("Наибыстрейшая");

        final String value;

        Speed(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Lifespan implements GenomeValue {
        SHORTEST("Наикратчайшее"),
        SHORTER("Более короткое"),
        SHORT("Короткое"),
        NORMAL("Нормальное"),
        LONG("Длинное"),
        LONGER("Более длинное"),
        LONGEST("Самое длинное");

        final String value;

        Lifespan(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Fertility implements GenomeValue {
        ONE("1 трутень"),
        TWO("2 трутня"),
        THREE("3 трутня");

        final String value;

        Fertility(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Nocturnal implements GenomeValue {
        NO("Нет"),
        YES("Да");

        final String value;

        Nocturnal(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Flyer implements GenomeValue {
        NO("Нет"),
        YES("Да");

        final String value;

        Flyer(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum Effect implements GenomeValue {
        NONE("Нет"),
        POISON("Отравляет сущностей рядом"),
        REGENERATION("Регенерирует игроков рядом"),
        EXPERIENCE("Даёт опыт игрокам рядом"),
        FREEZE("Превращает воду рядом в лёд"),
        FLAME("Поджигает сущностей рядом");

        final String value;

        Effect(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

}
