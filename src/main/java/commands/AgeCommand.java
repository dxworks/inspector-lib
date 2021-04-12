package commands;

import dtos.Dependency;
import dtos.LibraryInformation;
import dtos.LibraryVersion;
import factory.LibraryServiceFactory;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import services.DateUtilsKt;
import services.DependencyService;
import services.LibraryService;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static services.ResultsFileServiceKt.writeInResultsFile;

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

                if (libraryService == null) {
                    System.out.println("Provider " + entry.getKey() + " not supported!");
                    continue;
                }

                for (Dependency d : entry.getValue()) {
                    try {
                        content.add(getDependencyAge(Objects.requireNonNull(libraryService.getInformation(d)), d.getVersion()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pb.step();
                }
            }

            writeInResultsFile(content);
        }

        System.out.println("Result file was created successfully!");
    }

    private String getDependencyAge(LibraryInformation information, String version) {
        StringBuilder result = new StringBuilder(information.getName() + "," + version + ",");
        ZonedDateTime currentVersionDate = null;
        ZonedDateTime latestVersionDate = null;

        for (LibraryVersion v : information.getVersions()) {
            if (version.equals(v.getVersion())) {
                result.append(v.getTimestamp()).append(",");
                currentVersionDate = v.getTimestamp();
            }
        }

        for (LibraryVersion v : information.getVersions()) {
            if (v.isLatest()) {
                result.append(v.getVersion()).append(",").append(v.getTimestamp()).append(",");
                latestVersionDate = v.getTimestamp();
            }
        }

        if (currentVersionDate != null && latestVersionDate != null)
            result.append(DateUtilsKt.differenceBetweenDates(currentVersionDate, latestVersionDate));

        return result.toString();
    }
}
