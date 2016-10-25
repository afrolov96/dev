package models;


import java.util.HashMap;

public class City {
    private int id;
    private String name;
    private HashMap<String, Person> personsMap = new HashMap<String, Person>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Person> getPersonsMap() {
        return personsMap;
    }

    public void setPersonsMap(HashMap<String, Person> personsMap) {
        this.personsMap = personsMap;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personsMap=" + personsMap +
                '}';
    }
}
