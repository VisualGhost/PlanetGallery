package com.ciklumtask.networking;

import java.util.Collections;
import java.util.List;

public class ParsedModelImpl implements ParsedModel {

    private static final int INVALID_PAGE_NUMBER = -1;

    private boolean mIsPageNotFound;
    private int mPage;
    private List<Item> mItemList;

    ParsedModelImpl(ResponseData responseData) {
        mIsPageNotFound = false;
        if (responseData != null) {
            mPage = responseData.getPage();
            mItemList = responseData.getItems();
        } else {
            mPage = INVALID_PAGE_NUMBER;
            mItemList = Collections.emptyList();
        }
    }

    ParsedModelImpl(boolean isPageNotFound) {
        mIsPageNotFound = isPageNotFound;
        mPage = INVALID_PAGE_NUMBER;
        mItemList = Collections.emptyList();
    }

    public ParsedModelImpl(int page, List<Item> itemList, boolean isPageNotFound) {
        mPage = page;
        mItemList = itemList;
        mIsPageNotFound = isPageNotFound;
    }

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public List<Item> getItems() {
        return mItemList;
    }

    @Override
    public boolean isPageNotFound() {
        return mIsPageNotFound;
    }
}
