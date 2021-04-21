package commands

import dtos.Dependency
import services.getDependenciesFromFiles
import java.io.File

const val AGE_COMMAND = "age"
const val VULNERABILITY_COMMAND = "vulnerability"
const val SUMMARY_COMMAND = "summary"

abstract class InspectorLibCommand {

    var dependencyFiles: List<String> = emptyList()

    fun parse(args: Array<String>): Boolean {
        if (args.size == 1) return false

        val files = args.copyOfRange(1, args.size)
        dependencyFiles = files.filter(this::fileOrFolderExists)
        return dependencyFiles.isNotEmpty() && files.size == dependencyFiles.size
    }

    var dependencies: List<Dependency> = emptyList()

    fun execute() {
        dependencies = getDependenciesFromFiles(dependencyFiles)
        consumeDependencies(dependencies)
    }

    abstract fun consumeDependencies(dependencies: List<Dependency>)

    fun fileOrFolderExists(fileOrFolderPath: String): Boolean {
        val file = File(fileOrFolderPath)
        if (!file.exists()) System.err.println("Could not find path $fileOrFolderPath")
        return file.exists()
    }
}