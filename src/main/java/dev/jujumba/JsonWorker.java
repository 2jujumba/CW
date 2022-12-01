package dev.jujumba;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jujumba.models.Manufacturer;
import dev.jujumba.models.Souvenir;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//Название странное
public class JsonWorker {
    static {
        checkFiles(Path.of("souvenirs.json"));
        checkFiles(Path.of("manufacturers.json"));
    }
    private final ObjectMapper mapper = new ObjectMapper();
    private static JsonWorker jsonWorker;
    @SneakyThrows
    private static void checkFiles(Path path) {
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
    }
    private JsonWorker() {

    }

    public List<Souvenir> getSouvenirs() {
        return readListFromFile("souvenirs.json");
    }

    public List<Manufacturer> getManufacturers() {
        return readListFromFile("manufacturers.json");
    }

    @SneakyThrows
    public void save(List<Souvenir> souvenirs, List<Manufacturer> manufacturers) {
        mapper.writeValue(new File("souvenirs.json"), souvenirs);
        mapper.writeValue(new File("manufactures.json"), manufacturers);
    }

    @SneakyThrows
    private List readListFromFile(String fileName) {
        if (new File(fileName).length() == 0) {
            return null;
        }
        return mapper.readValue(new File(fileName), List.class);
    }
    public static JsonWorker getInstance() { //ленивый синглтон
        if (jsonWorker == null) {
            jsonWorker = new JsonWorker();
        }
        return jsonWorker;
    }
}
