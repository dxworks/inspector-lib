package commands;

import dtos.Dependency;
import factory.LibraryServiceFactory;
import services.LibraryService;
import services.DependencyService;

import java.util.*;
import java.util.stream.Collectors;

public class AgeCommand implements InspectorLibCommand {

    private List<String> dependencyFiles;
    private Map<String, List<Dependency>> mappedDependencies;

    @Override
    public boolean parse(String[] args) {
        if (args.length == 1)
            return false;

        String[] files = Arrays.copyOfRange(args, 1, args.length);

        dependencyFiles = Arrays.stream(files).filter(this::fileOrFolderExists).collect(Collectors.toList());

        return !dependencyFiles.isEmpty() && files.length == dependencyFiles.size();
    }

    @Override
    public void execute(String[] args) {
        DependencyService dependencyService = new DependencyService();
        List<Dependency> dependencies = dependencyService.getDependenciesFromFiles(dependencyFiles);

        mappedDependencies = dependencies.stream().collect(Collectors.groupingBy(Dependency::getProvider));

        LibraryService libraryService;
        LibraryServiceFactory libraryServiceFactory = new LibraryServiceFactory();

        for (Map.Entry<String, List<Dependency>> entry : mappedDependencies.entrySet()) {
            libraryService = libraryServiceFactory.createLibraryService(entry.getKey());

            for (Dependency d : entry.getValue())
                libraryService.getInformation(d);
        }
    }
}