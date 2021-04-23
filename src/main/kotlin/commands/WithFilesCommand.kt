package commands

import dtos.Dependency
import services.getDependenciesFromFiles

abstract class WithFilesCommand : InspectorLibCommand() {

    private var dependencyFiles: List<String> = emptyList()

    override fun parse(args: Array<String>): Boolean {
        if (args.size == 1) return false

        val files = args.copyOfRange(1, args.size)
        dependencyFiles = files.filter(this::fileOrFolderExists)
        return dependencyFiles.isNotEmpty() && files.size == dependencyFiles.size
    }

    var dependencies: List<Dependency> = emptyList()

    override fun execute() {
        dependencies = getDependenciesFromFiles(dependencyFiles)
        consumeDependencies(dependencies)
    }

    abstract fun consumeDependencies(dependencies: List<Dependency>)
}