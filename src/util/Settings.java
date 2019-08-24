package util;

import java.io.Serializable;

/**
 *
 * @author Uellington Damasceno
 */
public class Settings implements Serializable {
    
    public enum Algorithms implements Serializable {
        JACCARD_COEFFICIENT,
        SMC,
        DICE;
    }

    public enum PredictionClasses implements Serializable {

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

    public enum Icons implements Serializable{

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

    public enum StandardValues implements Serializable{

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

    public enum FileSettings implements Serializable{

        FALSE("0"),
        TRUE("1"),
        CSV_DIVISOR(","),
        EMPATY(",,"),
        NINE_NINE("-99"),
        INVALID_CHARACTER("'"),
        NOT_A_NUMBER("NaN"),
        STANDARD_CHARACTER("unknown");

        private final String value;

        FileSettings(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Dataset implements Serializable{
        HEROES,
        SUPER_POWER,
        SUPER_POWER_MERGE_HERO;
    }

    public enum Path implements Serializable{

        DATASET_FILE_ARFF("res\\dataset\\processed\\dataset.arff"),
        DATASET_FILE_CSV("res\\dataset\\processed\\dataset.csv"),
        HEROES_FILE_ARFF("res\\dataset\\pre-processed\\heroes.arff"),
        HEROES_CSV_ORIGINAL("res\\dataset\\original\\herois.csv"),
        HEROES_CSV_PRE_PROCESSED("res\\dataset\\pre-processed\\heroes.csv"),
        SETTINGS_FILE("res\\settings.txt"),
        SUPER_POWER_FILE_ARFF("res\\dataset\\pre-processed\\superpowers.arff"),
        SUPER_POWER_CSV_ORIGINAL("res\\dataset\\original\\superpoderes.csv"),
        SUPER_POWER_CSV_PRE_PROCESSED("res\\dataset\\pre-processed\\superpoderes.csv");
        
        private final String value;

        Path(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Scenes implements Serializable{

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
