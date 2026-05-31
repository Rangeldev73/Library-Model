package br.com.library.models;

public class User {
    private String name;
    private int id;

    public User(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toCsv() {
        return this.name + ";" + this.id;
    }
}
