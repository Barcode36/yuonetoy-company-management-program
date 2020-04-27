package com.yuonetoy.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TotalCoinChangerInfo {
        public TotalCoinChangerInfo() {
            name = new SimpleStringProperty(this, "name");
            count = new SimpleIntegerProperty(this, "count");
            initialQuantity = new SimpleIntegerProperty(this, "initialQuantity");
            totalCount = new SimpleIntegerProperty(this, "totalCount");
            totalInitialQuantity = new SimpleIntegerProperty(this, "totalInitialQuantity");
        }

        public TotalCoinChangerInfo(String name, long totalCount, long totalInitialQuantity) {
            this();
            setName(name);
            setCount((int)totalCount);
            setInitialQuantity((int)totalInitialQuantity);
            setTotalCount((int)totalCount);
            setTotalInitialQuantity((int)totalInitialQuantity);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public int getCount() {
            return count.get();
        }

        public void setCount(int count) {
            this.count.set(count);
        }

        public int getInitialQuantity() {
            return initialQuantity.get();
        }

        public void setInitialQuantity(int initalQuantity) {
            this.initialQuantity.set(initalQuantity);
        }

        public int getTotalCount() {
            return totalCount.get();
        }

        public void setTotalCount(int totalCount) {
            this.totalCount.set(totalCount);
        }

        public int getTotalInitialQuantity() {
            return totalInitialQuantity.get();
        }

        public void setTotalInitialQuantity(int totalInitialQuantity) {
            this.totalInitialQuantity.set(totalInitialQuantity);
        }

        private StringProperty name;
        private IntegerProperty count;
        private IntegerProperty initialQuantity;
        private IntegerProperty totalCount;
        private IntegerProperty totalInitialQuantity;
}
