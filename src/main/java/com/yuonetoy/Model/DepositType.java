package com.yuonetoy.Model;

public class DepositType {
    private int id;

    public DepositType(int type) {
        this.id = type;
        switch (type) {
            case 0:
                name = "통장입금";
                break;
            case 1:
                name = "전액 현금 수금 - 수수료 지급";
                break;
            case 2:
                name = "수금액(잔액) - 현금 수금";
                break;
            case 3:
                name = "총 현금 수금 - 업체로 전액 입금";
                break;
        }
    }

    public int getId() {
        return id;
    }

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
