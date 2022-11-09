package br.com.uniq.database.dbos;

import java.util.Objects;

public class Patient implements Cloneable {
    private String cpfPatient;
    private String name;
    private int age;
    private String password;

    public void setAge(int age) {
        this.age = age;
    }

    public void setCpfPatient(String cpfPatient) {
        this.cpfPatient = cpfPatient;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public String getCpfPatient() {
        return cpfPatient;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public Patient(String name, String cpfPatient, int age, String password) {
        this.name = name;
        this.cpfPatient = cpfPatient;
        this.age = age;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return Objects.hash(cpfPatient, name, age);
    }


    @Override
    public String toString() {
        return "Patient{" +
                "cpfPatient='" + cpfPatient + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}

