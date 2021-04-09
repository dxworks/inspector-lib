package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.Dependency;
import factory.LibraryServiceFactory;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import services.LibraryService;
import services.DependencyService;
import services.ResultsFileService;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        ResultsFileService resultsFileService = new ResultsFileService();
        List<String> content = new ArrayList<>();

        LibraryService libraryService;
        LibraryServiceFactory libraryServiceFactory = new LibraryServiceFactory();

        try (ProgressBar pb = new ProgressBarBuilder()
                .setInitialMax(dependencies.size())
                .setUnit(" Dependencies", 1)
                .setTaskName("Getting age...")
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis(100)
                .setPrintStream(System.err)
                .build()) {
            for (Map.Entry<String, List<Dependency>> entry : mappedDependencies.entrySet()) {
                libraryService = libraryServiceFactory.createLibraryService(entry.getKey());

                if(libraryService == null) {
                    System.out.println("Provider " + entry.getKey() + " not supported!");
                    continue;
                }

                for (Dependency d : entry.getValue()) {
                    content.add(libraryService.getInformation(d));
                    pb.step();
                }
            }

            resultsFileService.writeInResultsFile(content);
        }

        System.out.println("Result file was created successfully!");
    }
}