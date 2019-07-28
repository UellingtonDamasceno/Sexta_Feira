package model.bean;


import java.util.ArrayList;

public class Param {

        private String value;
        private int minValue;
        private int maxValue;
        private double selectedInterval;
        private int up;
        private boolean moreValues;
        
        public Param(String value) {
            this(value, 0, 0, 0, false);
        }

        public Param(String value, int minValue, int maxValue, int up, boolean moreValues) {
            this.value = value;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.up = up;
            this.moreValues = moreValues;
        }

        public double getUp() {
            return this.up;
        }

        public boolean hasMoreParams() {
            return this.moreValues;
        }

        public int getMinValue() {
            return this.minValue;
        }

        public int getMaxValue() {
            return this.maxValue;
        }

        public String getParam() {
            return this.value;
        }

        public void setSelectedInterval(double interval) {
            this.selectedInterval = interval;
        }

        public ArrayList<String> getAllIntervals() {
            ArrayList<String> interval = new ArrayList();
            for (double i = this.minValue; i < this.maxValue; i += this.up) {
                interval.add(String.valueOf(i));
            }
            return interval;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
