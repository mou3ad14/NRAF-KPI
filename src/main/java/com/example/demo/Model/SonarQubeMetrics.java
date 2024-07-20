package com.example.demo.Model;

import java.util.List;

public class SonarQubeMetrics {
    private Component component;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public static class Component {
        private List<Measure> measures;

        public List<Measure> getMeasures() {
            return measures;
        }

        public void setMeasures(List<Measure> measures) {
            this.measures = measures;
        }
    }

    public static class Measure {
        private String metric;
        private String value;

        public String getMetric() {
            return metric;
        }

        public void setMetric(String metric) {
            this.metric = metric;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
