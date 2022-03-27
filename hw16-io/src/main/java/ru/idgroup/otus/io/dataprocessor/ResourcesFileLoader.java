package ru.idgroup.otus.io.dataprocessor;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.idgroup.otus.io.model.Measurement;

import javax.json.Json;
import javax.json.JsonStructure;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ResourcesFileLoader implements Loader {
    private final String fileName;
    private ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        mapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try {
            var file = new File(ClassLoader.getSystemResource(fileName).getFile());
            List<LinkedHashMap<String, Object>> objects = mapper.readValue(file, List.class);
            return objects.stream()
                .map( linkedHashMap -> new Measurement((String)linkedHashMap.get("name"), (Double) linkedHashMap.get("value")))
                .collect(Collectors.toList());

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
