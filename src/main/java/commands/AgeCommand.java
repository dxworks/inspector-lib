package commands;

import dtos.Dependency;
import services.DependencyService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AgeCommand implements InspectorLibCommand {

    private List<String> dependencyFiles;

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

        for (Dependency s : dependencies) {
            System.out.println(s);
        }
    }
}
