package com.example.appchiasecongthucnauan.models;

public class Item {
    public enum Type { USER, RECIPE }

    private Type type;
    private Object data;

    public Item(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public User getUser() {
        return (type == Type.USER) ? (User) data : null;
    }

    public Recipe getRecipe() {
        return (type == Type.RECIPE) ? (Recipe) data : null;
    }
}
