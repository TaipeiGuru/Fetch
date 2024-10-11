package com.example.fetch;

public class Item {
    private int id;
    private int listId;
    private String name;

    public boolean nameIsValid() {
        return name != null && !name.isEmpty();
    }

    public int getListId() {
        return listId;
    }

    public int getNameNumber() {
        return Integer.parseInt(name.substring(5));
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
