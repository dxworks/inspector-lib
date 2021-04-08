package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.Dependency;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DependencyService {

    public List<Dependency> getDependenciesFromFiles(List<String> ruleFiles) {

        List<Path> ruleFilePaths = ruleFiles.stream()
                .map(Paths::get)
                .flatMap(fileOrFolder -> {
                    if (Files.isDirectory(fileOrFolder)) {
                        return getAllDependencyFilesFromFolder(fileOrFolder);
                    } else return Stream.of(fileOrFolder);
                }).collect(Collectors.toList());

        return ruleFilePaths.stream()
                .flatMap(ruleFile -> getDependenciesFromFile(ruleFile).stream())
                .collect(Collectors.toList());
    }

    private Stream<Path> getAllDependencyFilesFromFolder(Path fileOrFolder) {
        try {
            return Files.walk(fileOrFolder).filter(path -> path.getFileName().toString().endsWith(".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

    private List<Dependency> getDependenciesFromFile(Path ruleFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(ruleFilePath.toFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println(e + "Could not read dependency file!");
        }
        return new ArrayList<>();
    }
}
