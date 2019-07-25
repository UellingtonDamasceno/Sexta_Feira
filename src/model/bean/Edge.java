package model.bean;

import weka.core.Instance;

/**
 *
 * @author Uellington Damasceno
 */
public class Edge implements Comparable {

    private final Instance toCompare;
    private final double similarity;

    public Edge(Instance toCompare, double similarity) {
        this.toCompare = toCompare;
        this.similarity = similarity;
    }

    public Instance getToCompare() {
        return toCompare;
    }

    public double getSimilarity() {
        return similarity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.toCompare.hashCode();
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.similarity) ^ (Double.doubleToLongBits(this.similarity) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge other = (Edge) obj;
            return (this.hashCode() == other.hashCode());
        }
        return false;
    }

    @Override
    public int compareTo(Object t) {
        if (t instanceof Edge) {
            Edge other = (Edge) t;
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
        return toCompare.stringValue(0).concat("\n"+Double.toString(similarity));
    }

}