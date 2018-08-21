package org.yufan.common;

public enum ItemStatus {
    NORMAL,//正常
    INSTOCK,//下架
    DELETE;


    public static int getValue(ItemStatus itemStatus) {
        switch (itemStatus){
            case NORMAL:return 1;
            case INSTOCK:return 2;
            case DELETE:return 3;
            default: return 2;
        }

    }
}
