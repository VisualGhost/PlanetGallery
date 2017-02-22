package com.ciklumtask.loader;


import com.ciklumtask.networking.Item;
import com.ciklumtask.networking.ParsedModel;
import com.ciklumtask.networking.ParsedModelImpl;
import com.ciklumtask.util.DebugLogger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class CacheData {

    private static final String TAG = CacheData.class.getSimpleName();

    private LinkedHashMap<Integer, ParsedModel> mHashMap;

    CacheData() {
        mHashMap = new LinkedHashMap<>();
    }

    public void setData(ParsedModel model) {
        if (!mHashMap.containsKey(model.getPage())) {
            mHashMap.put(model.getPage(), model);
        }

        DebugLogger.d(TAG, "set data: " + model.getPage() + ", map size: " + mHashMap.size());
    }

    ParsedModel getParsedModel() {

        List<Item> items = new ArrayList<>();

        int page = 0;
        boolean isPageNotFound = false;

        for (Map.Entry<Integer, ParsedModel> entry : mHashMap.entrySet()) {
            ParsedModel parsedModel = entry.getValue();
            items.addAll(parsedModel.getItems());
            page = parsedModel.getPage();
            isPageNotFound = parsedModel.isPageNotFound();
        }

        ParsedModel parsedModel = new ParsedModelImpl(page, items, isPageNotFound);
        DebugLogger.d(TAG, "getParsedModel: " + parsedModel.getItems().size());
        return parsedModel;
    }

    boolean isEmpty() {
        return mHashMap.isEmpty();
    }
}
