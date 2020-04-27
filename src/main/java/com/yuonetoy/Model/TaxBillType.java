package com.yuonetoy.Model;

public class TaxBillType {
        public TaxBillType(int type) {
            this.id = type;
            switch (type) {
                case 0:
                    name = "발행";
                    break;
                case 1:
                    name = "역발행";
                    break;
                case 2:
                    name = "수수료 역발행";
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        int id;
        String name;

        @Override
        public String toString() {
            return getName();
        }
}
