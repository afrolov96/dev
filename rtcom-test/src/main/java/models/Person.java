package models;


import java.util.ArrayList;
import java.util.HashMap;

public class Person {
    private int id;
    private String name;
    private String patronymic;
    private String surname;
    private HashMap<String, Car> carsMap = new HashMap<String, Car>();

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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public HashMap<String, Car> getCarsMap() {
        return carsMap;
    }

    public void setCarsMap(HashMap<String, Car> carsMap) {
        this.carsMap = carsMap;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", surname='" + surname + '\'' +
                ", carsMap=" + carsMap +
                '}';
    }
}
