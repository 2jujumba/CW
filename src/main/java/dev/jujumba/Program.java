package dev.jujumba;

import dev.jujumba.models.Manufacturer;
import dev.jujumba.models.Souvenir;
import dev.jujumba.strategy.Strategy;
import dev.jujumba.strategy.StrategyImpl;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Program {
    private final List<Souvenir> souvenirs;
    private final List<Manufacturer> manufacturers;
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private final JsonWorker jsonWorker;
    private Strategy strategy;

    public static void main(String[] args) {
        Program program = new Program(StrategyImpl.class);
        program.run();
    }

    @SneakyThrows
    public Program(Class<?> strategyClass) {
        jsonWorker = JsonWorker.getInstance();
        souvenirs = jsonWorker.getSouvenirs();
        manufacturers = jsonWorker.getManufacturers();
        initStrategy(strategyClass);
    }
    @SneakyThrows
    private void initStrategy(Class<?> c) {
        Class[] interfaces = c.getInterfaces();
        boolean implementsStrategy = Arrays.stream(interfaces).anyMatch(anInterface -> anInterface.equals(Strategy.class));
        if (!implementsStrategy) throw new RuntimeException("Your strategy class should implement Strategy interface!");

        Optional<Constructor<?>> optionalConstructor = Arrays.stream(c.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).findFirst();
        strategy = optionalConstructor.isPresent() ? (Strategy) optionalConstructor.get().newInstance() : null;
    }
    @SneakyThrows
    private void run() {
        while (true) {
            String input = getTrimmedLineFromConsole();
            String[] split = input.split(" ");
            String command = split[0];
            switch (command) {
                case "souvenirs"-> processManufacturer(split);
                case "manufacturers"-> processSouvenir(split);
                case "save" -> jsonWorker.save(souvenirs, manufacturers);
            }
        }
    }

    private void processManufacturer(String[] args) {
        switch (args[1]) {
            case "print" -> strategy.print(manufacturers);
            case "add" -> strategy.addManufacturer(manufacturers);
            case "set" -> {
                int index = Integer.parseInt(args[2]);
                strategy.setManufacturer(index, manufacturers, souvenirs);
            }
            case "priceLowerThan" -> {
                double price = Double.parseDouble(args[2]);
                System.out.println(strategy.priceLowerThan(price, souvenirs));
            }
            case "group" -> System.out.println(strategy.group(souvenirs)); //Вывод всех производителей и их сувениров

            case "year" -> { //Выводим всех производителей, что производили сувениры в заданном году
                String name = args[2];
                int year = Integer.parseInt(args[3]);
                System.out.println(strategy.getManufacturersByYear(name, year, souvenirs));
            }
        }
    }

    private void processSouvenir(String[] args) {
        switch (args[1]) {
            case "print" -> strategy.print(souvenirs); //Код повторяется
            case "add" -> strategy.addSouvenir(souvenirs);
            case "set" -> {
                int index = Integer.parseInt(args[2]);
                strategy.setSouvenir(index, souvenirs);
            }
            case "getByCountry" -> {
                String country = args[2];
                strategy.getSouvenirsByCountry(country, souvenirs);
            }
            case "year" -> {
                int year = Integer.parseInt(args[2]);
                System.out.println(strategy.getSouvenirsByYear(year, souvenirs));
            }
        }
    }
    @SneakyThrows
    private String getTrimmedLineFromConsole() {
        return consoleReader.readLine().trim();
    }
}