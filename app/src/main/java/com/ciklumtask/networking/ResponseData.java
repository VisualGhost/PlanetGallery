package com.ciklumtask.networking;


import java.util.Collections;
import java.util.List;

class ResponseData {
    int page;
    List<Item> items;

    int getPage() {
        return page;
    }

    List<Item> getItems() {
        return items != null ? items : Collections.<Item>emptyList();
    }
}
