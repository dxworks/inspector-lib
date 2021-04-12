package commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface InspectorLibCommand {

    String AGE = "age";

    boolean parse(String[] args);

    default boolean fileOrFolderExists(String fileOrFolderPath) {
        Path path = Paths.get(fileOrFolderPath);
        boolean result = Files.exists(path) && (Files.isRegularFile(path) || Files.isDirectory(path));

        if (!result)
            System.err.println("Could not find path " + path.toAbsolutePath());

        return result;
    }

    void execute(String[] args);
}
