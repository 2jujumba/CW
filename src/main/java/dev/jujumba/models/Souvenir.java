package dev.jujumba.models;

import dev.jujumba.JsonWorker;
import lombok.Data;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Data
@Getter
public class Souvenir implements Serializable {
    private String name;
    private Manufacturer manufacturer;
    private final LocalDate dateOfManufacture;
    private double price;
    public Souvenir(String name, Manufacturer manufacturer, double price) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.dateOfManufacture = LocalDate.now();
        this.price = price;
    }

    public static Souvenir getSouvenir() {
        List<Manufacturer> manufacturers = JsonWorker.getInstance().getManufacturers();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите имя сувенира:..");
            String name = r.readLine();

            System.out.println("Выберите производителя сувенира:..");
            IntStream
                    .range(0, manufacturers.size())
                    .mapToObj(i -> i + ": " + manufacturers.get(i).getName())
                    .forEach(System.out::println);
            int index = Integer.parseInt(r.readLine());
            Manufacturer manufacturer = manufacturers.get(index);

            System.out.println("Введите цену сувенира:..");
            double price = Double.parseDouble(r.readLine());

            return new Souvenir(name, manufacturer,price);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
