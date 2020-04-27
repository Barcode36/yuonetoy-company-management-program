package com.yuonetoy.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TotalMachineInfo {
    public TotalMachineInfo() {
        name = new SimpleStringProperty(this, "name");
        count = new SimpleIntegerProperty(this, "count");
        totalCount = new SimpleIntegerProperty(this, "totalCount");
    }

    //쿼리 생성을 위한 불리언 매개변수
    public TotalMachineInfo(String name, long count, long totalCount) {
        this();
        setName(name);
        setCount((int) count);
        setTotalCount(((int) totalCount));
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


    public int getTotalCount() {
        return totalCount.get();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount.set(totalCount);
    }

    private StringProperty name;
    private IntegerProperty count;
    private IntegerProperty totalCount;
}
