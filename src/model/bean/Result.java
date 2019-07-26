package model.bean;

/**
 *
 * @author Uellington Damasceno
 */
public class Result implements Comparable {

    private final String characterName;
    private final double similarity;

    public Result(String characterName, double similarity) {
        this.characterName = characterName;
        this.similarity = similarity;
    }

    public String getCharacterName() {
        return characterName;
    }

    public double getSimilarity() {
        return similarity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.characterName.hashCode();
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.similarity) ^ (Double.doubleToLongBits(this.similarity) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result) {
            Result other = (Result) obj;
            return (this.hashCode() == other.hashCode());
        }
        return false;
    }

    @Override
    public int compareTo(Object t) {
        if (t instanceof Result) {
            Result other = (Result) t;
            if (this.getSimilarity() < other.getSimilarity()) {
                return -1;
            } else if (this.getSimilarity() > other.getSimilarity()) {
                return 1;
            } else {
                return 0;
            }
        }
        return -99;
    }

    @Override
    public String toString() {
        return characterName.concat("\n"+Double.toString(similarity));
    }

}
