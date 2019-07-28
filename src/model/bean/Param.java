package model.bean;


import java.util.ArrayList;

public class Param {

        private String value;
        private double minValue;
        private double maxValue;
        private double selectedInterval;
        private double up;

        public Param(String value) {
            this(value, 0, 0, 0);
        }

        public Param(String value, double minValue, double maxValue, double up) {
            this.value = value;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.up = up;
        }

        public double getUp() {
            return this.up;
        }

        public boolean hasMoreParams() {
            return (minValue + maxValue) != 0;
        }

        public double getMinValue() {
            return this.minValue;
        }

        public double getMaxValue() {
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
