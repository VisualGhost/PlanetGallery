package com.ciklumtask.networking;


public class Item {

    private static final String ERROR = "error";

    String name;
    String image_url;
    String description;

    public String getName() {
        return name != null ? name : ERROR;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description != null ? description : ERROR;
    }
}

