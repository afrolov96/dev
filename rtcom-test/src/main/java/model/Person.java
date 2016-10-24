package model;

/**
 * Created by Андрей on 04.10.2016.
 */
public class Person {
    private Integer id;
    private String name;
    private String patronymic;
    private String surname;
    private String city;
    private String car;
    private String car_num;
    private String car_color;
    private String car_class;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    public String toString(){
        return getSurname() + " " + getName() + " " + getPatronymic() + ", " + getCity() + ", " + getCar() + ", " + getCar_num() + ", " + getCar_color() + ", " + getCar_class();
    }
}
