package dev.jujumba.models;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

@Data
public class Manufacturer implements Serializable {
    private final String name;
    private final String country;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public static Manufacturer getManufacturer() {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите имя производителя:..");
            String name = r.readLine();
            System.out.println("Введите страну производителя:..");
            String country = r.readLine();
            return new Manufacturer(name, country);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
