package dev.jujumba.strategy;

import dev.jujumba.models.Manufacturer;
import dev.jujumba.models.Souvenir;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jujumba
 */
public class StrategyImpl implements Strategy {
    @Override
    public void print(List list) {
        list.forEach(System.out::println);
    }

    @Override
    public void addManufacturer(List<Manufacturer> list) {
        list.add(Manufacturer.getManufacturer());
    }

    @Override
    public void addSouvenir(List<Souvenir> list) {
        list.add(Souvenir.getSouvenir());
    }

    @Override
    public void setManufacturer(int index, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) {
        Manufacturer manufacturer = Manufacturer.getManufacturer();
        souvenirs.stream()
                .filter(s -> s.getManufacturer().equals(manufacturer))
                .forEach(s -> s.setManufacturer(manufacturer)); //Меняем всем сувенирам этого производителя на нового производителя
        manufacturers.set(index, manufacturer);
    }

    @Override
    public void setSouvenir(int index, List<Souvenir> souvenirs) {
        Souvenir souvenir = Souvenir.getSouvenir();
        souvenirs.set(index, souvenir);
    }

    @Override
    public void deleteManufacturerAndHisSouvenirs(Manufacturer manufacturer, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) {
        souvenirs.removeIf(souvenir -> souvenir.getManufacturer().equals(manufacturer));
        manufacturers.remove(manufacturer);
    }

    @Override
    public List<Manufacturer> priceLowerThan(double price, List<Souvenir> souvenirs) {
        return souvenirs.stream().filter(s -> s.getPrice() < price).map(Souvenir::getManufacturer).toList();
    }

    @Override
    public Set<Manufacturer> getManufacturersByYear(String name, int year, List<Souvenir> list) {
        return list.stream()
                .filter(s -> s.getDateOfManufacture().getYear() == year && s.getName().equals(name))
                .map(Souvenir::getManufacturer)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Manufacturer, List<Souvenir>> group(List<Souvenir> souvenirs) {
        return souvenirs.stream().collect(Collectors.groupingBy(Souvenir::getManufacturer));
    }

    @Override
    public List<Souvenir> getSouvenirsByCountry(String country, List<Souvenir> souvenirs) {
        return souvenirs
                .stream()
                .filter(s -> s.getManufacturer().getCountry().equals(country))
                .toList();
    }

    @Override
    public List<Souvenir> getSouvenirsByYear(int year, List<Souvenir> souvenirs) {
        return souvenirs.stream()
                .filter(s -> s.getDateOfManufacture().getYear() == year)
                .toList();
    }
}
