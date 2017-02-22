package com.ciklumtask.networking;


import java.util.List;

public interface ParsedModel {
    int getPage();

    List<Item> getItems();

    boolean isPageNotFound();
}
