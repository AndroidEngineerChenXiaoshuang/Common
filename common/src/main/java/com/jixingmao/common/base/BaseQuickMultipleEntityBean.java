package com.jixingmao.common.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class BaseQuickMultipleEntityBean implements MultiItemEntity {


    public static final int INPUT = 0;
    public static final int ADD_SHOP_PHOTO = 1;
    public static final int ADD_LOGO = 2;
    public static final int ADD_BUSINESS_LICENSE = 3;
    public static final int ADD_ID_CARD = 4;

    public static final int INPUT_TYPE_NUMBER = 1;
    public static final int INPUT_TYPE_TEXT = 2;

    public static final int VERIFY_TYPE_EMAIL = 1;
    public static final int VERIFY_TYPE_MOBILE = 2;


    private int mItemType;

    public void setItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    @Override
    public int getItemType() {
        return this.mItemType;
    }
}
