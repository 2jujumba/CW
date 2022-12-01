package dev.jujumba.strategy;

import dev.jujumba.models.Manufacturer;
import dev.jujumba.models.Souvenir;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Реализация стратегии
 * Вообще, не думаю что оно тут сильно уместна, так как по факту,
 * реализация всей работы программа может только 1.
 * В плане того, что вряд ли можно сделать 2 разные реализации группировки сувениров
 * по производителю
 */
public interface Strategy {
    void print(List list);
    void addManufacturer(List<Manufacturer> list);
    void addSouvenir(List<Souvenir> list);
    void setManufacturer(int index, List<Manufacturer> manufacturers, List<Souvenir> souvenirs);
    void setSouvenir(int index, List<Souvenir> souvenirs);
    void deleteManufacturerAndHisSouvenirs(Manufacturer manufacturer, List<Manufacturer> manufacturers, List<Souvenir> souvenirs);
    List<Manufacturer> priceLowerThan(double price, List<Souvenir> souvenirs);
    Set<Manufacturer> getManufacturersByYear(int year, List<Souvenir> list);
    Map<Manufacturer, List<Souvenir>> group(List<Souvenir> souvenirs);
    List<Souvenir> getSouvenirsByCountry(String country, List<Souvenir> souvenirs);
    List<Souvenir> getSouvenirsByYear(int year, List<Souvenir> souvenirs);
}
