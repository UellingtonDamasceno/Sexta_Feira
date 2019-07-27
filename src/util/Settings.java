package util;

/**
 *
 * @author Uellington Damasceno
 */
public class Settings {

    public enum Algorithms {

        JACCARD_COEFFICIENT,
        SMC,
        DICE;
    }

    public enum PredictionClasses {

        FLIGHT(19),
        SUPER_STRENGTH(28),
        ACCELERATED_HEALING(12),
        ALIGNMENT(9),
        INVISIBILITY(145);

        private final int value;

        PredictionClasses(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum Icons {

        IRON_MAN("ironman.png"),
        LIGHTNING_BOLT("lightning_bolt.png"),
        MODULE("module.png"),
        SUPER_HERO_MALE("super_hero_male.png"),
        SUPER_HERO_FEMALE("super_hero_female.png");

        private final String value;
        private final String PATH;

        Icons(String value) {
            this.value = value;
            this.PATH = "res/images/";
        }

        public String getValue() {
            return this.PATH.concat(value);
        }
    }

    public enum StandardValues {

        MINIMUM_ALLOWED_VALUE(0),
        NUMBER_ATTRIBUTE_HERO(11),
        NUMBER_ATTRIBUTE_SUPER_POWER(168);

        private final int value;

        StandardValues(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum FileSettings {

        FALSE("0"),
        TRUE("1"),
        CSV_DIVISOR(","),
        EMPATY(",,"),
        INVALID_CHARACTER("'"),
        NOT_A_NUMBER("NaN"),
        STANDARD_CHARACTER("-");

        private final String value;

        FileSettings(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum DatasetId {

        HEROES,
        SUPER_POWER,
        SUPER_POWER_MERGE_HERO;
    }

    public enum Path {

        DATASET_FILE_ARFF("processed\\dataset.arff"),
        DATASET_FILE_CSV("processed\\dataset.csv"),
        HEROES_FILE_ARFF("pre-processed\\heroes.arff"),
        HEROES_CSV_ORIGINAL("original\\herois.csv"),
        HEROES_CSV_PRE_PROCESSED("pre-processed\\heroes.csv"),
        SUPER_POWER_FILE_ARFF("pre-processed\\superpowers.arff"),
        SUPER_POWER_CSV_ORIGINAL("original\\superpoderes.csv"),
        SUPER_POWER_CSV_PRE_PROCESSED("pre-processed\\superpoderes.csv");

        private final String value;
        private final String PATH;

        Path(String value) {
            this.value = value;
            this.PATH = "res\\dataset\\";
        }

        public String getValue() {
            return PATH.concat(value);
        }
    }

    public enum Scenes {

        DASHBOARD("/view/Dashboard.fxml", true);

        private final String value;
        private final boolean cacheable;

        Scenes(String value, boolean cacheable) {
            this.value = value;
            this.cacheable = cacheable;
        }

        public String getValue() {
            return this.value;
        }

        public boolean isCacheable() {
            return this.cacheable;
        }
    }
}
