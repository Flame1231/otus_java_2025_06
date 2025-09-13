package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.otus.model.Measurement;

@Slf4j
public class ResourcesFileLoader implements Loader {

    private static final ObjectMapper mapper = JsonMapper.builder().build();
    private static final ClassLoader classLoader = ResourcesFileLoader.class.getClassLoader();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                log.error("Файл отсутствует: " + fileName);
                return Collections.emptyList();
            }
            return mapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Ошибка при чтении файла " + fileName + ": " + e.getMessage(), e);
            throw new FileProcessException(e);
        }
    }
}
